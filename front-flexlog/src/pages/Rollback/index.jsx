import React, { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import Grid from "../../components/Grid";
import { useNavigate } from "react-router-dom";
import * as C from "./styles";
import SearchBar from "../../components/SearchBar";



const Rollback = () => {
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
        const response = await axios.get("http://localhost:8080/pacotes/rollback", getRequestConfig());
        setPacotes(response.data);
        console.log(response.data);
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

        const telaAtual = "Tela de Rollback";
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

  const handleRollback = (idPacote) => {
     axios.put(`http://localhost:8080/pacotes/rollback/${idPacote}`, getRequestConfig());
     alert(`Pacote Retornado`);
  };

  const handleViewPacote = (idPacote) => {
    alert(`Visualizar pacote com ID ${idPacote}`);
    // futuramente: chamar a pagina de view  de get / pacote/id
  };


  const columns = ["Código","Entregador",  "Transportadora", "Destinatário", "Ultimo Status"];

  const actions = [];
  if (permissoesTelaAtual.includes("PUT")) actions.push("rollback");
  if (permissoesTelaAtual.includes("GET")) actions.push("view");

  const showActionsColumn =
    permissoesTelaAtual.includes("PUT") || permissoesTelaAtual.includes("GET");

  return (
    <C.Container>
      <C.Content>
        <C.Title>Pacotes em Rollback</C.Title>

        <SearchBar input={searchQuery} setInput={setSearchQuery} msg={"Digite o código"}/>

        {pacotes.length === 0 ? (
          <p>Nenhum pacote encontrado.</p>
        ) : (
          <Grid
            data={filterPacotes()}
            columns={columns}
            columnMap={{
              Entregador: "entregador.nomeUsuario",
              Código: "codigoRastreio",
              Transportadora: "transportadora.nomeSocial",
              Destinatário: "destinatario",
              "Ultimo Status": "ultimoStatus.nomeStatusPacote",
            }}
            idKey="idPacote"
            actions={actions}
            showActionsColumn={showActionsColumn}
            handleView={handleViewPacote}
            handleRollback={handleRollback}
          />
        )}
      </C.Content>
    </C.Container>
  );
};

export default Rollback;
