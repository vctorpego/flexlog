import styled, { css } from "styled-components";

export const CellWrapper = styled.div`
  height: 40px;
  width: 40px;
  border-bottom: 1px solid #ddd;
  border-right: 1px solid #ddd;
  display: flex;
  align-items: center;
  justify-content: center;
  user-select: none;
  transition: background-color 0.2s;

  ${({ clickable }) =>
    clickable &&
    css`
      cursor: pointer;
      &:hover {
        background-color: #f3f3f3;
      }
      &:active {
        background-color: #e5e5e5;
      }
    `}

  ${({ isActive }) =>
    isActive &&
    css`
      font-weight: bold;
      color: white;
      background-color: #2563eb; /* Azul */
    `}
`;
