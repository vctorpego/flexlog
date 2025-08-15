import styled from "styled-components";

export const Container = styled.div`
  width: 100%;
  padding: 20px;
  min-height: 100vh;
  background-color: #f8f9fa;
`;

export const Content = styled.div`
  max-width: 600px;
  margin: 0 auto;
  background-color: white;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const Title = styled.h2`
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
`;

export const CalendarWrapper = styled.div`
  margin-bottom: 20px;

  .react-calendar {
    border-radius: 10px;
    border: 1px solid #ddd;
  }
`;

export const SelectWrapper = styled.div`
  width: 100%;
  margin-bottom: 20px;
`;

export const Label = styled.label`
  display: block;
  margin-bottom: 5px;
  color: #333;
`;

export const Select = styled.select`
  width: 100%;
  padding: 8px;
  border-radius: 5px;
  border: 1px solid #ccc;
`;

export const Button = styled.button`
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 16px;

  &:hover {
    background-color: #0069d9;
  }
`;
