import React, { useEffect, useState } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import { useNavigate } from "react-router-dom";
import * as C from "./styles";

const AddTransportadora = () => {
  const [nomeSocial, setNomeSocial] = useState("");
  const [cnpj, setCnpj] = useState("");
  const [logradouro, setLogradouro] = useState("");
  const [numero, setNumero] = useState("");
  const [complemento, setComplemento] = useState("");
  const [bairro, setBairro] = useState("");
  const [cidade, setCidade] = useState("");
  const [estado, setEstado] = useState("");
  const [cep, setCep] = useState("");

  const [hasPermission, setHasPermission] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const checkPermission = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) {
          navigate("/nao-autorizado");
          return;
        }

        const decoded = jwt_decode(token);
        const userLogin = decoded.sub;

        const usuarioResponse = await axios.get(
          `http://localhost:8080/usuario/id/${userLogin}`,
          { headers: { Authorization: `Bearer ${token}` } }
        );
        const userId = usuarioResponse.data;

        const permissionsResponse = await axios.get(
          `http://localhost:8080/permissao/telas/${userId}`,
          { headers: { Authorization: `Bearer ${token}` } }
        );

        const permissoesTransportadora = permissionsResponse.data.find(
          (perm) => perm.tela === "Tela de Transportadoras"
        );

        const permissoesAtuais = permissoesTransportadora?.permissoes || [];

        const hasPostPermission = permissoesAtuais.includes("POST");
        setHasPermission(hasPostPermission);

        if (!hasPostPermission) {
          navigate("/nao-autorizado");
        }
      } catch (error) {
        console.error("Erro ao verificar permissões:", error);
        navigate("/nao-autorizado");
      }
    };

    checkPermission();
  }, [navigate]);

  const handleCepBlur = async () => {
    if (cep.length !== 8) {
      alert("CEP inválido. Deve conter 8 dígitos.");
      return;
    }

    try {
      const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);
      const data = response.data;

      if (data.erro) {
        alert("CEP não encontrado.");
        return;
      }

      setLogradouro(data.logradouro || "");
      setBairro(data.bairro || "");
      setCidade(data.localidade || "");
      setEstado(data.uf || "");
    } catch (error) {
      console.error("Erro ao buscar CEP:", error);
      alert("Erro ao buscar informações do CEP.");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!nomeSocial || !cnpj || !cidade) {
      alert("Preencha os campos obrigatórios: Nome Social, CNPJ e Cidade.");
      return;
    }

    const token = localStorage.getItem("token");

    try {
      await axios.post(
        "http://localhost:8080/transportadora",
        {
          nomeSocial,
          cnpj,
          logradouro: logradouro || null,
          numero: numero || null,
          complemento: complemento || null,
          bairro: bairro || null,
          cidade,
          estado: estado || null,
          cep: cep || null,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      alert("Transportadora adicionada com sucesso!");
      navigate("/transportadoras");
    } catch (error) {
      console.error("Erro ao adicionar transportadora:", error);
      alert("Erro ao adicionar transportadora.");
    }
  };

  return (
    <C.Container>
      <C.Title>Adicionar Transportadora</C.Title>
      {hasPermission && (
        <C.Form onSubmit={handleSubmit}>
          <C.Input
            type="text"
            placeholder="Nome Social *"
            value={nomeSocial}
            onChange={(e) => setNomeSocial(e.target.value)}
          />
          <C.Input
            type="text"
            placeholder="CNPJ *"
            value={cnpj}
            onChange={(e) => setCnpj(e.target.value)}
          />
          <C.Input
            type="text"
            placeholder="CEP"
            value={cep}
            onChange={(e) => setCep(e.target.value)}
            onBlur={handleCepBlur}
          />
          <C.Input
            type="text"
            placeholder="Logradouro"
            value={logradouro}
            onChange={(e) => setLogradouro(e.target.value)}
          />
          <C.Input
            type="text"
            placeholder="Número"
            value={numero}
            onChange={(e) => setNumero(e.target.value)}
          />
          <C.Input
            type="text"
            placeholder="Complemento"
            value={complemento}
            onChange={(e) => setComplemento(e.target.value)}
          />
          <C.Input
            type="text"
            placeholder="Bairro"
            value={bairro}
            onChange={(e) => setBairro(e.target.value)}
          />
          <C.Input
            type="text"
            placeholder="Cidade *"
            value={cidade}
            onChange={(e) => setCidade(e.target.value)}
          />
          <C.Input
            type="text"
            placeholder="Estado"
            value={estado}
            onChange={(e) => setEstado(e.target.value)}
          />

          <C.Button type="submit">Adicionar Transportadora</C.Button>
        </C.Form>
      )}
    </C.Container>
  );
};

export default AddTransportadora;
