import './App.css';
import MainNavigation from "./components/MainNavigation";
import MyMap from "./components/Map";
import Authentication from "./components/AuthWindow";
import AdminPanel from "./components/AdminPanel";
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
          <Route path="/auth">
            <Authentication />
          </Route>
          <Route path="/adminPanel">
            <AdminPanel />
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
      <BDiv mx="auto" className="home">
        <MyMap />
      </BDiv>
    </div>
  );
}

function About() {
  return (
    <div className="wrapper">
      <MainNavigation />
      <BDiv mx="auto" className="about">
        <div>
          <h2>Геоинформационная система</h2>
          <p>Предназначена для регулирования ТСОД и маршрутов в вашем городе!</p>
        </div>
        <AxiosWork />
      </BDiv>
    </div>
  );
}

function TableTSOD() {
  return (
    <div className="wrapper">
      <MainNavigation />
        <BDiv mx="auto" className="tableTSOD">
          Табличка с ТСОД
        </BDiv>
    </div>
  );
}

function TableFlows() {
  return (
    <div className="wrapper">
      <MainNavigation />
        <BDiv mx="auto" className="tableFlows">
          Табличка с Потоками
        </BDiv>
    </div>
  );
}

function AxiosWork() {
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
}

