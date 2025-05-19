import React from "react";
import { NavBarContainer, ToggleButton } from "./styles";



const Navbar = ({ isSidebarCollapsed, toggleSidebar }) => {
  return (
    <NavBarContainer>
      <ToggleButton onClick={toggleSidebar} aria-label="Toggle Sidebar">
        {isSidebarCollapsed ? "☰" : "✕"}
        
      </ToggleButton>
      

    </NavBarContainer>
  );
};

export default Navbar;
