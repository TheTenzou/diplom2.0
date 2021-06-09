import EnhancedTable from "./EnhancedTable";
import TableFlows from "./TableFlows";
import TablePlan from "./TablePlan";

import React, { Component, useEffect } from "react";
import { Nav, Tab, Button, Badge } from 'bootstrap-4-react';
import axios from 'axios';
import ReactDOM from 'react-dom';

export default class MyTable extends Component {
  render() {
    return (
      <React.Fragment>
        <Nav tabs role="tablist">
          <Nav.ItemLink active href="#events" id="events-tab" data-toggle="tab" aria-selected="true">
            Мероприятия
            </Nav.ItemLink>
          <Nav.ItemLink href="#flows" id="flows-tab" data-toggle="tab" aria-selected="false">
            Потоки
          </Nav.ItemLink>
          <Nav.ItemLink href="#plan" id="plan-tab" data-toggle="tab" aria-selected="false">
            План мероприятий
          </Nav.ItemLink>
        </Nav>
        <Tab.Content>
          <Tab.Pane id="events" aria-labelledby="event-tab" show active>
            <div style={{ marginTop: "10px" }}>
              <EnhancedTable />
            </div>
          </Tab.Pane>
          <Tab.Pane id="flows" aria-labelledby="flows-tab">
            <div style={{ marginTop: "10px" }}>
              <TableFlows />
            </div>
          </Tab.Pane>
          <Tab.Pane id="plan" aria-labelledby="plan-tab">
            <div style={{ marginTop: "10px" }}>
              <Button info style={{ marginBottom: '10px' }}>Сгенерировать план мероприятий<Badge info>&#43;</Badge></Button>
              <GetPlan />
              <div id="planData"></div>
              <TablePlan />
            </div>
          </Tab.Pane>
        </Tab.Content>
      </React.Fragment>
    );
  }
}

function GetPlan () {
  const getData = () => {
    var args = {
      method: 'get',
      url: 'api/uds/upgradePlans?page=0&size=20',
    };
    
    axios(args).then((r) => {
      const data = r.data._embedded.upgradePlans;
      ReactDOM.render(
        <React.StrictMode>
          <h5>План мероприятий за {data[0]["dateTime"].split('T')[0]} с ограничением на ресурсы {data[0]["resourceLimit"]}</h5>
        </React.StrictMode>,
        document.getElementById('planData')
      );
    }).catch((er) => {
      console.log(er);
    });
  }

  useEffect(() => {
    getData();
  });

  return <></>
}