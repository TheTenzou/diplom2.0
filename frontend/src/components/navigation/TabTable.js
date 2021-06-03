import EnhancedTable from "./EnhancedTable";
import TableFlows from "./TableFlows";

import React, { Component } from "react";
import { Nav, Tab } from 'bootstrap-4-react';

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
        </Tab.Content>
      </React.Fragment>
    );
  }
}