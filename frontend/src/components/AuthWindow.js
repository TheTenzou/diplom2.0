import '../authWindow.css';
import axios from "axios";
import React, { Component, useEffect, useState } from "react";
import { BDiv, Form, Button, ButtonGroup } from 'bootstrap-4-react';

export default class Authentication extends Component {
    render() {
        return (
            <div className="wrapper">
              <div style={{ height: '20%' }}></div>
              <BDiv m="auto" className="mainField">
                <BDiv m="auto" className="contentField">
                  <h1 className="title">Авторизация</h1>
                  <Form>
                    <Form.Group>
                      <Form.Input type="email" id="email" placeholder="E-mail" mx="auto" style={{ width: '60%' }} />
                      <Form.Text text="muted">Будьте уверены, никто больше не узнает вашу почту.</Form.Text>
                    </Form.Group>
                    <Form.Group>
                      <Form.Input type="password" id="password" placeholder="Password" mx="auto" style={{ width: '60%' }} />
                    </Form.Group>
                    <Form.Group>
                      <Form.Check>
                        <Form.CheckInput type="checkbox" id="exampleCheck1" />
                        <Form.CheckLabel htmlFor="exampleCheck1">Запомнить</Form.CheckLabel>
                      </Form.Check>
                    </Form.Group>
                    <Button primary outline as="a" href="/" style={{marginRight: '20px'}}>Назад</Button>
                    <Button primary type="submit">Войти</Button>
                  </Form>
                </BDiv>
              </BDiv>
            </div>
        );
    }
}