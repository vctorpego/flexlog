import React, { useState } from "react";
import axios from 'axios';
import Input from "../../components/Input";
import Button from "../../components/Button";
import * as C from "./styles";
import { useNavigate } from "react-router-dom";
import useAuth from "../../hooks/useAuth";
import jwtDecode from "jwt-decode";
import imgLogin from "../../pages/Signin/login.png";

const Signin = () => {
  const { login } = useAuth(); // Obtendo o login do contexto
  const navigate = useNavigate();

  const [loginInput, setLogin] = useState("");
  const [senha, setSenha] = useState("");
  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const handleLogin = async (event) => {
    event.preventDefault();

    const loginData = {
      login: loginInput,
      senhaUsuario: senha,
    };

    try {
      const response = await axios.post("http://localhost:8080/auth/login", loginData);

      if (response.data && response.data.token) {
        const token = response.data.token;
        localStorage.setItem("token", token);
        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;

        // Acessar as permissões após o login
        const decodedToken = jwtDecode(token);
        const userId = decodedToken.sub;

        // 1. Obter o ID do usuário
        const responseUser = await axios.get(`http://localhost:8080/usuario/id/${loginInput}`, {
          headers: {
            "Authorization": `Bearer ${token}`,
          },
        });

        const userIdFromResponse = responseUser.data; // Agora, a resposta é apenas o ID

        // Verifique se o valor retornado é um número e é válido
        if (typeof userIdFromResponse === "number" && userIdFromResponse > 0) {
        } else {
          setError("Usuário não encontrado ou ID inválido.");
          return;
        }

        // 2. Usar o ID do usuário para buscar as permissões
        const responsePermissoes = await axios.get(`http://localhost:8080/permissao/telas/${userIdFromResponse}`, {
          headers: {
            "Authorization": `Bearer ${token}`,
          },
        });

        const permissoes = responsePermissoes.data;

        // Encontrar a primeira tela que o usuário tem permissão para acessar
        const telaComPermissao = permissoes.find(p => p.permissoes.includes('GET'));

        if (telaComPermissao) {
          navigate(telaComPermissao.urlTela || "/"); // Navega para a primeira tela com permissão
        } else {
          navigate("/"); // Caso o usuário não tenha permissão para nenhuma tela
        }

        login(token); // Atualiza o estado de login aqui
      } else {
        setError("Erro de autenticação. Verifique suas credenciais.");
      }
    } catch (error) {
      console.error("Erro ao realizar login:", error);
      setError("Erro ao tentar se conectar com o servidor.");
    }
  };

  return (
    <C.Wrapper>
      <C.Left>
        <img src={imgLogin} alt="Login Ilustração" />
      </C.Left>
      <C.Right>
        <C.FormBox>
          <h2>LOGIN</h2>
          <div className="inputs">
            <label htmlFor="usuario">Usuário</label>
            <C.LoginInput
              id="usuario"
              type="text"
              placeholder="Digite seu usuário"
              value={loginInput}
              onChange={(e) => setLogin(e.target.value)}
            />

            <label htmlFor="senha">Senha</label>
            <C.LoginInput
              id="senha"
              type="password"
              placeholder="Digite sua senha"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
            />
          </div>
          {error && <C.labelError>{error}</C.labelError>}
          <Button Text="Entrar" onClick={handleLogin} />
        </C.FormBox>
      </C.Right>
    </C.Wrapper>
  );
};

export default Signin;
