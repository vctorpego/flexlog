import styled from "styled-components";

export const Container = styled.div`
  max-width: 900px;
  margin: 60px auto;
  padding: 30px;
  background: #f9f9f9;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
`;

export const Title = styled.h2`
  text-align: center;
  margin-bottom: 24px;
  color: #333;
  font-size: 24px;
`;

export const Form = styled.form`
  display: grid;
  grid-template-columns: 1fr 1fr; /* duas colunas */
  gap: 24px; /* espaço entre colunas */
`;

export const FormColumn = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

export const Input = styled.input`
  padding: 12px 14px;
  font-size: 15px;
  border: 1px solid #ccc;
  border-radius: 6px;
  outline: none;
  transition: border 0.3s;

  &:focus {
    border-color: #007bff;
  }
`;

export const Select = styled.select`
  padding: 12px 14px;
  font-size: 15px;
  border: 1px solid #ccc;
  border-radius: 6px;
  outline: none;
  transition: border 0.3s;

  &:focus {
    border-color: #007bff;
  }
`;

export const Button = styled.button`
  padding: 14px;
  font-size: 16px;
  background-color: #007bff;
  border: none;
  color: white;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.25s;

  &:hover {
    background-color: #0056b3;
  }

  &:disabled {
    background-color: #aaa;
    cursor: not-allowed;
  }
`;
