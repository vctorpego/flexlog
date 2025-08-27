import React, { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import Grid from "../../components/Grid";
import Modal from "../../components/Modal";
import { useNavigate } from "react-router-dom";
import * as C from "./styles";
import SearchBar from "../../components/SearchBar";
import { AddButton, ModalButton } from "./styles";

const ListagemAgendamentos = () => {
  const [agendamentos, setAgendamentos] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [user, setUser] = useState(null);
  const [permissoesTelaAtual, setPermissoesTelaAtual] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedPacote, setSelectedPacote] = useState(null);
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
    } catch {
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

    const fetchAgendamentos = async () => {
      try {
        const response = await axios.get("http://localhost:8080/pacotes/rollback", getRequestConfig());
        setAgendamentos(response.data);
      } catch (error) {
        console.error("Erro ao buscar agendamentos:", error);
      }
    };

    const fetchUserPermissions = async () => {
      try {
        const userResponse = await axios.get(`http://localhost:8080/usuario/id/${userLogin}`, getRequestConfig());
        const userId = userResponse.data;

        const permissionsResponse = await axios.get(
          `http://localhost:8080/permissao/telas/${userId}`,
          getRequestConfig()
        );

        const telaAtual = "Tela de Agendamentos";
        const permissoesTela = permissionsResponse.data.find((perm) => perm.tela === telaAtual);
        setPermissoesTelaAtual(permissoesTela?.permissoes || []);
      } catch (error) {
        console.error("Erro ao buscar permissões:", error);
      }
    };

    fetchAgendamentos();
    fetchUserPermissions();
  }, []);

  const filterAgendamentos = () => {
    return !searchQuery
      ? agendamentos
      : agendamentos.filter((p) =>
          p.codigoRastreio?.toLowerCase().includes(searchQuery.toLowerCase()) ||
          p.destinatario?.toLowerCase().includes(searchQuery.toLowerCase())
        );
  };

  const handleOpenModal = (pacote) => {
    setSelectedPacote(pacote);
    setModalOpen(true);
  };

  const handleEfetuar = async () => {
    if (!selectedPacote) return;

    const dto = { entregador: { id: user.id } }; // envia id do entregador logado
    try {
      await axios.put(`http://localhost:8080/pacotes/saida/${selectedPacote.idPacote}`, dto, getRequestConfig());
      setModalOpen(false);
      setSelectedPacote(null);
      // Atualiza a lista de agendamentos
      setAgendamentos((prev) => prev.filter((p) => p.idPacote !== selectedPacote.idPacote));
    } catch (error) {
      console.error("Erro ao efetuar agendamento:", error);
    }
  };

  const columns = ["Código", "Data Agendada", "Horário Previsto", "Status"];
  const actions = permissoesTelaAtual.includes("PUT") ? ["efetuar"] : [];
  const showActionsColumn = actions.length > 0;

  return (
    <C.Container>
      <C.Content>
        <C.Title>Lista de Agendamentos</C.Title>

        <SearchBar input={searchQuery} setInput={setSearchQuery} msg={"Digite o código"} />

        {agendamentos.length === 0 ? (
          <p>Nenhum agendamento encontrado.</p>
        ) : (
          <Grid
            data={filterAgendamentos()}
            columns={columns}
            columnMap={{
              "Código": "codigoRastreio",
              "Data Agendada": "ultimoAgendamento.dtAgendamento",
              "Horário Previsto": "ultimoAgendamento.horarioPrevisto",
              "Status": "ultimoStatus.nomeStatusPacote"
            }}
            idKey="idPacote"
            actions={actions}
            showActionsColumn={showActionsColumn}
            handleView={() => {}}
            handleSaida={handleOpenModal}
          />
        )}

        <Modal open={modalOpen} onClose={() => setModalOpen(false)}>
          <h3>Efetuar Agendamento?</h3>
          <ModalButton onClick={handleEfetuar}>Confirmar</ModalButton>
          <ModalButton onClick={() => setModalOpen(false)}>Cancelar</ModalButton>
        </Modal>
      </C.Content>
    </C.Container>
  );
};

export default ListagemAgendamentos;
