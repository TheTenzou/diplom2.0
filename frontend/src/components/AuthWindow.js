import '../authWindow.css';
import axios from "axios";
import React, { Component, useEffect, useState } from "react";
import { BDiv, Form, Button } from 'bootstrap-4-react';

export default class Authentication extends Component {
  render() {
    return (
      <div className="wrapper">
        <div style={{ height: '20%' }}></div>
        <BDiv m="auto" className="mainField">
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

  useEffect(() => {
    setLogin(login);
  }, [setLogin]);

  useEffect(() => {
    setPassword(password);
  }, [setPassword]);

  useEffect(() => {
    setErrorMessage(errorMessage);
  }, [setErrorMessage]);

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
        const args = {
          method: 'post',
          url: 'api/users/v1/auth/login',
          data: {
            "login": login,
            "password": password
          }
        };
        
        axios(args).then((r) => {
          console.log(r.data.accessToken);
          console.log(r.data.refreshToken);
        }).catch((er) => {
          setErrorMessage(JSON.parse(er.response.request.response)["message"]);
        });
      }}>Войти</Button>
    </Form>
  );
}