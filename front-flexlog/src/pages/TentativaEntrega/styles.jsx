import styled from "styled-components";

export const Container = styled.div`
  width: 100%;
  max-width: 600px;
  margin: 40px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

export const Title = styled.h1`
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

/* Grupo para inputs lado a lado, flexível */
export const InputGroup = styled.div`
  display: flex;
  gap: 20px;
  justify-content: space-between;
`;

/* Wrapper individual para label + input */
export const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
`;

/* Label estilizado */
export const Label = styled.label`
  font-size: 14px;
  margin-bottom: 8px;
  color: #555;
`;

/* Input e Select estilos compartilhados */
const sharedInputStyles = `
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 4px;
  outline: none;
  transition: border-color 0.3s;

  &:focus {
    border-color: #007bff;
  }

  &::placeholder {
    color: #aaa;
  }
`;

export const Input = styled.input`
  ${sharedInputStyles}
`;

export const Select = styled.select`
  ${sharedInputStyles}
`;

/* Botão principal */
export const Button = styled.button`
  padding: 10px 20px;
  font-size: 16px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;

  &:hover {
    background-color: #0056b3;
  }
`;

/* Estilos para grupo de permissões e checkboxes */
export const PermissoesContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-top: 10px;
`;

export const CheckboxContainer = styled.div`
  display: flex;
  gap: 12px;
  margin-top: 6px;
`;

export const CheckboxLabel = styled.label`
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  color: #333;
`;

export const Checkbox = styled.input`
  width: 16px;
  height: 16px;
  cursor: pointer;
`;
