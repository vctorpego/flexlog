// src/routes/index.jsx
import React, { Fragment, useEffect, useState, Suspense, lazy } from "react";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import Sidebar from "../components/Sidebar";

// Páginas da aplicação com carregamento dinâmico



const AddUsuario = lazy(() => import("../pages/AddUsuario"));

const EditUsuario = lazy(() => import("../pages/EditUsuario"));
const Home = lazy(() => import("../pages/Home"));


const Signin = lazy(() => import("../pages/Signin"));
const SwaggerPage = lazy(() => import("../pages/Swagger"));

const ListagemUsuarios = lazy(() => import("../pages/ListagemUsuarios"));
const NaoAutorizado = lazy(() => import("../pages/NaoAutorizado"));
const CadastroTela = lazy(() => import("../pages/CadastroTela"));

import PermissaoRoute from "./PermissaoRoute";

// Wrappers de segurança
const ProtectedRoute = ({ children }) => {
  const { token } = useAuth();
  if (!token) {
    return <Navigate to="/auth/login" replace />;
  }
  return children;
};

const RoutesApp = () => {
  const { token, user, signout } = useAuth();
  const [isSidebarVisible, setSidebarVisible] = useState(!!token);

  useEffect(() => {
    setSidebarVisible(!!token);
  }, [token]);

  return (
    <BrowserRouter>
      <Fragment>
        {isSidebarVisible && <Sidebar user={user} handleLogout={signout} />}

        <Suspense fallback={<div>Loading...</div>}>
          <Routes>
            {/* Home */}
            <Route
              path="/home"
              element={
                <ProtectedRoute>
                  <Home />
                </ProtectedRoute>
              }
            />       

            {/* Usuários */}
            <Route
              path="/usuarios"
              element={
                <ProtectedRoute>
                  <PermissaoRoute tela="Tela de Usuarios">
                    <ListagemUsuarios />
                  </PermissaoRoute>
                </ProtectedRoute>
              }
            />
            <Route
              path="/usuarios/adicionar"
              element={
                <ProtectedRoute>
                  <PermissaoRoute tela="Tela de Usuarios">
                    <AddUsuario />
                  </PermissaoRoute>
                </ProtectedRoute>
              }
            />
            <Route
              path="/usuarios/editar/:id"
              element={
                <ProtectedRoute>
                  <PermissaoRoute tela="Tela de Usuarios">
                    <EditUsuario />
                  </PermissaoRoute>
                </ProtectedRoute>
              }
            />

            {/* Swagger */}
            <Route
              path="/swagger"
              element={
                <ProtectedRoute>
                  <PermissaoRoute tela="Tela de Relatórios">
                    <SwaggerPage />
                  </PermissaoRoute>
                </ProtectedRoute>
              }
            />

            
            <Route
              path="/telas"
              element={
                <ProtectedRoute>
                  <CadastroTela />
                </ProtectedRoute>
              }
            />


            {/* Não autorizado */}
            <Route path="/nao-autorizado" element={<NaoAutorizado />} />

            {/* Autenticação */}
            <Route path="/auth/login" element={<Signin />} />

            {/* Fallback */}
            <Route path="*" element={<Navigate to={token ? "/home" : "/auth/login"} />} />
          </Routes>
        </Suspense>
      </Fragment>
    </BrowserRouter>
  );
};

export default RoutesApp;
