// src/routes/index.jsx
import React, { Fragment, useEffect, useState, Suspense, lazy } from "react";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import Layout from "../components/Layout";

const AddUsuario = lazy(() => import("../pages/AddUsuario"));
const EditUsuario = lazy(() => import("../pages/EditUsuario"));
const EditTransp = lazy(() => import("../pages/EditTransportadora"));
const Home = lazy(() => import("../pages/Home"));
const Signin = lazy(() => import("../pages/Signin"));
const SwaggerPage = lazy(() => import("../pages/Swagger"));
const ListagemUsuarios = lazy(() => import("../pages/ListagemUsuarios"));
const NaoAutorizado = lazy(() => import("../pages/NaoAutorizado"));
const CadastroTela = lazy(() => import("../pages/CadastroTela"));
const ListTransp = lazy(() => import("../pages/ListagemTransportadoras"));
const AddTransp = lazy(() => import("../pages/AddTransportadora"));
const Entrada = lazy(() => import("../pages/Entrada"));
const Saida = lazy(() => import("../pages/Saida"));
const ListPacotes = lazy(() => import("../pages/ListagemPacotes"));
const MeusPacotes = lazy(() => import("../pages/MeusPacotes"));
const TentativaEntrega = lazy(() => import("../pages/TentativaEntrega"));
const Rollback = lazy(() => import("../pages/Rollback"));
import FazerAgendamento from "../pages/FazerAgendamento";

import PermissaoRoute from "./PermissaoRoute";

// Wrapper de segurança
const ProtectedRoute = ({ children }) => {
  const { token } = useAuth();
  if (!token) {
    return <Navigate to="/auth/login" replace />;
  }
  return children;
};

const RoutesApp = () => {
  const { token } = useAuth();

  return (
    <BrowserRouter>
      <Fragment>
        <Suspense fallback={<div>Loading...</div>}>
          <Routes>
            {/* Rotas públicas */}
            <Route path="/auth/login" element={<Signin />} />
            <Route path="/nao-autorizado" element={<NaoAutorizado />} />

            {/* Rotas protegidas que usam o Layout */}
            <Route
              path="/home"
              element={
                <ProtectedRoute>
                  <Layout>
                    <Home />
                  </Layout>
                </ProtectedRoute>
              }
            />

            <Route
              path="/usuarios"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Usuarios">
                      <ListagemUsuarios />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />

            <Route
              path="/usuarios/adicionar"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Usuarios">
                      <AddUsuario />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />

            <Route
              path="/usuarios/editar/:id"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Usuarios">
                      <EditUsuario />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />

            <Route
              path="/swagger"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Tela">
                      <SwaggerPage />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />

            <Route
              path="/entrada"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Entrada">
                      <Entrada />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/saida/:idPacote"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Entrada">
                      <Saida />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/tentativa-entrega/:id"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Meus Pacotes">
                      <TentativaEntrega />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/pacotes"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Entrada">
                      <ListPacotes />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/meus-pacotes"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Meus Pacotes">
                      <MeusPacotes />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/rollback"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Rollback">
                      <Rollback />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />

            <Route
              path="/transportadoras"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Transportadoras">
                      <ListTransp />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/transportadoras/adicionar"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Transportadoras">
                      <AddTransp />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/transportadoras/editar/:id"
              element={
                <ProtectedRoute>
                  <Layout>
                    <PermissaoRoute tela="Tela de Transportadoras">
                      <EditTransp />
                    </PermissaoRoute>
                  </Layout>
                </ProtectedRoute>
              }
            />

            <Route
              path="/fazer-agendamento"
              element={
                <ProtectedRoute>
                  <Layout>
                    <FazerAgendamento />
                  </Layout>
                </ProtectedRoute>
              }
            />
            {/* Rota fallback */}
            <Route
              path="*"
              element={<Navigate to={token ? "/home" : "/auth/login"} replace />}
            />
          </Routes>
        </Suspense>
      </Fragment>
    </BrowserRouter>
  );
};

export default RoutesApp;
