import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import jwt_decode from "jwt-decode";
import * as S from "./styles"; // Seu CSS

const TentativaEntrega = () => {
  const { id } = useParams();
  const [pacote, setPacote] = useState({});
  const [erroPacote, setErroPacote] = useState(false);
  
  const [idEntregador, setIdEntregador] = useState(null);
  const [recebedor, setRecebedor] = useState("");
  const [statusEntrega, setStatusEntrega] = useState("");
  const [falhaEntrega, setFalhaEntrega] = useState("");
  const [assinaturaRecebedor, setAssinaturaRecebedor] = useState([]);
  const [loading, setLoading] = useState(false);



  const token = localStorage.getItem("token");

  useEffect(() => {
    let cancelado = false;
  
    if (!token || !id) return;
  
    const decoded = jwt_decode(token);
    const nomeUsuario = decoded.sub;
  
    axios.get(`http://localhost:8080/usuario/id/${nomeUsuario}`, {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => {
      if (!cancelado) setIdEntregador(res.data);
    })
    .catch(err => {
      if (!cancelado) console.error("Erro ao buscar id do entregador:", err);
    });
  
    axios.get(`http://localhost:8080/pacotes/codigo/${id}`, {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => {
      if (!cancelado) setPacote(res.data);
    })
    .catch(err => {
      if (!cancelado) {
        console.error("Erro ao buscar pacote:", err);
        setErroPacote(true);
        alert("Não foi possível carregar o pacote.");
      }
    });
  
    return () => {
      cancelado = true;
    };
  }, [id, token]);
  

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (!file) return;
  
    const reader = new FileReader();
    reader.onload = () => {
      const arrayBuffer = reader.result; // ArrayBuffer com os bytes do arquivo
      const bytes = new Uint8Array(arrayBuffer); // array de bytes
      setAssinaturaRecebedor(Array.from(bytes)); // converte para array normal (números)
    };
    reader.readAsArrayBuffer(file);
  };
  

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!statusEntrega) {
      alert("Selecione o status da entrega.");
      return;
    }
    if (statusEntrega === "Tentativa de entrega com falha" && !falhaEntrega.trim()) {
      alert("Informe o motivo da falha.");
      return;
    }
    if (!idEntregador) {
      alert("ID do entregador não carregado. Aguarde e tente novamente.");
      return;
    }

    setLoading(true);

    const payload = {
      recebedor,
      statusEntrega,
      falhaEntrega: statusEntrega === "Tentativa de entrega com falha" ? falhaEntrega : "",
      assinaturaRecebedor, // array de bytes (números)
      idEntregador,
      idPacote: pacote.idPacote
    };
    

    try {
      await axios.post(`http://localhost:8080/tentativa-entrega/${id}`, payload, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert("Tentativa de entrega registrada com sucesso!");
      // Limpar campos ou redirecionar, se quiser
    } catch (error) {
      console.error("Erro ao registrar tentativa:", error);
      alert("Erro ao registrar tentativa de entrega.");
    } finally {
      setLoading(false);
    }
  };

  if (!pacote?.codigoRastreio && !erroPacote) return <div>Carregando pacote...</div>;


  return (
    <S.Container>
      <S.Title>Tentativa de Entrega</S.Title>
      <S.Form onSubmit={handleSubmit}>
        <S.InputWrapper>
          <S.Label>Código de Rastreio</S.Label>
          <S.Input type="text" value={pacote.codigoRastreio} disabled />
        </S.InputWrapper>

        <S.InputWrapper>
          <S.Label>Destinatário</S.Label>
          <S.Input type="text" value={pacote.destinatario || "N/A"} disabled />
        </S.InputWrapper>

        <S.InputWrapper>
          <S.Label>Status Atual</S.Label>
          <S.Input type="text" value={pacote.ultimoStatus?.nomeStatusPacote || "N/A"} disabled />
        </S.InputWrapper>

        <S.InputWrapper>
          <S.Label>Nome do Recebedor</S.Label>
          <S.Input
            type="text"
            value={recebedor}
            onChange={e => setRecebedor(e.target.value)}

          />
        </S.InputWrapper>

        <S.InputWrapper>
          <S.Label>Assinatura (imagem)</S.Label>
          <S.Input
            type="file"
            accept="image/*"
            onChange={handleFileChange}
          />
        </S.InputWrapper>

        <S.InputWrapper>
          <S.Label>Status da Entrega</S.Label>
          <S.Select
            value={statusEntrega}
            onChange={e => setStatusEntrega(e.target.value)}
            required
          >
            <option value="">Selecione</option>
            <option value="Entregue com sucesso">Entregue com sucesso</option>
            <option value="Tentativa de entrega com falha">Tentativa de entrega com falha</option>
          </S.Select>
        </S.InputWrapper>

        {statusEntrega === "Tentativa de entrega com falha" && (
          <S.InputWrapper>
            <S.Label>Motivo da Falha</S.Label>
            <S.Input
              type="text"
              value={falhaEntrega}
              onChange={e => setFalhaEntrega(e.target.value)}
              required={statusEntrega === "Tentativa de entrega com falha"}
            />
          </S.InputWrapper>
        )}

        <S.Button type="submit" disabled={loading}>
          {loading ? "Registrando..." : "Registrar Tentativa"}
        </S.Button>
      </S.Form>
    </S.Container>
  );
};

export default TentativaEntrega;
