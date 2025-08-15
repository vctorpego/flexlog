import React from "react";
import { CellWrapper } from "./styles";

const Cell = ({ onClick, children, className = "", isActive = false }) => {
  return (
    <CellWrapper
      onClick={!isActive && onClick ? onClick : undefined}
      isActive={isActive}
      clickable={!isActive && onClick}
      className={className}
    >
      {children}
    </CellWrapper>
  );
};

export default Cell;
