import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import SwaggerUI from 'swagger-ui-react';
import jwt_decode from 'jwt-decode';
import axios from 'axios';
import 'swagger-ui-react/swagger-ui.css';

const SwaggerPage = () => {
  const navigate = useNavigate();
  const [hasPermission, setHasPermission] = useState(null); // null = carregando, true = autorizado, false = não autorizado

  const getToken = () => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/auth/login');
      return null;
    }
    try {
      const decoded = jwt_decode(token);
      if (decoded.exp < Date.now() / 1000) {
        localStorage.removeItem('token');
        navigate('/auth/login');
        return null;
      }
      return token;
    } catch (error) {
      localStorage.removeItem('token');
      navigate('/auth/login');
      return null;
    }
  };

  const getRequestConfig = () => {
    const token = localStorage.getItem('token');
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {};
  };

  useEffect(() => {
    const token = getToken();
    if (!token) return;

    const decoded = jwt_decode(token);
    const userLogin = decoded.sub;

    const verificarPermissao = async () => {
      try {
        const userResponse = await axios.get(`http://localhost:8080/usuario/id/${userLogin}`, getRequestConfig());
        const userId = userResponse.data;

        const permissaoResponse = await axios.get(`http://localhost:8080/permissao/telas/${userId}`, getRequestConfig());
        const permissoes = permissaoResponse.data;

        const telaPermissao = permissoes.find(p => p.tela === "Tela de Tela");
        const temGet = telaPermissao?.permissoes?.includes("GET");

        setHasPermission(temGet);

        if (!temGet) {
          navigate("/nao-autorizado");
        }
      } catch (err) {
        console.error("Erro ao verificar permissões:", err);
      }
    };

    verificarPermissao();
  }, [navigate]);


  if (hasPermission === null) {
    return <div>Verificando permissões...</div>;
  }

  if (!hasPermission) {
    return null; 
  }

  return (
    <div style={{ height: '100vh' }}>
      <SwaggerUI
        url="http://localhost:8080/v3/api-docs"
        requestInterceptor={(request) => {
          const token = localStorage.getItem('token');
          if (token) {
            request.headers['Authorization'] = `Bearer ${token}`;
          }
          return request;
        }}
      />
    </div>
  );
};

export default SwaggerPage;
