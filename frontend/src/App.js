import './App.css';
import MainNavigation from "./components/MainNavigation";
import MyMap from "./components/Map";
import Info from "./components/Info";
import axios from "axios";
import React, { Component, useEffect, useState } from "react";
import { BDiv } from 'bootstrap-4-react';
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

export default class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route exact path="/">
            <Home />
          </Route>
          <Route path="/about">
            <About />
          </Route>
          <Route path="/table1">
            <TableTSOD />
          </Route>
          <Route path="/table2">
            <TableFlows />
          </Route>
        </Switch>
      </Router>
    );
  }
}

function Home() {
  return (
    <div className="wrapper">
      <MainNavigation />
      <BDiv mx="auto" style={{ width: '80%', height: '80%', padding: '10px', backgroundColor: 'rgba(86, 61, 124, .5)' }}>
        <MyMap />
      </BDiv>
    </div>
  );
}

function About() {
  return (
    <div className="wrapper">
      <MainNavigation />
      <BDiv mx="auto" style={{ width: '80%', height: '80%', padding: '10px', backgroundColor: 'rgba(176, 167, 199, .4)' }}>
        <Info />
      </BDiv>
    </div>
  );
}

function TableTSOD() {
  return (
    <div className="wrapper">
      <MainNavigation />
        <BDiv mx="auto" style={{ width: '80%', height: '80%', padding: '10px', backgroundColor: 'rgba(176, 167, 199, .4)' }}>
          Табличка с ТСОД
        </BDiv>
    </div>
  );
}

function TableFlows() {
  return (
    <div className="wrapper">
      <MainNavigation />
        <BDiv mx="auto" style={{ width: '80%', height: '80%', padding: '10px', backgroundColor: 'rgba(176, 167, 199, .4)' }}>
          Табличка с Потоками
        </BDiv>
    </div>
  );
}

/*function App() {
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
    <p>
          user service is {userService ? userService : "offline"} <br />
          tsodd service is {tsoddService ? tsoddService : "offline"}
    </p>
  );
}*/
