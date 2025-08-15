import styled from "styled-components";

export const CalendarWrapper = styled.div`
  width: 320px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background: white;
  overflow: visible;

  @media (min-width: 768px) {
    width: 400px;
  }
`;

export const CalendarGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  text-align: center;

  .week-label {
    font-size: 0.75rem;
    font-weight: bold;
    text-transform: uppercase;
  }
`;

export const MonthTitle = styled.div`
  font-size: 0.875rem;
  font-weight: 600;
`;
