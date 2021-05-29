import "./App.css";

import MainNavigation from "./components/MainNavigation";
import MyMap from "./components/Map";
import Authentication from "./components/AuthWindow";
import AdminPanel from "./components/AdminPanel";
import TsoddSchedulesList from "./pages/TsoddSchedulesList";

import axios from "axios";
import React, { Component, useEffect, useState } from "react";
import { BDiv } from "bootstrap-4-react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

export default class App extends Component {
  render() {
    return (
      <div className="wrapper">
        <Router>
          <Switch>
            <Route exact path="/">
              <MainNavigation />
              <Home />
            </Route>
            <Route path="/about">
              <MainNavigation />
              <About />
            </Route>
            <Route path="/tsoddSchedules">
              <MainNavigation />
              <TsoddSchedulesList />
            </Route>
            <Route path="/table2">
              <MainNavigation />
              <TableFlows />
            </Route>
            <Route path="/auth">
              <Authentication />
            </Route>
            <Route path="/adminPanel">
              <MainNavigation />
              <AdminPanel />
            </Route>
          </Switch>
        </Router>
      </div>
    );
  }
}

function Home() {
  return (
    <BDiv mx="auto" className="home">
      <MyMap />
    </BDiv>
  );
}

function About() {
  return (
    <BDiv mx="auto" className="about">
      <div>
        <h2>Геоинформационная система</h2>
        <p>Предназначена для регулирования ТСОД и маршрутов в вашем городе!</p>
      </div>
      <AxiosWork />
    </BDiv>
  );
}

function TableFlows() {
  return (
    <BDiv mx="auto" className="tableFlows">
      Табличка с Потоками
    </BDiv>
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
