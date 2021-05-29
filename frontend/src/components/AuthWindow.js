import '../authWindow.css';
import axios from "axios";
import React, { Component, useEffect, useState } from "react";
import { BDiv, Form, Button } from 'bootstrap-4-react';
import { Redirect } from 'react-router';

export default class Authentication extends Component {
  render() {
    return (
      <div className="wrapper">
        <div style={{ height: '20%' }}></div>
        <BDiv m="auto" className="mainField" style={{width: "40%"}}>
          <BDiv m="auto" className="contentField">
            <h1 className="title">Авторизация</h1>
            <AuthForm />
          </BDiv>
        </BDiv>
      </div>
    );
  }
}

const AuthForm = () => {
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState(null);
  const [redirect, setRedirect] = useState(false);

  useEffect(() => {
    setLogin(login);
  }, [login]);

  useEffect(() => {
    setPassword(password);
  }, [password]);

  useEffect(() => {
    setErrorMessage(errorMessage);
  }, [errorMessage]);

  useEffect(() => {
    setRedirect(redirect);
  }, [redirect]);

  return (
    <Form>
      <Form.Group>
        <Form.Input type="login" id="loginInput" placeholder="Login" mx="auto" style={{ width: '60%' }} onChange={(e) => setLogin(e.target.value)} />
        <Form.Text text="muted">Будьте уверены, никто больше не узнает ваш логин и пароль.</Form.Text>
      </Form.Group>
      <Form.Group>
        <Form.Input type="password" id="passwordInput" placeholder="Password" mx="auto" style={{ width: '60%' }} onChange={(e) => setPassword(e.target.value)} />
        <Form.Text style={{color: 'red'}}>{errorMessage? errorMessage: ''}</Form.Text>
      </Form.Group>
      <Form.Group>
        <Form.Check>
          <Form.CheckInput type="checkbox" id="exampleCheck1" />
          <Form.CheckLabel htmlFor="exampleCheck1">Запомнить</Form.CheckLabel>
        </Form.Check>
      </Form.Group>
      <Button primary outline as="a" href="/" style={{ marginRight: '20px' }}>Назад</Button>
      <Button primary type="submit" onClick={(e) => {
        e.preventDefault();

        var args = {
          method: 'post',
          url: 'api/users/v1/auth/login',
          data: {
            "login": login,
            "password": password
          }
        };
        
        axios(args).then((r) => {
          localStorage.setItem("accessToken", r.data.accessToken);
          localStorage.setItem("refreshToken", r.data.refreshToken);
          setRedirect(true);
        }).catch((er) => {
          console.log(er.response);
          if (er.response.data.error.message !== undefined) {
            setErrorMessage(er.response.data.error.message);
          } else if (er.response.data.message !== undefined) {
            setErrorMessage(er.response.data.message);
          } else {
            setErrorMessage(er.response.statusText);
          }
        });
      }}>Войти</Button>
      <p>{redirect ? <Redirect to="/" /> : ''}</p>
    </Form>
  );
}