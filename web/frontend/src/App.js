import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Profile from "./pages/Profile";
import Navbar from "./components/Navbar";
import { setAuthToken } from "./utils/api";


const ProtectedRoute = ({ token, children }) => {
  if (!token) return <Navigate to="/login" replace />;
  return children;
};


const PublicRoute = ({ token, children }) => {
  if (token) return <Navigate to="/dashboard" replace />;
  return children;
};

function AppContent() {
  const [token, setToken] = useState(() => localStorage.getItem("token") || "");
  const location = useLocation();

  useEffect(() => {
    if (token) {
      localStorage.setItem("token", token);
      setAuthToken(token);
    } else {
      localStorage.removeItem("token");
      localStorage.removeItem("username");
      setAuthToken(null);
    }
  }, [token]);

  const handleLogin = (newToken) => setToken(newToken);
  
  const handleLogout = () => {
    setToken("");
    localStorage.clear(); 
  };

  
  const isAuthPage = location.pathname === "/login" || location.pathname === "/register";

  return (
    <>
 
      {token && !isAuthPage && <Navbar logout={handleLogout} />}

      <Routes>
 
        <Route path="/" element={<Navigate to={token ? "/dashboard" : "/login"} replace />} />


        <Route path="/login" element={
          <PublicRoute token={token}>
            <Login onLogin={handleLogin} />
          </PublicRoute>
        } />
        
        <Route path="/register" element={
          <PublicRoute token={token}>
            <Register />
          </PublicRoute>
        } />

   
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute token={token}>
              <Dashboard logout={handleLogout} />
            </ProtectedRoute>
          }
        />
        <Route
          path="/profile"
          element={
            <ProtectedRoute token={token}>
              <Profile logout={handleLogout} />
            </ProtectedRoute>
          }
        />

        <Route path="*" element={<Navigate to={token ? "/dashboard" : "/login"} replace />} />
      </Routes>
    </>
  );
}

export default function App() {
  return (
    <Router>
      <AppContent />
    </Router>
  );
}