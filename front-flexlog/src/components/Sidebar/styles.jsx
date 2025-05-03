import styled from "styled-components";

export const SidebarContainer = styled.div`
  width: ${(props) => (props.collapsed ? "60px" : "250px")};
  height: 100vh;
  background-color: #007bff; /* Cor de fundo da Sidebar */
  padding-top: 15px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  position: fixed;
  transition: width 0.3s ease; /* Animação ao expandir/recolher */
`;

export const Logo = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 30px;
  width: 100%;
    padding-left: 17px;
    svg {
    color: white;
  }
  
`;

export const FooterContainer = styled.div`
  margin-top: auto;
  width: 100%;
  display: flex;
  flex-direction: column;
`;

export const LogoutContainer = styled.div`
  margin-top: auto;
  width: 100%;
  padding-bottom: ${(props) => (props.collapsed ? "10px" : "20px")};
`;


export const LogoText = styled.h1`
  color: #f0f0f0;
  font-size: 24px;
  margin-left: 4px;
  margin-top: 1px;
  white-space: nowrap; /* Evita quebra de linha no nome */
`;



export const Menu = styled.div`
  width: 100%;
  margin-bottom: 20px;
`;


export const MenuItem = styled.div`
  display: flex;
  align-items: center;
  padding: 13px;
  color: white;
  font-size: ${(props) => (props.collapsed ? "0.8rem" : "18px")};
  cursor: pointer;
  background-color: ${(props) =>
    props.isActive && !props.collapsed ? "#0056b3" : "transparent"};

  &:hover {
    background-color: #0069d9;
  }

  svg {
    margin-right: ${(props) => (props.collapsed ? "0" : "10px")};
  }
`;




export const UserInfo = styled.div`
  padding: 12px 20px 8px 20px; 
  text-align: left;
  color: white;
`;


export const UserName = styled.h3`
  font-size: 15px;
  margin: 5px 0;
`;

export const UserEmail = styled.p`
  font-size: 14px;
  margin: 0;
  opacity: 0.7;
`;

export const MainContent = styled.div`
  margin-left: ${(props) => (props.collapsed ? "60px" : "250px")}; /* Ajusta conforme o tamanho da Sidebar */
  transition: margin-left 0.3s ease; /* Animação para a transição */
  padding: 20px;
  width: 100%;
  height: 100vh;
  overflow-y: auto;
`;

