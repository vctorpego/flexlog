import React, { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import Grid from "../../components/Grid";
import ModalExcluir from "../../components/ModalExcluir";
import { useNavigate } from "react-router-dom";
import *  as C from "./styles";
import SearchBar from "../../components/SearchBar";
import { AddButton } from "./styles";




const ListagemTransportadoras = () => {
  const [transportadoras, setTransportadoras] = useState([]);
  const [user, setUser] = useState(null);
  const [openModalExcluir, setOpenModalExcluir] = useState(false);
  const [transportadoraExcluir, setTransportadoraExcluir] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");
  const [userPermissions, setUserPermissions] = useState([]);
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
      console.error("Erro ao decodificar token:", error);
      localStorage.removeItem("token");
      navigate("/auth/login");
      return null;
    }
  };

  const getRequestConfig = () => {
    const token = localStorage.getItem("token");
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {};
  };

  useEffect(() => {
    const token = getToken();
    if (!token) return;

    const decoded = jwt_decode(token);
    const userLogin = decoded.sub;

    axios
      .get("http://localhost:8080/transportadora", getRequestConfig())
      .then(({ data }) => {
        setTransportadoras(data);
      })
      .catch((err) => {
        console.error("Erro ao buscar transportadoras:", err);
      });

    const fetchUserPermissions = async () => {
      try {
        // Busca o ID do usuário pelo login
        const response = await axios.get(
          `http://localhost:8080/usuario/id/${userLogin}`,
          getRequestConfig()
        );
        const userId = response.data;

        // Busca permissões do usuário para a tela atual
        const permissionsResponse = await axios.get(
          `http://localhost:8080/permissao/telas/${userId}`,
          getRequestConfig()
        );
        setUserPermissions(permissionsResponse.data);

        const telaAtual = "Tela de Transportadoras";  
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

    fetchUserPermissions();
  }, [navigate]);

  const filterTransportadoras = () => {
    return !searchQuery
      ? transportadoras
      : transportadoras.filter((transportadora) =>
          transportadora.nomeTransportadora.toLowerCase().includes(searchQuery.toLowerCase())
        );
  };

  const handleDeleteTransportadora = (transportadoraId) => {
    setTransportadoraExcluir(transportadoraId);
    setOpenModalExcluir(true);
  };

  const handleConfirmDelete = async () => {
    try {
      await axios.delete(
        `http://localhost:8080/transportadora/${transportadoraExcluir}`,
        getRequestConfig()
      );

      setTransportadoras((prev) =>
        prev.filter((transportadora) => transportadora.idTransportadora !== transportadoraExcluir)
      );
      setOpenModalExcluir(false);
      setTransportadoraExcluir(null);
    } catch (error) {
      if (error.response?.status === 409) {
        alert("⚠️ Você não pode excluir esta transportadora.");
      } else {
        console.error("Erro ao deletar transportadora:", error);
        alert("❌ Ocorreu um erro ao deletar a transportadora.");
      }
    }
  };

  const handleCloseModal = () => {
    setOpenModalExcluir(false);
    setTransportadoraExcluir(null);
  };

  const handleAddTransportadora = () => {
    navigate("/transportadoras/adicionar");
  };

  const handleEditTransportadora = (transportadoraId) => {
    navigate(`/transportadoras/editar/${transportadoraId}`);
  };

  const columns = ["ID", "Nome Social", "CNPJ", "Cidade"];

    // Ajuste as colunas conforme seu objeto transportadora

  const actions = [];
  if (permissoesTelaAtual.includes("PUT")) actions.push("edit");
  if (permissoesTelaAtual.includes("DELETE")) actions.push("delete");

  const showActionsColumn =
    permissoesTelaAtual.includes("PUT") || permissoesTelaAtual.includes("DELETE");

  return (
    <C.Container>
      <C.Content>
        <C.Title>Lista de Transportadoras</C.Title>

        <SearchBar input={searchQuery} setInput={setSearchQuery} msg={"Digite o nome social"}/>

        {permissoesTelaAtual.includes("POST") && (
            <AddButton onClick={handleAddTransportadora}>
            Adicionar Transportadora
          </AddButton>

        )}

        {transportadoras.length === 0 ? (
          <p>Nenhuma transportadora encontrada.</p>
        ) : (
          <Grid
            data={filterTransportadoras()}
            columns={columns}
            columnMap={{
              ID: "idTransportadora",
              "Nome Social": "nomeSocial",
              CNPJ: "cnpj",
              Cidade : "cidade"
            }}
            idKey="idTransportadora"
            handleDelete={handleDeleteTransportadora}
            handleEdit={handleEditTransportadora}
            actions={actions}
            showActionsColumn={showActionsColumn}
          />
        )}

        <ModalExcluir
          open={openModalExcluir}
          onClose={handleCloseModal}
          onConfirm={handleConfirmDelete}
        />
      </C.Content>
    </C.Container>
  );
};

export default ListagemTransportadoras;
