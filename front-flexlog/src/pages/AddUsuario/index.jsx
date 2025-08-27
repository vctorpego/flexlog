import React, { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import { useNavigate } from "react-router-dom";
import { ArrowLeft } from "lucide-react";
import Button from "../../components/Button";
import * as C from "./styles";

const acoes = {
  adicionar: 1,
  editar: 2,
  excluir: 3,
  visualizar: 4,
};

const AddUsuario = () => {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [telefone, setTelefone] = useState("");
  const [login, setLogin] = useState("");
  const [cnh, setCnh] = useState("");
  const [isEntregador, setIsEntregador] = useState(false);
  const [telas, setTelas] = useState([]);
  const [permissoes, setPermissoes] = useState({});
  const [hasPermission, setHasPermission] = useState(false);
  const [isSuperAdm, setIsSuperAdm] = useState(false);
  const [adminChecked, setAdminChecked] = useState(false);
  const [message, setMessage] = useState("");
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

    const decoded = jwt_decode(token);
    const userLogin = decoded.sub;

    const fetchData = async () => {
      try {
        const telaResponse = await axios.get("http://localhost:8080/tela", getRequestConfig());
        const telasBackend = telaResponse.data;
        setTelas(telasBackend);

        const permissoesIniciais = {};
        telasBackend.forEach((tela) => {
          permissoesIniciais[tela.nomeTela] = {
            adicionar: false,
            editar: false,
            excluir: false,
            visualizar: false,
          };
        });
        setPermissoes(permissoesIniciais);

        const usuarioResponse = await axios.get(
          `http://localhost:8080/usuario/id/${userLogin}`,
          getRequestConfig()
        );
        const userId = usuarioResponse.data;

        const permissionsResponse = await axios.get(
          `http://localhost:8080/permissao/telas/${userId}`,
          getRequestConfig()
        );

        const logadoResponse = await axios.get(
          `http://localhost:8080/usuario/${userId}`,
          getRequestConfig()
        );
        setIsSuperAdm(logadoResponse.data.isSuperAdm);
        setAdminChecked(false);

        const permissoesTela = permissionsResponse.data.find(
          (perm) => perm.tela === "Tela de Usuarios"
        );
        const permissoesAtuais = permissoesTela?.permissoes || [];
        const hasPostPermission = permissoesAtuais.includes("POST");
        setHasPermission(hasPostPermission);

        if (!hasPostPermission) {
          navigate("/nao-autorizado");
        }
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    };

    fetchData();
  }, [navigate]);

  useEffect(() => {
    setPermissoes((prevPermissoes) => {
      const novaPermissao = { ...prevPermissoes };

      if (novaPermissao["Tela de Meus Pacotes"]) {
        Object.keys(acoes).forEach((acao) => {
          novaPermissao["Tela de Meus Pacotes"][acao] = isEntregador;
        });
      }

      return novaPermissao;
    });
  }, [isEntregador]);

  const toggleAllCheckboxes = (checked) => {
    const novaPermissao = {};
    const todasTelas = telas
      .filter((t) => t.nomeTela !== "Tela de Tela")
      .map((t) => t.nomeTela);

    if (isSuperAdm) todasTelas.push("Tela de Usuarios");

    todasTelas.forEach((tela) => {
      novaPermissao[tela] = {};
      Object.keys(acoes).forEach((acao) => {
        novaPermissao[tela][acao] = checked;
      });
    });
    setPermissoes(novaPermissao);
  };

  const handleAdminCheckboxChange = () => {
    const novoValor = !adminChecked;
    setAdminChecked(novoValor);
    toggleAllCheckboxes(novoValor);
  };

  // Atualizado para marcar visualizar junto com adicionar/editar/excluir
  const handleCheckboxChange = (telaNome, acao) => {
    setPermissoes((prev) => {
      const telaPermissoes = prev[telaNome] || {};
      const novoValor = !telaPermissoes[acao];

      const novasPermissoes = {
        ...prev,
        [telaNome]: {
          ...telaPermissoes,
          [acao]: novoValor,
        },
      };

      if (
        novoValor &&
        (acao === "adicionar" || acao === "editar" || acao === "excluir")
      ) {
        novasPermissoes[telaNome]["visualizar"] = true;
      }


      return novasPermissoes;
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!nome || !email || !senha || !telefone || !login) {
      alert("Preencha todos os campos.");
      return;
    }

    if (isEntregador && !cnh.trim()) {
      alert("Preencha o campo CNH para entregadores.");
      return;
    }

    const token = getToken();
    if (!token) return;

    try {
      const payload = {
        nomeUsuario: nome,
        emailUsuario: email,
        telefoneUsuario: telefone,
        login,
        senhaUsuario: senha,
        cnh: isEntregador ? cnh : undefined,
        isAdm: adminChecked,
        isSuperAdm: false,
      };

      const url = isEntregador
        ? "http://localhost:8080/entregador"
        : "http://localhost:8080/usuario";

      const response = await axios.post(url, payload, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (response.status === 200 || response.status === 201) {
        alert("Usuário adicionado com sucesso!");

        const usuarioId = response.data.idUsuario;
        if (!usuarioId) throw new Error("ID do usuário não retornado.");

        const permissaoPromises = [];
        for (const tela of telas) {
          const telaNome = tela.nomeTela;
          for (const acao in acoes) {
            if (permissoes[telaNome]?.[acao]) {
              permissaoPromises.push(
                axios.post(
                  `http://localhost:8080/usuario/${usuarioId}/permissao`,
                  null,
                  {
                    params: {
                      idTela: tela.idTela,
                      idPermissao: acoes[acao],
                    },
                    headers: { Authorization: `Bearer ${token}` },
                  }
                )
              );
            }
          }
        }

        await Promise.all(permissaoPromises);
        setTimeout(() => navigate("/usuarios"), 1500);
      }
    } catch (error) {
      console.error("Erro ao adicionar usuário:", error);
      if (error.response?.status === 409) {
        alert("Usuário já cadastrado.");
      } else {
        alert("Erro ao adicionar usuário.");
      }
    }
  };

  if (!hasPermission) {
    return <div>Você não tem permissão para acessar esta página.</div>;
  }

  return (
    <C.Container>

      <C.Title>Adicionar Usuário</C.Title>

      {message && <C.Message type={messageType}>{message}</C.Message>}

      <C.Form onSubmit={handleSubmit}>
        <C.InputsRow columns={2}>
          <C.InputGroup>
            <C.Label>Nome</C.Label>
            <C.Input value={nome} onChange={(e) => setNome(e.target.value)} placeholder="Nome completo" />
          </C.InputGroup>
          <C.InputGroup>
            <C.Label>Email</C.Label>
            <C.Input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="email@exemplo.com" />
          </C.InputGroup>
        </C.InputsRow>

        <C.InputsRow columns={2}>
          <C.InputGroup>
            <C.Label>Telefone</C.Label>
            <C.Input value={telefone} onChange={(e) => setTelefone(e.target.value)} placeholder="(XX) XXXX-XXXX" />
          </C.InputGroup>
          <C.InputGroup>
            <C.Label>CNH</C.Label>
            <C.Input
              value={cnh}
              onChange={(e) => setCnh(e.target.value)}
              placeholder="CNH"
              disabled={!isEntregador}
            />
          </C.InputGroup>
        </C.InputsRow>

        <C.InputsRow columns={2}>
          <C.InputGroup>
            <C.Label>Login</C.Label>
            <C.Input value={login} onChange={(e) => setLogin(e.target.value)} placeholder="Nome de usuário" />
          </C.InputGroup>
          <C.InputGroup>
            <C.Label>Senha</C.Label>
            <C.Input value={senha} onChange={(e) => setSenha(e.target.value)} placeholder="Senha segura" type="password" />
          </C.InputGroup>
        </C.InputsRow>

        <C.InputGroup>
          <C.AdminCheckbox>
            <label>
              <input type="checkbox" checked={isEntregador} onChange={(e) => setIsEntregador(e.target.checked)} />
              Entregador
            </label>
          </C.AdminCheckbox>
        </C.InputGroup>

        {isSuperAdm && (
          <C.AdminCheckbox>
            <label>
              <input type="checkbox" checked={adminChecked} onChange={handleAdminCheckboxChange} />
              Administrador (Super Usuário)
            </label>
          </C.AdminCheckbox>
        )}

        <C.PermissionsSection>
          <h3>Permissões</h3>
          <C.CardsContainer>
            {telas.map((tela) => (
              <C.Card key={tela.idTela}>
                <C.CardTitle>{tela.nomeTela}</C.CardTitle>
                <C.PermissionsList>
                  {Object.keys(acoes).map((acao) => (
                    <C.PermissionItem key={acao}>
                      <label>
                        <input
                          type="checkbox"
                          checked={permissoes[tela.nomeTela]?.[acao] || false}
                          onChange={() => handleCheckboxChange(tela.nomeTela, acao)}
                        />
                        {acao.charAt(0).toUpperCase() + acao.slice(1)}
                      </label>
                    </C.PermissionItem>
                  ))}
                </C.PermissionsList>
              </C.Card>
            ))}
          </C.CardsContainer>
        </C.PermissionsSection>

        <C.AddButtonWrapper>
          <C.Button type="submit">Adicionar Usuário</C.Button>
        </C.AddButtonWrapper>

      </C.Form>
    </C.Container>
  );
};

export default AddUsuario;