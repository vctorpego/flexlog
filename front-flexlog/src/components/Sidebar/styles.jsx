import styled from "styled-components";

export const SidebarContainer = styled.div`
  width: ${(props) => (props.collapsed ? "0" : "250px")};
  transition: width 0.3s ease;
  height: calc(100vh - 50px); /* Ajuste aqui */
  background-color: #FFFFFF; 
  position: fixed;
  top: 50px; /* Começa abaixo da navbar */
  left: 0;
  z-index: 1000;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-top: 15px;
`;
export const Logo = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 30px;
  width: 100%;
  padding-left: 17px;
  svg {
    color: #2283C8;
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
  color: #2283C8;
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
  color: #2283C8;
  font-size: ${(props) => (props.collapsed ? "0.8rem" : "16px")};
  cursor: pointer;
  background-color: ${(props) =>
    props.isActive && !props.collapsed ? "#B3D7F2" : "transparent"}; 

  &:hover {
    background-color: #B3D7F2;  
  }

  svg {
    margin-right: ${(props) => (props.collapsed ? "0" : "10px")};
  }
`;

export const UserInfo = styled.div`
  padding: 12px 20px 8px 20px;
  text-align: left;
  color: #2283C8;
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
  margin-left: ${(props) => (props.collapsed ? "60px" : "250px")};
  transition: margin-left 0.3s ease;
  padding: 20px;
  width: calc(100% - ${(props) => (props.collapsed ? "60px" : "250px")});
  height: 100vh;
  overflow-y: auto;
  position: relative;
  z-index: 1; 
  margin-top: 15px;
  padding-left: ${(props) => (props.collapsed ? "60px" : "250px")}; /* Esse ajuste é importante se houver algum problema com o padding */
`;

