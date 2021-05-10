import '../AdminPanel.css';
import React, { Component, useState, useEffect } from "react";
import { BDiv, Card, Button, Modal, Form } from 'bootstrap-4-react';
import axios from "axios";

export default class AdminPanel extends Component {
  render() {
    return (
      <div className="adminWrapper">
        <div style={{ height: '15%' }}></div>
        <BDiv m="auto" className="mainField">
          <BDiv m="auto" className="contentField">
            <h1 className="title">Список пользователей</h1>
            <UsersData />
            <UserCards />
          </BDiv>
        </BDiv>
      </div>
    );
  }
}

const UserCards = () => {
  let cards = [];
  var countOfUsers = 0;
  try {
    countOfUsers = JSON.parse(localStorage.getItem("usersData"))["total"];
    var data = JSON.parse(localStorage.getItem("usersData"))["data"];
  } catch { }

  for (let i = 0; i < countOfUsers; ++i) {
    cards.push(
      <Card key={i} style={{ borderRadius: '20px' }}>
        <Card.Body>
          <Card.Title style={{ fontWeight: "bold", fontSize: "24px" }}>{data[i]["login"]}</Card.Title>
          <Card.Text>{data[i]["status"]}</Card.Text>
          <Card.Text>{data[i]["role"][0]}</Card.Text>
          <Card.Text>{data[i]["name"]}</Card.Text>
        </Card.Body>
        <Card.Footer>
          <UpdateModal data={data[i]} />
          <Button primary mx="2">Удалить</Button>
        </Card.Footer>
      </Card>
    );
  }

  return <Card.Columns mb="3">{cards}</Card.Columns>;
}

const UsersData = () => {
  var args = {
    method: 'get',
    url: 'api/users/v1/users?page=1&limit=10&&status=ACTIVE&role=ROLE_USER',
    headers: {
      Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    }
  };

  axios(args).then((r) => {
    localStorage.setItem("usersData", JSON.stringify(r.data));
  }).catch((er) => {
    console.log(er.response.request);
  });

  return <p></p>
}

const UpdateModal = (data) => {
  const id = data["data"]["id"];

  const [login, setLogin] = useState('');
  const [name, setName] = useState('');
  const [userRole, setUserRole] = useState(false);
  const [adminRole, setAdminRole] = useState(false);
  const [errorMessage, setErrorMessage] = useState(null);
  const [goodMessage, setGoodMessage] = useState(null);

  useEffect(() => {
    setLogin(login);
  }, [login]);

  useEffect(() => {
    setName(name);
  }, [name]);

  useEffect(() => {
    setErrorMessage(errorMessage);
  }, [errorMessage]);

  useEffect(() => {
    setGoodMessage(goodMessage);
  }, [goodMessage]);

  return (
    <>
      <Button primary data-toggle="modal" data-target={"#update" + id} mx="2">Обновить</Button>
      <Modal id={"update" + id} fade>
        <Modal.Dialog>
          <Modal.Content>
            <Modal.Header>
              <Modal.Title>Новые данные пользователя:</Modal.Title>
              <Modal.Close>
                <span aria-hidden="true">&times;</span>
              </Modal.Close>
            </Modal.Header>
            <Modal.Body>
              <Form>
                <Form.Input type="login" id={"loginInput" + id} placeholder="Login" my="2" onChange={(e) => setLogin(e.target.value)} />
                <Form.Input id={"nameInput" + id} placeholder="Name" my="2" onChange={(e) => setName(e.target.value)} />
                <Form.Text style={{ color: 'red' }}>{errorMessage ? errorMessage : ''}</Form.Text>
                <Form.Group>
                  <Form.Check>
                    <Form.Checkbox id={"roleUser" + id} value="ROLE_USER" onChange={(e) => setUserRole(e.target.value)} />
                    <Form.CheckLabel htmlFor={"roleUser" + id}>ROLE_USER</Form.CheckLabel>
                  </Form.Check>
                  <Form.Check>
                    <Form.Checkbox id={"roleAdmin" + id} value="ROLE_ADMIN" onChange={(e) => setAdminRole(e.target.value)} />
                    <Form.CheckLabel htmlFor={"roleAdmin" + id}>ROLE_ADMIN</Form.CheckLabel>
                  </Form.Check>
                </Form.Group>
                <Form.Text style={{ color: 'green' }}>{goodMessage ? goodMessage : ''}</Form.Text>
              </Form>
            </Modal.Body>
            <Modal.Footer>
              <Button secondary data-dismiss="modal">Отмена</Button>
              <Button primary onClick={(e) => {
                e.preventDefault();

                var role = []
                if (adminRole) role.push(adminRole);
                if (userRole) role.push(userRole);
                
                var args = {
                  method: 'patch',
                  url: 'api/users/v1/user',
                  headers: {
                    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
                  },
                  data: {
                    "id": id,
                    "login": login,
                    "name": name,
                    "role": role
                  }
                };

                axios(args).then((r) => {
                  if (r.status === 200) {
                    setGoodMessage("Данные пользователя обновлены!");
                  }
                }).catch((er) => {
                  setErrorMessage(er.response.data.error.message);
                  console.log(er.response);
                });
              }}>Обновить</Button>
            </Modal.Footer>
          </Modal.Content>
        </Modal.Dialog>
      </Modal>
    </>
  );
}