import React, { useState, useEffect } from "react";
import Sidebar from "./Sidebar";
import Navbar from "./NavBar";

const Layout = ({ children }) => {
    const [isSidebarCollapsed, setIsSidebarCollapsed] = useState(true);

    const [windowWidth, setWindowWidth] = useState(window.innerWidth);

    useEffect(() => {
        const handleResize = () => {
            setWindowWidth(window.innerWidth);
            if (window.innerWidth < 768 && !isSidebarCollapsed) {
                setIsSidebarCollapsed(true);
            } else if (window.innerWidth >= 768 && isSidebarCollapsed) {
                setIsSidebarCollapsed(false);
            }
        };
        window.addEventListener("resize", handleResize);
        return () => window.removeEventListener("resize", handleResize);
    }, [isSidebarCollapsed]);

    const toggleSidebar = () => {
        setIsSidebarCollapsed(!isSidebarCollapsed);
    };

    const sidebarWidth = 1;
    const collapsedWidth = -110;

    const isMobile = windowWidth < 768;

    return (
        <div style={{ display: "flex", height: "100vh" }}>
            <Sidebar
                isCollapsed={isSidebarCollapsed}
                onToggleSidebar={toggleSidebar}
                style={{
                    position: isMobile ? "fixed" : "relative",
                    top: 0,
                    left: isSidebarCollapsed && isMobile ? `-${sidebarWidth}px` : "0",
                    width: isSidebarCollapsed ? collapsedWidth : sidebarWidth,
                    height: "100vh",
                    transition: "left 0.3s, width 0.3s",
                    zIndex: 1100,
                }}
            />
            <div
                style={{
                    flexGrow: 1,
                    display: "flex",
                    flexDirection: "column",
                    height: "100vh",
                    marginLeft:
                        !isMobile && (isSidebarCollapsed ? `${collapsedWidth}px` : `${sidebarWidth}px`),
                    // Se for mobile (sidebar fixed), não adiciona marginLeft, para evitar espaço em branco
                    transition: "margin-left 0.3s",
                }}
            >
                {/* Div que contém a navbar */}
                <div style={{ height: "50px", flexShrink: 0 }}>
                    <Navbar
                        isSidebarCollapsed={isSidebarCollapsed}
                        toggleSidebar={toggleSidebar}
                    />
                </div>
                <main
                    style={{
                        flexGrow: 1,
                        display: "flex",
                        flexDirection: "column",
                        height: "100vh",
                        marginLeft: isMobile ?0 : isSidebarCollapsed ? collapsedWidth : sidebarWidth,
                        transition: "margin-left 0.3s",



                    }}
                >
                    {children}
                </main>
            </div>
        </div>
    );
};

export default Layout;
