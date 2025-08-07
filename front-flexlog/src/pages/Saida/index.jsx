import React, { useEffect, useState } from "react";
import axios from "axios";
import * as C from "./styles";
import { useNavigate, useParams } from "react-router-dom";

const Saida = () => {
  const [pacote, setPacote] = useState(null);
  const [entregadores, setEntregadores] = useState([]);
  const [selectedEntregador, setSelectedEntregador] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { idPacote } = useParams(); // pega id do pacote da URL

  const getRequestConfig = () => {
    const token = localStorage.getItem("token");
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {};
  };

  useEffect(() => {
    if (!idPacote) {
      alert("Pacote não informado.");
      navigate("/pacotes"); // volta para listagem
      return;
    }

    const fetchPacote = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/pacotes/${idPacote}`, getRequestConfig());
        setPacote(response.data);
      } catch (error) {
        console.error("Erro ao buscar pacote:", error);
        alert("Erro ao carregar pacote.");
        navigate("/pacotes");
      }
    };

    const fetchEntregadores = async () => {
      try {
        const response = await axios.get("http://localhost:8080/entregador", getRequestConfig());
        setEntregadores(response.data);
      } catch (error) {
        console.error("Erro ao buscar entregadores:", error);
        alert("Erro ao carregar entregadores.");
      }
    };

    fetchPacote();
    fetchEntregadores();
  }, [idPacote, navigate]);

  const handleSelectEntregador = (e) => {
    setSelectedEntregador(e.target.value);
  };

  const registrarSaida = async () => {
    if (!selectedEntregador) {
      alert("Selecione um entregador antes de registrar a saída.");
      return;
    }

    setLoading(true);
    try {
      const body = {
        entregador: {
          idUsuario: Number(selectedEntregador),
        },
      };

      await axios.put(
        `http://localhost:8080/pacotes/saida/${idPacote}`,
        body,
        getRequestConfig()
      );

      alert("Saída registrada com sucesso!");
      navigate("/pacotes"); // volta para lista após saída
    } catch (error) {
      console.error("Erro ao registrar saída:", error);
      alert("Erro ao registrar saída.");
    }
    setLoading(false);
  };

  if (!pacote) return <p>Carregando dados do pacote...</p>;

  return (
    <C.Container>
      <C.Title>Registrar Saída de Pacote</C.Title>

      <C.Form>
        <label>Código de Rastreio</label>
        <C.Input type="text" value={pacote.codigoRastreio} readOnly />

        <label>Destinatário</label>
        <C.Input type="text" value={pacote.destinatario} readOnly />

        <label>Transportadora</label>
        <C.Input type="text" value={pacote.transportadora?.nomeSocial || ""} readOnly />

        <label>Entregador</label>
        <C.Select value={selectedEntregador} onChange={handleSelectEntregador}>
          <option value="">Selecione o entregador</option>
          {entregadores.map((ent) => (
            <option key={ent.idUsuario} value={ent.idUsuario}>
              {ent.nomeUsuario || ent.nome}
            </option>
          ))}
        </C.Select>

        <C.Button disabled={loading} onClick={registrarSaida}>
          Registrar Saída
        </C.Button>
      </C.Form>
    </C.Container>
  );
};

export default Saida;
