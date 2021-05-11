import '../AdminPanel.css';
import React, { Component, useState, useEffect } from "react";
import { BDiv, Card, Button, Modal, Form } from 'bootstrap-4-react';
import axios from "axios";
import { Redirect } from 'react-router';

export default class AdminPanel extends Component {
  render() {
    return (
      <div className="adminWrapper">
        <div style={{ height: '15%' }}></div>
        <BDiv m="auto" className="mainField">
          <BDiv m="auto" className="contentField">
            <h1 style={{ paddingBottom: '10px', borderBottom: '1px solid gray' }}>Список пользователей</h1>
            <CreateModal />
            <Button dark m="2" onClick={() => window.location.reload()}>Обновить список</Button>
            <p style={{ margin: '5px', borderBottom: '1px solid gray' }}></p>
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
          <DeleteModal data={data[i]}/>
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

const DeleteModal = (data) => {
  const id = data["data"]["id"];

  const [errorMessage, setErrorMessage] = useState(null);
  const [goodMessage, setGoodMessage] = useState(null);

  return (
    <>
      <Button primary data-toggle="modal" data-target={"#delete" + id}>Удалить</Button>
      <Modal id={"delete" + id} fade>
        <Modal.Dialog centered>
          <Modal.Content>
            <Modal.Header>
              <Modal.Title>Точно удалить?</Modal.Title>
              <Modal.Close>
                <span aria-hidden="true">&times;</span>
              </Modal.Close>
            </Modal.Header>
            <Modal.Body>
              {errorMessage? errorMessage : ''}
              {goodMessage? goodMessage : ''}
            </Modal.Body>
            <Modal.Footer>
              <Button secondary data-dismiss="modal">Закрыть</Button>
              <Button primary onClick={(e) => {
                e.preventDefault();

                var myUrl = 'api/users/v1/user/' + id;
                console.log(myUrl);
                
                var args = {
                  method: 'delete',
                  url: myUrl,
                  headers: {
                    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
                  }
                };

                axios(args).then((r) => {
                  if (r.statusText === "OK") {
                    setGoodMessage("Пользователь успешно удалён!");
                  }
                }).catch((er) => {
                  setErrorMessage(er.response.data.error.message);
                  console.log(er.response);
                });
              }}>Удалить</Button>
            </Modal.Footer>
          </Modal.Content>
        </Modal.Dialog>
      </Modal>
    </>
  )
}

const CreateModal = () => {

  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [userRole, setUserRole] = useState(false);
  const [adminRole, setAdminRole] = useState(false);
  const [errorMessage, setErrorMessage] = useState(null);
  const [goodMessage, setGoodMessage] = useState(null);

  return (
    <>
      <Button success m="2" data-toggle="modal" data-target="#createUser">Создать пользователя</Button>
      <Modal id="createUser" fade>
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
                <Form.Input type="login" id="loginInput" placeholder="Login" my="2" onChange={(e) => setLogin(e.target.value)} />
                <Form.Input type="password" id="passwordInput" placeholder="Password" my="2" onChange={(e) => setPassword(e.target.value)} />
                <Form.Input id="nameInput" placeholder="Name" my="2" onChange={(e) => setName(e.target.value)} />
                <Form.Text style={{ color: 'red' }}>{errorMessage ? errorMessage : ''}</Form.Text>
                <Form.Group>
                  <Form.Check>
                    <Form.Checkbox id="roleUser" value="ROLE_USER" onChange={(e) => setUserRole(e.target.value)} />
                    <Form.CheckLabel htmlFor="roleUser">ROLE_USER</Form.CheckLabel>
                  </Form.Check>
                  <Form.Check>
                    <Form.Checkbox id="roleAdmin" value="ROLE_ADMIN" onChange={(e) => setAdminRole(e.target.value)} />
                    <Form.CheckLabel htmlFor="roleAdmin">ROLE_ADMIN</Form.CheckLabel>
                  </Form.Check>
                </Form.Group>
                <Form.Text style={{ color: 'green' }}>{goodMessage ? goodMessage : ''}</Form.Text>
              </Form>
            </Modal.Body>
            <Modal.Footer>
              <Button secondary data-dismiss="modal">Закрыть</Button>
              <Button primary onClick={(e) => {
                e.preventDefault();

                var role = []
                if (adminRole) role.push(adminRole);
                if (userRole) role.push(userRole);

                console.log(login, password, name, role);
                
                var args = {
                  method: 'put',
                  url: 'api/users/v1/user',
                  headers: {
                    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
                  },
                  data: {
                    "login": login,
                    "password": password,
                    "name": name,
                    "role": role
                  }
                };

                axios(args).then((r) => {
                  if (r.status === 201) {
                    setGoodMessage("Пользователь успешно создан!");
                  }
                  console.log(r);
                }).catch((er) => {
                  setErrorMessage(er.response.data.message);
                });
              }}>Создать</Button>
            </Modal.Footer>
          </Modal.Content>
        </Modal.Dialog>
      </Modal>
    </>
  );
}