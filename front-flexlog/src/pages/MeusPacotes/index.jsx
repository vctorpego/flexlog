import React, { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import Grid from "../../components/Grid";
import { useNavigate } from "react-router-dom";
import * as C from "./styles";
import SearchBar from "../../components/SearchBar";

const MeusPacotes = () => {
  const [pacotes, setPacotes] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const navigate = useNavigate();
  const [permissoesTelaAtual, setPermissoesTelaAtual] = useState([]);

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
      return token;
    } catch (error) {
      localStorage.removeItem("token");
      navigate("/auth/login");
      return null;
    }
  };

  const getRequestConfig = () => {
    const token = getToken();
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {};
  };

  useEffect(() => {
    const token = getToken();
    if (!token) return;

    const decoded = jwt_decode(token);
    const userLogin = decoded.sub;

    const fetchUserData = async () => {
      try {
        const userResponse = await axios.get(
          `http://localhost:8080/usuario/id/${userLogin}`,
          getRequestConfig()
        );
        const userId = userResponse.data;

        // Buscar pacotes
        const pacotesResponse = await axios.get(
          `http://localhost:8080/entregador/pacotes/${userId}`,
          getRequestConfig()
        );

        const pacotesComCampos = pacotesResponse.data.map(p => {
          const saidaObj = p.historicosStatus?.find(
            h => h.id?.statusPacote?.nomeStatusPacote === "Saiu para entrega"
          );
          
          let saidaFormatada = "-";
          if (saidaObj) {
            const dataStatus = saidaObj.id?.dataStatus;
            if (typeof dataStatus === "string") {
              saidaFormatada = new Date(dataStatus).toLocaleString("pt-BR");
            }
          }
           
          
        
          return {
            ...p,
            destinatario: typeof p.destinatario === "string"
              ? p.destinatario
              : p.destinatario?.nome || "N/A",
            saida: saidaFormatada,
            horarioPrevisto: p.ultimoAgendamento?.horarioPrevisto || "-"
          };
        });
        

        setPacotes(pacotesComCampos);
        console.log(pacotesComCampos);
        console.log(pacotesResponse.data);

        // Buscar permissões
        const permissionsResponse = await axios.get(
          `http://localhost:8080/permissao/telas/${userId}`,
          getRequestConfig()
        );

        const telaAtual = "Tela de Meus Pacotes";
        const permissoesTela = permissionsResponse.data.find(
          (perm) => perm.tela === telaAtual
        );
        const permissoes = permissoesTela?.permissoes || [];
        setPermissoesTelaAtual(permissoes);
      } catch (error) {
        console.error("Erro ao buscar pacotes ou permissões:", error);
      }
    };

    fetchUserData();
  }, []);

  const filterPacotes = () => {
    return !searchQuery
      ? pacotes
      : pacotes.filter((p) =>
          p.codigoRastreio?.toLowerCase().includes(searchQuery.toLowerCase()) ||
          p.destinatario?.toLowerCase().includes(searchQuery.toLowerCase()) ||
          p.transportadora?.nomeSocial?.toLowerCase().includes(searchQuery.toLowerCase())
        );
  };

  const handleViewPacote = (idPacote) => {
    alert("Consulta de Pacote - redirecionar");
    //navigate(`/pacotes/view/${idPacote}`);
  };

  const handleTentativaEntregaPacote = (idPacote) => {
  
    navigate(`/tentativa-entrega/${idPacote}`);
  };

  const columns = ["Código", "Destinatário", "Saida", "Horário Previsto"];
  const actions = [];

  if (permissoesTelaAtual.includes("PUT")) actions.push("tentativa");
  if (permissoesTelaAtual.includes("GET")) actions.push("view");

  return (
    <C.Container>
      <C.Content>
        <C.Title>Meus Pacotes</C.Title>
        <SearchBar input={searchQuery} setInput={setSearchQuery} msg={"Digite o código"} />

        {pacotes.length === 0 ? (
          <p>Nenhum pacote encontrado.</p>
        ) : (
          <Grid
            data={filterPacotes()}
            columns={columns}
            columnMap={{
              Código: "codigoRastreio",
              Destinatário: "destinatario",
              Saida: "saida",
              "Horário Previsto": "horarioPrevisto"
            }}
            idKey="codigoRastreio"
            actions={actions}
            showActionsColumn={actions.length > 0}
            handleView={handleViewPacote}
            handleTentativaEntregaPacote={handleTentativaEntregaPacote}
          />
        )}
      </C.Content>
    </C.Container>
  );
};

export default MeusPacotes;
