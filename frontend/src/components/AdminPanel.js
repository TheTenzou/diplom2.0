import '../AdminPanel.css';
import React, { Component } from "react";
import { BDiv, Card, Button, Modal } from 'bootstrap-4-react';
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
        var countOfUsers = JSON.parse(localStorage.getItem("usersData"))["total"];
        var data = JSON.parse(localStorage.getItem("usersData"))["data"];
    } catch {}
    
    for (let i = 0; i < countOfUsers; ++i) {
        cards.push(
            <Card key={i} style={{borderRadius: '20px'}}>
                <Card.Body>
                    <Card.Title style={{fontWeight: "bold", fontSize: "24px"}}>{data[i]["login"]}</Card.Title>
                    <Card.Text>{data[i]["status"]}</Card.Text>
                    <Card.Text>{data[i]["role"][0]}</Card.Text>
                </Card.Body>
                <Card.Footer>
                    <div>
                        <Button primary data-toggle="modal" data-target="#exampleModal" mx="2">Обновить</Button>
                        <Button primary mx="2">Удалить</Button>
                        <Modal id="exampleModal" fade>
                            <Modal.Dialog>
                                <Modal.Content>
                                    <Modal.Header>
                                        <Modal.Title>Modal title</Modal.Title>
                                        <Modal.Close>
                                        <span aria-hidden="true">&times;</span>
                                        </Modal.Close>
                                    </Modal.Header>
                                    <Modal.Body>
                                        Modal body text goes here.
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button secondary data-dismiss="modal">Close</Button>
                                        <Button primary>Save changes</Button>
                                    </Modal.Footer>
                                </Modal.Content>
                            </Modal.Dialog>
                        </Modal>
                    </div>
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
