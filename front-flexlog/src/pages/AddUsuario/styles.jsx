import styled from "styled-components";

export const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 90vh;
  width: 100%;
  max-width: 900px; /* limite razoável para formulários */
  text-align: center;
  flex-direction: column;
  padding: 20px;
  margin: 0 auto; /* centraliza no pai */
  box-sizing: border-box;
  border-radius: 8px;
`;

export const Title = styled.h2`
  font-size: 28px;
  color: #003366; /* azul escuro */
  margin-bottom: 24px;
`;

export const Form = styled.form`
  background-color: white;
  padding: 30px 40px;
  border-radius: 8px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 25px;
`;

export const InputGroup = styled.div`
  display: flex;
  gap: 24px;
  flex-wrap: wrap;
`;

export const Label = styled.label`
  display: block;
  margin-bottom: 6px;
  font-weight: 600;
  color: #004080;
  text-align: left;
`;

export const InputWrapper = styled.div`
  flex: 1;
  min-width: 280px;
  display: flex;
  flex-direction: column;
`;

export const Input = styled.input`
  padding: 12px;
  border: 1.5px solid #a0bce6;
  border-radius: 6px;
  font-size: 1rem;
  width: 100%;
  transition: border-color 0.2s ease;

  &:focus {
    border-color: #007bff;
    outline: none;
  }
`;

export const Button = styled.button`
  padding: 14px;
  background-color: #007bff;  /* azul padrão */
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 17px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #0056b3;  /* azul mais escuro no hover */
  }
`;

export const CheckboxContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-top: 10px;
`;

export const CheckboxLabel = styled.label`
  display: flex;
  align-items: center;
  gap: 8px;
  color: #003366;
  font-weight: 500;
`;

export const Checkbox = styled.input`
  transform: scale(1.2);
  cursor: pointer;
`;
  
export const PermissoesContainer = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
`;
