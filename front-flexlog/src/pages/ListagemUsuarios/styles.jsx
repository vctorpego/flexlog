import styled from 'styled-components';

// Container para o layout flexível
export const Container = styled.div`
  display: flex;
  height: 100vh;
  transition: all 0.3s ease;
`;


// Conteúdo que ocupa o restante da tela
export const Content = styled.div`
  flex-grow: 1; /* Faz com que o conteúdo ocupe o espaço restante */
  margin-left: ${(props) => (props.collapsed ? '70px' : '250px')}; /* Ajusta o conteúdo dependendo do estado da Sidebar */
  padding: 20px;
  height: 100vh;
  overflow: auto;
  transition: margin-left 0.3s ease, width 0.3s ease; /* Animação suave */

  /* Para dispositivos menores, a sidebar desaparece e o conteúdo ocupa toda a tela */
  @media (max-width: 768px) {
    margin-left: 0;
    width: 100%;
    padding: 10px;
  }
`;

export const Title = styled.h2`
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
`;

export const TableContainer = styled.div`
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow-x: auto;
`;

export const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  text-align: left;
  margin: 20px 0;
`;

export const TableHeader = styled.thead`
  background-color: #f4f4f4;
`;

export const TableRow = styled.tr`
  border-bottom: 1px solid #ddd;

  &:hover {
    background-color: #f9f9f9;
  }
`;

export const TableCell = styled.td`
  padding: 12px;
  text-align: left;
  font-size: 16px;
  color: #222;
`;

export const TableHeaderCell = styled.th`
  padding: 12px;
  text-align: left;
  font-size: 16px;
  font-weight: bold;
  color: #333;
`;

export const ButtonExcluir = styled.button`
  background-color: #ff4d4f;
  color: #fff;
  border: none;
  padding: 6px 12px;
  font-size: 14px;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.2s ease;

  &:hover {
    background-color: #ff7875;
  }
`;

export const ModalContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: ${(props) => (props.open ? 'flex' : 'none')};
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
`;

export const ModalContent = styled.div`
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  max-width: 400px;
  width: 100%;
  text-align: center;
`;

export const ModalButton = styled.button`
  background-color: #4caf50;
  color: white;
  padding: 10px 20px;
  margin: 10px 5px;
  border: none;
  border-radius: 4px;
  cursor: pointer;

  &:hover {
    background-color: #45a049;
  }
`;

export const ModalCloseButton = styled(ModalButton)`
  background-color: #ccc;
  color: #333;

  &:hover {
    background-color: #bbb;
  }
`;
