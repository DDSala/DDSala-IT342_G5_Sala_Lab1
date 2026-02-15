import React from "react";
import { Link } from "react-router-dom";
import "../styles/Login.css"; 

export default function Dashboard({ logout }) {

  const username = localStorage.getItem("username") || "Explorer";

  return (
    <div className="register-page" style={{ paddingTop: "80px" }}>
      <div className="auth-card" style={{ maxWidth: "600px" }}>
        <h2>Dashboard</h2>
        <p className="subtitle">Welcome back, <span style={{color: "#ffffff", fontWeight: 'bold'}}>{username}</span>!</p>
        
        <div className="dashboard-content" style={{ marginTop: "30px", display: "flex", flexDirection: "column", gap: "10px" }}>
          <p style={{ color: "var(--text-dim)" }}>What on gods green earth would you like to do next?</p>
        </div>
      </div>
    </div>
  );
}