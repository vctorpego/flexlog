import React from "react";
import RoutesApp from "./routes";  // As rotas da sua aplicação
import { AuthProvider } from "./contexts/auth";  // O contexto de autenticação

export function App() {
  console.log("App está carregando!");

  return (
    <>
      <AuthProvider> {/* Envolvendo toda a aplicação com o AuthProvider */}
        <RoutesApp /> {/* Renderizando as rotas da aplicação */}
      </AuthProvider>
    </>
  );
}
