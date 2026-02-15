import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import API from "../utils/api";
import "../styles/Login.css";

export default function Profile({ logout }) {
  const [user, setUser] = useState(null);
  const [error, setError] = useState(false);

  useEffect(() => {
    API.get("/user/me")
      .then((res) => setUser(res.data))
      .catch((err) => {
        console.error("Profile fetch error:", err);
        setError(true);
      });
  }, []);

  return (
    <div className="register-page" style={{ paddingTop: "80px" }}>
      <div className="auth-card">
        <h2>User Profile</h2>
        <p className="subtitle">Identification and credentials.</p>

        {user ? (
          <div className="profile-details" style={{ textAlign: "left", margin: "20px 0" }}>
            <div className="input-group">
              <label>Username</label>
              <div className="profile-value">{user.username}</div>
            </div>
            <div className="input-group">
              <label>Email Address</label>
              <div className="profile-value">{user.email}</div>
            </div>
          </div>
        ) : error ? (
          <div className="error-banner">User profile not found in database.</div>
        ) : (
          <p className="subtitle">Retrieving data...</p>
        )}
        </div>
      </div>
  );
}