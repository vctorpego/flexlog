import React, { useEffect, useState } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import * as C from "./styles";
import { FileText } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { Message } from "../ListagemProdutos/styles";

const Relatorios = () => {
  const [user, setUser] = useState(null);
  const [messageType, setMessageType] = useState("");
  const [message, setMessage] = useState("");
  const [showParamPopup, setShowParamPopup] = useState(false);
  const [selectedRel, setSelectedRel] = useState(null);
  const [mes, setMes] = useState("");
  const [ano, setAno] = useState("");
  
  const navigate = useNavigate();

  const relatorios = [
    { nome: "Transportadoras mais utilizadas", endpoint: "transportadoras-utilizadas" },
    { nome: "Relatório de reagendamentos", endpoint: "reagendamentos" },
    { nome: "Relatório de entregas", endpoint: "entregas" },
    { nome: "Relatório de atrasos", endpoint: "atrasos" }
  ];

  const getToken = () => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/auth/login");
      return null;
    }
    try {
      const decoded = jwt_decode(token);
      if (decoded.exp < Date.now() / 1000) {
        localStorage.removeItem("token");
        navigate("/auth/login");
        return null;
      }
      setUser(decoded);
      return token;
    } catch {
      localStorage.removeItem("token");
      navigate("/auth/login");
      return null;
    }
  };

  const getRequestConfig = () => {
    const token = getToken();
    return token
      ? { responseType: "blob", headers: { Authorization: `Bearer ${token}` } }
      : {};
  };

  const handleDownload = async (endpoint, params = {}) => {
    try {
      let url = `http://localhost:8080/relatorios/${endpoint}`;
      if (params.mes) url += `?mes=${encodeURIComponent(params.mes)}`;
      if (params.ano) url += params.mes ? `&ano=${encodeURIComponent(params.ano)}` : `?ano=${encodeURIComponent(params.ano)}`;

      const response = await axios.post(url, {}, getRequestConfig());

      const blobUrl = window.URL.createObjectURL(
        new Blob([response.data], { type: "application/pdf" })
      );
      const link = document.createElement("a");
      link.href = blobUrl;
      link.setAttribute("download", `${endpoint}.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error("Erro ao gerar relatório:", error);
      setMessageType("error");
      setMessage("Erro ao baixar o relatório!");
      setTimeout(() => {
        setMessage("");
        setMessageType("");
      }, 3000);
    }
  };

  const handleCardClick = (rel) => {
    setSelectedRel(rel);
    setShowParamPopup(true);
  };

  const handleParamSubmit = (e) => {
    e.preventDefault();
    setShowParamPopup(false);
    handleDownload(selectedRel.endpoint, { mes, ano });
    setMes("");
    setAno("");
    setSelectedRel(null);
  };

  useEffect(() => {
    getToken();
  }, []);

  return (
    <C.Container>
      <C.Content>
        <C.Title>Relatórios</C.Title>
        {message && <Message type={messageType}>{message}</Message>}

        <C.CardContainer>
          {relatorios.map((rel, idx) => (
            <C.Card key={idx} onClick={() => handleCardClick(rel)}>
              <FileText size={40} color="#007bff" />
              <span>{rel.nome}</span>
            </C.Card>
          ))}
        </C.CardContainer>

        {showParamPopup && (
          <C.ModalOverlay>
            <C.ModalForm onSubmit={handleParamSubmit}>
              <C.ModalTitle>Selecione o mês e ano</C.ModalTitle>
              <C.ModalLabel>
                Mês:
                <C.ModalInput
                  type="number"
                  min="1"
                  max="12"
                  value={mes}
                  onChange={(e) => setMes(e.target.value)}
                  required
                />
              </C.ModalLabel>
              <C.ModalLabel>
                Ano:
                <C.ModalInput
                  type="number"
                  min="2000"
                  max="2100"
                  value={ano}
                  onChange={(e) => setAno(e.target.value)}
                  required
                />
              </C.ModalLabel>
              <C.ModalButtonContainer>
                <C.ModalCancelButton type="button" onClick={() => setShowParamPopup(false)}>
                  Cancelar
                </C.ModalCancelButton>
                <C.ModalSubmitButton type="submit">
                  Baixar Relatório
                </C.ModalSubmitButton>
              </C.ModalButtonContainer>
            </C.ModalForm>
          </C.ModalOverlay>
        )}
      </C.Content>
    </C.Container>
  );
};

export default Relatorios;
