import styled from "styled-components";

 export const NavBarContainer = styled.nav`
  height: 50px;
  width: 100%;
  background-color: #2283C8;
  display: flex;
  align-items: center;
  padding: 0 1rem;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1000;
  box-shadow: 0 2px 4px rgba(0,0,0,0.3);
`;

export const ToggleButton = styled.button`
  margin-right: 1rem;
  background: none;
  border: none;
  color: #fff;
  font-size: 1.25rem;
  cursor: pointer;
  user-select: none;

  &:hover {
    color: #ddd;
  }
`;
