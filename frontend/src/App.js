import logo from "./logo.svg";
import "./App.css";
import axios from "axios";
import React, { useEffect, useState } from "react";

function App() {
  const [userService, setUserService] = useState(null);

  const [tsoddService, setTsoddService] = useState(null);

  useEffect(() => {
    axios.get("/api/users/health").then((response) => {
      setUserService(response.data.status);
    });
  }, [setUserService]);

  useEffect(() => {
    axios.get("/api/tsodd/health").then((response) => {
      setTsoddService(response.data.status);
    });
  }, [setTsoddService]);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          user service is {userService ? userService : "offline"} <br />
          tsodd service is {tsoddService ? tsoddService : "offline"}
        </p>
      </header>
    </div>
  );
}

export default App;
