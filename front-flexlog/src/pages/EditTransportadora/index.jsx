import React, { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import { useNavigate, useParams } from "react-router-dom";
import * as C from "./styles";

const EditTransportadora = () => {
  const { id } = useParams();
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

    // Aqui você pode implementar a verificação de permissão se quiser, exemplo:
    // setHasPermission(true); // Se não quiser controle, deixa true direto

    setHasPermission(true);

    const fetchTransportadora = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/transportadora/${id}`,
          getRequestConfig()
        );
        const { nomeSocial, cnpj, logradouro} = response.data;
        setNome(nomeSocial);
        setCnpj(cnpj);
        setLogradouro(logradouro);

      } catch (error) {
        console.error("Erro ao carregar transportadora:", error);
        alert("Erro ao carregar transportadora.");
      }
    };

    fetchTransportadora();
  }, [id, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!nome || !logradouro) {
      alert("Preencha todos os campos.");
      return;
    }

    const token = getToken();
    if (!token) return;

    try {
      const updateResponse = await axios.put(
        `http://localhost:8080/transportadora/${id}`,
        {
          nomeTransportadora: nome,
          CNPJ : cnpj,
        },
        getRequestConfig()
      );

      if (updateResponse.status === 200) {
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
