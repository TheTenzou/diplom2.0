import '../AdminPanel.css';
import React, { Component, useState, useEffect } from "react";
import { BDiv, Card, Button, Modal, Form, Collapse, Row, Col } from 'bootstrap-4-react';
import axios from "axios";
import ReactDOM from 'react-dom';

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
            <UserDataSample />
            <p style={{ margin: '5px', borderBottom: '1px solid gray' }}></p>
            <div id="usersCards"></div>
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
          <DeleteModal data={data[i]} />
        </Card.Footer>
      </Card>
    );
  }

  return <Card.Columns my="4">{cards}</Card.Columns>;
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
              <Modal.Title>Новые данные для {data["data"]["login"]} :</Modal.Title>
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
              <Modal.Title>Точно удалить пользователя {data["data"]["login"]} ?</Modal.Title>
              <Modal.Close>
                <span aria-hidden="true">&times;</span>
              </Modal.Close>
            </Modal.Header>
            <Modal.Body>
              {errorMessage ? errorMessage : ''}
              {goodMessage ? goodMessage : ''}
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

const UserDataSample = () => {
  const [role1, setRole1] = useState(false);
  const [role2, setRole2] = useState(false);
  const [status1, setStatus1] = useState(false);
  const [status2, setStatus2] = useState(false);

  const getUserData = (myUrl) => {
    var args = {
      method: 'get',
      url: myUrl,
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      }
    };
  
    axios(args).then((r) => {
      localStorage.setItem("usersData", JSON.stringify(r.data));
      ReactDOM.render(
        <React.StrictMode>
          <UserCards />
        </React.StrictMode>,
        document.getElementById('usersCards')
      );
    }).catch((er) => {
      console.log(er.response.request);
    });
  }

  useEffect(() => {
    var testUrl = '';
    if (!role1 && !role2 && !status2 && !status1) {
      testUrl = 'api/users/v1/users?page=1&limit=10&&status=ACTIVE&role=ROLE_USER';
    }
    else {
      testUrl = 'api/users/v1/users?page=1&limit=10&';
      if (status1) testUrl += '&status=ACTIVE';
      if (status2) testUrl += '&status=DELETED';
      if (role1) testUrl += '&role=ROLE_USER';
      if (role2) testUrl += '&role=ROLE_ADMIN';
    }
    
    getUserData(testUrl);
  }, [role1, role2, status1, status2]);

  return (
    <>
      <Collapse.Button warning m="2" target=".multi-collapse" aria-expanded="false" aria-controls="collapseExample">
        Выборка
      </Collapse.Button>

      <Row mb="4">
        <Col>
          <Collapse id="Collapse1" className="multi-collapse">
            <Card>
              <Card.Header>
                Нужна выборка по умолчанию?
              </Card.Header>
              <Card.Body>
                Тогда просто снимите все галочки ;)
              </Card.Body>
            </Card>
          </Collapse>
        </Col>

        <Col>
          <Collapse id="Collapse2" className="multi-collapse">
            <Card>
              <Card.Header>
                Роли/Права
              </Card.Header>
              <Card.Body>
                <input type="checkbox" id="role1" onChange={(e) => setRole1(!role1)} /> ROLE_USER
                <br></br>
                <input type="checkbox" id="role2" onChange={(e) => setRole2(!role2)} /> ROLE_ADMIN
              </Card.Body>
            </Card>
          </Collapse>
        </Col>

        <Col>
          <Collapse id="Collapse3" className="multi-collapse">
            <Card>
              <Card.Header>
                Статусы
              </Card.Header>
              <Card.Body>
                <input type="checkbox" id="status1" onChange={(e) => setStatus1(!status1)} /> ACTIVE
                <br></br>
                <input type="checkbox" id="status2" onChange={(e) => setStatus2(!status2)} /> DELETED
              </Card.Body>
            </Card>
          </Collapse>
        </Col>
      </Row>
    </>
  );
}