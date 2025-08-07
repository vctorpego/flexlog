import React, { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import Grid from "../../components/Grid";
import { useNavigate } from "react-router-dom";
import * as C from "./styles";
import SearchBar from "../../components/SearchBar";
import { AddButton } from "./styles";


const ListagemPacotes = () => {
  const [pacotes, setPacotes] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [user, setUser] = useState(null);
  const [permissoesTelaAtual, setPermissoesTelaAtual] = useState([]);
  const navigate = useNavigate();

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

    const fetchPacotes = async () => {
      try {
        const response = await axios.get("http://localhost:8080/pacotes", getRequestConfig());
        setPacotes(response.data);
      } catch (error) {
        console.error("Erro ao buscar pacotes:", error);
      }
    };

    const fetchUserPermissions = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/usuario/id/${userLogin}`, getRequestConfig());
        const userId = response.data;

        const permissionsResponse = await axios.get(
          `http://localhost:8080/permissao/telas/${userId}`,
          getRequestConfig()
        );

        const telaAtual = "Tela de Pacotes";
        const permissoesTela = permissionsResponse.data.find(
          (perm) => perm.tela === telaAtual
        );

        const permissoes = permissoesTela?.permissoes || [];
        setPermissoesTelaAtual(permissoes);
        console.log(`Permissões para ${telaAtual}:`, permissoes);
      } catch (error) {
        console.error("Erro ao buscar permissões:", error);
      }
    };

    fetchPacotes();
    fetchUserPermissions();
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

  const handleSaidaPacote = (idPacote) => {
    navigate(`/saida/${idPacote}`);
  };

  const handleViewPacote = (idPacote) => {
    alert(`Visualizar pacote com ID ${idPacote}`);
    // futuramente: chamar endpoint de get / pacote/id
  };

  const handleAddPacote = () => {
    navigate("/entrada");
  };

  const columns = ["ID", "Código", "Transportadora", "Destinatário", "Ultimo Status", "Ultimo Agendamento"];

  const actions = [];
  if (permissoesTelaAtual.includes("PUT")) actions.push("saida");
  if (permissoesTelaAtual.includes("GET")) actions.push("view");

  const showActionsColumn =
    permissoesTelaAtual.includes("PUT") || permissoesTelaAtual.includes("GET");

  return (
    <C.Container>
      <C.Content>
        <C.Title>Lista de Pacotes</C.Title>

        <SearchBar input={searchQuery} setInput={setSearchQuery} msg={"Digite o código"}/>
        {permissoesTelaAtual.includes("POST") && (
          <AddButton onClick={handleAddPacote}>
            Novo Pacote
          </AddButton>

        )}

        {pacotes.length === 0 ? (
          <p>Nenhum pacote encontrado.</p>
        ) : (
          <Grid
            data={filterPacotes()}
            columns={columns}
            columnMap={{
              ID : "idPacote",
              Código: "codigoRastreio",
              Transportadora: "transportadora.nomeSocial",
              Destinatário: "destinatario",
              "Ultimo Status": "ultimoStatus.nomeStatusPacote",
              "Ultimo Agendamento": "ultimoAgendamento.nomeAgendamento",

              

            }}
            idKey="idPacote"
            actions={actions}
            showActionsColumn={showActionsColumn}
            handleView={handleViewPacote}
            handleSaida={handleSaidaPacote}
          />
        )}
      </C.Content>
    </C.Container>
  );
};

export default ListagemPacotes;
