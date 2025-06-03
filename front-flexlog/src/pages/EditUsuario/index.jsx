import React, { useState, useEffect } from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";
import { useNavigate, useParams } from "react-router-dom";
import * as C from "./styles";

const acoes = {
  adicionar: 1,
  editar: 2,
  excluir: 3,
  visualizar: 4,
};

const EditUsuario = () => {
  const { id } = useParams();
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [telefone, setTelefone] = useState("");
  const [login, setLogin] = useState("");
  const [cnh, setCnh] = useState("");
  const [isEntregador, setIsEntregador] = useState(false);
  const [permissoes, setPermissoes] = useState({});
  const [telas, setTelas] = useState([]);
  const [hasPermission, setHasPermission] = useState(false);
  const [isSuperAdm, setIsSuperAdm] = useState(false);
  const [adminChecked, setAdminChecked] = useState(false);


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

    const fetchUserData = async () => {
      try {
        // Pega ID do usuário logado para verificar permissões
        const userResponse = await axios.get(
          `http://localhost:8080/usuario/id/${userLogin}`,
          getRequestConfig()
        );

        const currentUserId = userResponse.data;

        // Pega permissões do usuário logado
        const permissionsResponse = await axios.get(
          `http://localhost:8080/permissao/telas/${currentUserId}`,
          getRequestConfig()
        );

        // Dados do usuário logado para saber se é super admin
        const logadoResponse = await axios.get(
          `http://localhost:8080/usuario/${currentUserId}`,
          getRequestConfig()
        );

        setIsSuperAdm(logadoResponse.data.isSuperAdm);

        // Verifica se o usuário tem permissão PUT na tela de usuários
        const telaUsuarios = permissionsResponse.data.find(
          (perm) => perm.tela === "Tela de Usuarios"
        );

        const temPermissaoPUT = telaUsuarios?.permissoes?.includes("PUT") || false;
        setHasPermission(temPermissaoPUT);

        if (!temPermissaoPUT) {
          navigate("/nao-autorizado");
          return;
        }

        // Dados do usuário que está sendo editado
        const userToEditResponse = await axios.get(
          `http://localhost:8080/usuario/${id}`,
          getRequestConfig()
        );

        const {
          nomeUsuario,
          emailUsuario,
          telefoneUsuario,
          login,
          isAdm,
        } = userToEditResponse.data;

        setNome(nomeUsuario);
        setEmail(emailUsuario);
        setTelefone(telefoneUsuario);
        setLogin(login);
        setAdminChecked(isAdm);

        // Buscar dados do entregador pelo ID (caso exista)
        try {
          const entregadorResponse = await axios.get(
            `http://localhost:8080/entregador/${id}`,
            getRequestConfig()
          );
          const { cnh } = entregadorResponse.data;
          setCnh(cnh || "");
          setIsEntregador(!!cnh);

        } catch (error) {
          setCnh("");
          setIsEntregador(false);

        }


        // Permissões atuais do usuário sendo editado
        const permissoesUsuario = await axios.get(
          `http://localhost:8080/permissao/telas/${id}`,
          getRequestConfig()
        );

        const permissoesObj = {};
        permissoesUsuario.data.forEach((telaPermissao) => {
          permissoesObj[telaPermissao.tela] = {
            adicionar: telaPermissao.permissoes.includes("POST"),
            editar: telaPermissao.permissoes.includes("PUT"),
            excluir: telaPermissao.permissoes.includes("DELETE"),
            visualizar: telaPermissao.permissoes.includes("GET"),
          };
        });

        // Buscar todas as telas para mostrar as permissões
        const telasResponse = await axios.get("http://localhost:8080/tela", getRequestConfig());
        const todasAsTelas = telasResponse.data;
        setTelas(todasAsTelas);

        // Preencher permissões para telas que não tem no usuário (false)
        todasAsTelas.forEach((tela) => {
          if (!permissoesObj[tela.nomeTela]) {
            permissoesObj[tela.nomeTela] = {
              adicionar: false,
              editar: false,
              excluir: false,
              visualizar: false,
            };
          }
        });

        setPermissoes(permissoesObj);
      } catch (error) {
        console.error("Erro ao carregar dados do usuário:", error);
        alert("Erro ao carregar dados do usuário.");
      }
    };

    fetchUserData();
  }, [id, navigate]);

  const toggleAllCheckboxes = (checked) => {
    const novasPermissoes = {};
    telas.forEach((tela) => {
      if (tela.nomeTela === "Tela de Tela") return; // Ignora a tela de controle
      novasPermissoes[tela.nomeTela] = {
        adicionar: checked,
        editar: checked,
        excluir: checked,
        visualizar: checked,
      };
    });
    setPermissoes(novasPermissoes);
  };

  const handleAdminCheckboxChange = () => {
    const novoValor = !adminChecked;
    setAdminChecked(novoValor);

    if (novoValor) {
      // Marca todas as permissões se admin for marcado
      const novasPermissoes = {};
      telas.forEach((tela) => {
        if (tela.nomeTela === "Tela de Tela") return;
        novasPermissoes[tela.nomeTela] = {
          adicionar: true,
          editar: true,
          excluir: true,
          visualizar: true,
        };
      });
      setPermissoes(novasPermissoes);
    } else {
      toggleAllCheckboxes(false);
    }
  };

  const handleCheckboxChange = (telaNome, acao) => {
    setPermissoes((prevPermissoes) => ({
      ...prevPermissoes,
      [telaNome]: {
        ...prevPermissoes[telaNome],
        [acao]: !prevPermissoes[telaNome]?.[acao],
      },
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!nome || !email || !telefone || !login) {
      alert("Preencha todos os campos.");
      return;
    }

    const token = getToken();
    if (!token) return;

    try {
      // Atualiza dados do usuário
      let updateResponse;

      if (isEntregador) {

        updateResponse = await axios.put(
          `http://localhost:8080/entregador/alterar/${id}`,
          {
            nomeUsuario: nome,
            emailUsuario: email,
            telefoneUsuario: telefone,
            login: login,
            cnh: cnh,
            isAdm: adminChecked
          },
          getRequestConfig()
        );
      } else {
        updateResponse = await axios.put(
          `http://localhost:8080/usuario/${id}`,
          {
            nomeUsuario: nome,
            emailUsuario: email,
            telefoneUsuario: telefone,
            login: login,
            isAdm: adminChecked,
          },
          getRequestConfig()
        );
      }



      if (updateResponse.status === 200) {
        // Apaga permissões antigas
        await axios.delete(`http://localhost:8080/usuario/tela/${id}`, getRequestConfig());

        const usuarioId = updateResponse.data.idUsuario;
        const permissaoRequests = [];

        // Envia permissões atualizadas
        for (const [nomeTela, permissoesTela] of Object.entries(permissoes)) {
          const telaId = telas.find((t) => t.nomeTela === nomeTela)?.idTela;
          if (!telaId) continue;

          for (const [acao, ativa] of Object.entries(permissoesTela)) {
            if (ativa) {
              permissaoRequests.push(
                axios.post(
                  `http://localhost:8080/usuario/${usuarioId}/permissao`,
                  null,
                  {
                    params: {
                      idTela: telaId,
                      idPermissao: acoes[acao],
                    },
                    headers: {
                      Authorization: `Bearer ${token}`,
                    },
                  }
                )
              );
            }
          }
        }

        await Promise.all(permissaoRequests);
        alert("Usuário atualizado com sucesso!");
        navigate("/usuarios");
      } else {
        alert("Erro ao atualizar usuário.");
      }
    } catch (error) {
      console.error("Erro ao atualizar usuário:", error);
      alert("Erro ao atualizar usuário.");
    }
  };

  if (!hasPermission) {
    return <div>Você não tem permissão para acessar esta página.</div>;
  }

  return (
    <C.Container>
      <C.Title>Editar Usuário</C.Title>
      <C.Form onSubmit={handleSubmit}>
        <C.Input
          type="text"
          placeholder="Nome"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
        />
        <C.Input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <C.Input
          type="text"
          placeholder="Telefone"
          value={telefone}
          onChange={(e) => setTelefone(e.target.value)}
        />
        <C.Input
          type="text"
          placeholder="Login"
          value={login}
          onChange={(e) => setLogin(e.target.value)}
        />
        <C.Input
          type="text"
          placeholder="CNH"
          value={cnh}
          onChange={(e) => setCnh(e.target.value)}
          disabled={!isEntregador}
        />

        <C.CheckboxContainer>
          <label>
            <input
              type="checkbox"
              checked={isEntregador}
              onChange={(e) => setIsEntregador(e.target.checked)}
              disabled={true}
            />
            Entregador
          </label>
        </C.CheckboxContainer>

        {isSuperAdm && (
          <C.CheckboxContainer>
            <label>
              <input
                type="checkbox"
                checked={adminChecked}
                onChange={handleAdminCheckboxChange}
              />
              Administrador
            </label>
          </C.CheckboxContainer>
        )}

        {telas
          .filter((tela) => tela.nomeTela !== "Tela de Tela")
          .map((tela) => (
            <div key={tela.idTela}>
              <h3>{tela.nomeTela}</h3>
              <C.CheckboxContainer>
                <label>
                  <input
                    type="checkbox"
                    checked={permissoes[tela.nomeTela]?.adicionar || false}
                    onChange={() => handleCheckboxChange(tela.nomeTela, "adicionar")}
                  />
                  Adicionar
                </label>
                <label>
                  <input
                    type="checkbox"
                    checked={permissoes[tela.nomeTela]?.editar || false}
                    onChange={() => handleCheckboxChange(tela.nomeTela, "editar")}
                  />
                  Editar
                </label>
                <label>
                  <input
                    type="checkbox"
                    checked={permissoes[tela.nomeTela]?.excluir || false}
                    onChange={() => handleCheckboxChange(tela.nomeTela, "excluir")}
                  />
                  Excluir
                </label>
                <label>
                  <input
                    type="checkbox"
                    checked={permissoes[tela.nomeTela]?.visualizar || false}
                    onChange={() => handleCheckboxChange(tela.nomeTela, "visualizar")}
                  />
                  Visualizar
                </label>
              </C.CheckboxContainer>
            </div>
          ))}

        <C.Button type="submit">Salvar</C.Button>
      </C.Form>
    </C.Container>
  );
};

export default EditUsuario;
