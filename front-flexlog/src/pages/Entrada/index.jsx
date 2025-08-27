import React, { useEffect, useState } from "react";
import axios from "axios";
import * as C from "./styles";

const Entrada = () => {
  const [formData, setFormData] = useState({
    codigoRastreio: "",
    destinatario: "",
    cep: "",
    cidade: "",
    bairro: "",
    rua: "",
    numeroEndereco: "",
    transportadora: {
      idTransportadora: "",
    },
  });

  const [transportadoras, setTransportadoras] = useState([]);

  const getRequestConfig = () => {
    const token = localStorage.getItem("token");
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {};
  };

  useEffect(() => {
    const fetchTransportadoras = async () => {
      try {
        const response = await axios.get("http://localhost:8080/transportadora", getRequestConfig());
        setTransportadoras(response.data);
      } catch (error) {
        console.error("Erro ao buscar transportadoras:", error);
        alert("Erro ao carregar transportadoras.");
      }
    };

    fetchTransportadoras();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "idTransportadora") {
      setFormData((prev) => ({
        ...prev,
        transportadora: {
          idTransportadora: value,
        },
      }));
    } else {
      setFormData((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/pacotes", formData, getRequestConfig());
      if (response.status === 201 || response.status === 200) {
        alert("Entrada registrada com sucesso!");
      } else {
        alert("Erro ao registrar entrada.");
      }
    } catch (error) {
      console.error("Erro ao salvar entrada:", error);
      alert("Erro ao registrar entrada.");
    }
  };

  return (
    <C.Container>
      <C.Title>Registrar Entrada de Pacote</C.Title>
      <C.Form onSubmit={handleSubmit}>
        <C.FormColumn>
          <label>Transportadora</label>
          <C.Select
            name="idTransportadora"
            value={formData.transportadora.idTransportadora}
            onChange={handleChange}
          >
            <option value="">Selecione a Transportadora</option>
            {transportadoras.map((t) => (
              <option key={t.idTransportadora} value={t.idTransportadora}>
                {t.nomeSocial}
              </option>
            ))}
          </C.Select>

          <label>Código de Rastreio</label>
          <C.Input
            type="text"
            name="codigoRastreio"
            value={formData.codigoRastreio}
            onChange={handleChange}
          />

          <label>Destinatário</label>
          <C.Input
            type="text"
            name="destinatario"
            value={formData.destinatario}
            onChange={handleChange}
          />

          <label>CEP</label>
          <C.Input
            type="text"
            name="cep"
            value={formData.cep}
            onChange={handleChange}
          />
        </C.FormColumn>

        <C.FormColumn>
          <label>Cidade</label>
          <C.Input
            type="text"
            name="cidade"
            value={formData.cidade}
            onChange={handleChange}
          />

          <label>Bairro</label>
          <C.Input
            type="text"
            name="bairro"
            value={formData.bairro}
            onChange={handleChange}
          />

          <label>Rua</label>
          <C.Input
            type="text"
            name="rua"
            value={formData.rua}
            onChange={handleChange}
          />

          <label>Número</label>
          <C.Input
            type="text"
            name="numeroEndereco"
            value={formData.numeroEndereco}
            onChange={handleChange}
          />
        </C.FormColumn>

        <C.Button type="submit" style={{ gridColumn: "1 / 3" }}>
          Salvar Entrada
        </C.Button>
      </C.Form>

    </C.Container>
  );
};

export default Entrada;
