import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import "../styles/Login.css";

export default function Navbar({ logout }) {
  const [showConfirm, setShowConfirm] = useState(false);
  const location = useLocation();
  const username = localStorage.getItem("username") || "User";

  return (
    <>
      <nav className="glass-nav">
        <div className="nav-container">
          <div className="nav-logo">USERAUTH<span>LabAct</span></div>
          
          <div className="nav-links">
            <Link to="/dashboard" className={location.pathname === "/dashboard" ? "active" : ""}>Dashboard</Link>
            <Link to="/profile" className={location.pathname === "/profile" ? "active" : ""}>Profile</Link>

            <button onClick={() => setShowConfirm(true)} className="nav-logout">Logout</button>
          </div>
        </div>
      </nav>


      {showConfirm && (
        <div className="modal-overlay">
          <div className="success-modal" style={{ borderColor: "#ef4444", boxShadow: "0 0 30px rgba(239, 68, 68, 0.2)" }}>
            <h2 style={{ color: "#ef4444" }}>Hol'on Bruh!</h2>
            <p>C'mon bruh is you really gon dip again?</p>
            
            <div style={{ display: "flex", gap: "10px", marginTop: "25px" }}>
              <button 
                onClick={logout} 
                style={{ background: "#ef4444", marginTop: 0 }}
              >
                Yeh bruh I'm Out
              </button>
              <button 
                onClick={() => setShowConfirm(false)} 
                style={{ background: "transparent", border: "1px solid var(--glass-border)", marginTop: 0 }}
                className="secondary-btn"
              >
                Nah We Finna Stay
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}