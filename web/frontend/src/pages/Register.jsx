import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import API from "../utils/api";
import "../styles/Login.css";

export default function Register() {
  const [formData, setFormData] = useState({ username: "", email: "", password: "" });
  const [error, setError] = useState("");
  const [showSuccess, setShowSuccess] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    if (error) setError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await API.post("/auth/register", formData);
      
      setShowSuccess(true);
      
      setTimeout(() => {
        navigate("/login");
      }, 2500);
    } catch (err) {
      setError(err.response?.data || "Registration failed");
    }
  };

  return (
    <div className="register-page">
      {showSuccess && (
        <div className="modal-overlay">
          <div className="success-modal">
            <h2>Registration Successful! 🎉</h2>
            <p>Redirecting to login...</p>
          </div>
        </div>
      )}

      <div className="auth-card">
        <h2>Create Account</h2>
        <p className="subtitle">Join us and start your journey.</p>

        {error && <div className="error-banner">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <label>Username</label>
            <input
              name="username"
              type="text"
              placeholder="johndoe"
              onChange={handleChange}
              required
            />
          </div>

          <div className="input-group">
            <label>Email Address</label>
            <input
              name="email"
              type="email"
              placeholder="name@example.com"
              onChange={handleChange}
              required
            />
          </div>

          <div className="input-group">
            <label>Password</label>
            <input
              name="password"
              type="password"
              placeholder="••••••••"
              onChange={handleChange}
              required
            />
          </div>

          <button type="submit">Sign Up</button>
        </form>

        <div className="login-prompt">
          Already have an account?{" "}
          <Link to="/login" className="login-link">
            Log in
          </Link>
        </div>
      </div>
    </div>
  );
}