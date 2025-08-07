import React, { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import { useNavigate, useParams } from "react-router-dom";
import * as C from "./styles";

const EditTransportadora = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [nome, setNome] = useState("");
  const [cnpj, setCnpj] = useState("");
  const [logradouro, setLogradouro] = useState("");
  const [numero, setNumero] = useState("");
  const [bairro, setBairro] = useState("");
  const [cidade, setCidade] = useState("");
  const [estado, setEstado] = useState("");
  const [cep, setCep] = useState("");
  const [complemento, setComplemento] = useState("");

  const [hasPermission, setHasPermission] = useState(false);
  const [isSuperAdm, setIsSuperAdm] = useState(false); // <- Adicionado

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

    const fetchUserData = async () => {
      try {
        const userRes = await axios.get(
          `http://localhost:8080/usuario/id/${userLogin}`,
          getRequestConfig()
        );
        const currentUserId = userRes.data;

        const permsRes = await axios.get(
          `http://localhost:8080/permissao/telas/${currentUserId}`,
          getRequestConfig()
        );

        const usuarioRes = await axios.get(
          `http://localhost:8080/usuario/${currentUserId}`,
          getRequestConfig()
        );
        setIsSuperAdm(usuarioRes.data.isSuperAdm);

        const telaTransportadoras = permsRes.data.find(
          (perm) => perm.tela === "Tela de Transportadoras"
        );

        const temPermissaoPUT = telaTransportadoras?.permissoes?.includes("PUT") || false;
        setHasPermission(temPermissaoPUT);

        if (!temPermissaoPUT) {
          navigate("/nao-autorizado");
          return;
        }

        // Carrega dados da transportadora
        fetchTransportadora();

      } catch (error) {
        console.error("Erro ao verificar permissões:", error);
        alert("Erro ao verificar permissões.");
      }
    };

    const fetchTransportadora = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/transportadora/${id}`,
          getRequestConfig()
        );
        const {
          nomeSocial, cnpj, logradouro, numero,
          complemento, bairro, cidade, estado, cep
        } = response.data;

        setNome(nomeSocial);
        setCnpj(cnpj);
        setLogradouro(logradouro);
        setNumero(numero);
        setComplemento(complemento);
        setBairro(bairro);
        setCidade(cidade);
        setEstado(estado);
        setCep(cep);
      } catch (error) {
        console.error("Erro ao carregar transportadora:", error);
        alert("Erro ao carregar transportadora.");
      }
    };

    fetchUserData();
  }, [id, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!nome || !logradouro) {
      alert("Preencha todos os campos obrigatórios.");
      return;
    }

    const token = getToken();
    if (!token) return;

    try {
      const response = await axios.put(
        `http://localhost:8080/transportadora/alterar/${id}`,
        {
          nomeSocial: nome,
          cnpj: cnpj,
          numero: numero,
          complemento: complemento,
          logradouro: logradouro,
          bairro: bairro,
          cidade: cidade,
          estado: estado,
          cep: cep
        },
        getRequestConfig()
      );

      if (response.status === 200) {
        alert("Transportadora atualizada com sucesso!");
        navigate("/transportadoras");
      }
    } catch (error) {
      console.error("Erro ao atualizar transportadora:", error);
      alert("Erro ao atualizar transportadora.");
    }
  };

  if (!hasPermission) {
    return <div>Você não tem permissão para acessar esta página.</div>;
  }

  return (
    <C.Container>
      <C.Title>Editar Transportadora</C.Title>
      <C.Form onSubmit={handleSubmit}>
        <C.Input
          type="text"
          placeholder="Nome da Transportadora"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
        />
        <C.Input
          type="text"
          placeholder="CNPJ"
          value={cnpj}
          readOnly
        />
        <C.Input
          type="text"
          placeholder="Logradouro"
          value={logradouro}
          onChange={(e) => setLogradouro(e.target.value)}
        />
        <C.Input
          type="text"
          placeholder="Numero"
          value={numero}
          onChange={(e) => setNumero(e.target.value)}
        />
        <C.Input
          type="text"
          placeholder="Bairro"
          value={bairro}
          onChange={(e) => setBairro(e.target.value)}
        />
        <C.Input
          type="text"
          placeholder="Cidade"
          value={cidade}
          onChange={(e) => setCidade(e.target.value)}
        />
        <C.Input
          type="text"
          placeholder="CEP"
          value={cep}
          onChange={(e) => setCep(e.target.value)}
        />
        <C.Input
          type="text"
          placeholder="Complemento"
          value={complemento}
          onChange={(e) => setComplemento(e.target.value)}
        />
        <C.Button type="submit">Salvar Alterações</C.Button>
      </C.Form>
    </C.Container>
  );
};

export default EditTransportadora;
