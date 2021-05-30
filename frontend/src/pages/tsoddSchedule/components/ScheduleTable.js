import React from "react";
import { Button, Badge, Row, Col } from "bootstrap-4-react";

import "../css/table.css";

function ScheduleTable({ crew, schedule }) {
  let firstRow = [<th></th>];

  crew?.forEach((crew) => {
    firstRow.push(<th>{crew.name}</th>);
  });

  let table = [
    <thead>
      <tr>{firstRow}</tr>
    </thead>,
  ];

  let tableBody = [];

  if (schedule) {
    const days = [...schedule.keys()];

    days.forEach((day) => {
      const crewsTasks = schedule.get(day);

      let dayTaskList = [<th>{day}</th>];
      crew?.forEach((crew) => {
        let crewTasks = [];
        if (crewsTasks.has(crew.id)) {
          crewsTasks.get(crew.id).forEach((task) => {
            crewTasks.push(
              <div
                style={{
                  display: "flex",
                  flexDirection: "row",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                <div>
                  {task.taskType.name}
                  <br />
                  {task.tsodd.typeName}
                  <br />
                  {task.tsodd.positionDescription}
                </div>

                <div
                  style={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <Badge primary className="btn btn-outline-light" style={{ marginBottom: ".2rem" }}>&#9998;</Badge>
                  <Badge danger className="btn btn-outline-light">&#10008;</Badge>
                </div>
              </div>
            );
          });
        }
        dayTaskList.push(<td>{crewTasks}</td>);
      });
      tableBody.push(<tr>{dayTaskList}</tr>);
    });
    table.push(<tbody>{tableBody}</tbody>);
  }

  return (
    <>
      <table className="styled-table">{table}</table>
    </>
  );
}

export default ScheduleTable;
