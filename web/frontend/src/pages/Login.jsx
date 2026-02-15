import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import API, { setAuthToken } from "../utils/api";
import "../styles/Login.css"; 

export default function Login({ onLogin }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const res = await API.post("/auth/login", { email, password });
      
      const { token, username } = res.data;
      
      localStorage.setItem("token", token);
      localStorage.setItem("username", username); 
      
      setAuthToken(token);
      
      onLogin(token);
    
      navigate("/dashboard", { replace: true });

    } catch (err) {
      const errorMsg = typeof err.response?.data === 'string' 
        ? err.response.data 
        : err.response?.data?.error || "Login failed. Please check your credentials.";
        
      setError(errorMsg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-page"> 
      <div className="auth-card"> 
        <h2>Welcome Back</h2>
        <p className="subtitle">Please Enter your Registered Credentials to access your Account.</p>

        {error && <div className="error-banner">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <label>Email Address</label>
            <input
              type="email"
              placeholder="name@example.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="input-group">
            <label>Password</label>
            <input
              type="password"
              placeholder="••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button type="submit" disabled={loading}>
            {loading ? "Verifying..." : "Login"}
          </button>
        </form>

        <div className="login-prompt">
          Don't have an account?{" "}
          <Link to="/register" className="login-link">
            Sign up
          </Link>
        </div>
      </div>
    </div>
  );
}