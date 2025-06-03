import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 90vh;
  width: 100%;  /* Aqui você pode ajustar para o que precisar */
  max-width: 1800px; /* opcional, para limitar largura */
  text-align: center;
  flex-direction: column;

`;


export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 12px;
  width: 300px;
`;

export const Input = styled.input`
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 1rem;
`;

export const Button = styled.button`
  padding: 10px;
  border: none;
  background-color: #2283C8;
  color: white;
  font-weight: bold;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.2s ease;

  &:hover {
    background-color: red;
  }
`;
