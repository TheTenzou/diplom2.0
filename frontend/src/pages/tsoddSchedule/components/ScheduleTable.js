import React from "react";
import { Alert } from "bootstrap-4-react";

function ScheduleTable({ crew, schedule }) {
  const cellStyle = { width: "200px" };
  let firstRow = [
    <td>
      <Alert success style={cellStyle}>
        {" "}
        {" "}
      </Alert>
    </td>,
  ];

  crew?.map((crew) => {
    firstRow.push(
      <td>
        <Alert success style={cellStyle}>
          {crew.name}
        </Alert>
      </td>
    );
  });

  let table = [<tr>{firstRow}</tr>];

  if (schedule) {
    const days = [...schedule.keys()].sort();

    days.map((day) => {
      const crewsTasks = schedule.get(day);

      let dayTaskList = [
        <td>
          <Alert success style={cellStyle}>
            {day}
          </Alert>
        </td>,
      ];
      crew?.map((crew) => {
        let crewTasks = [];
        if (crewsTasks.has(crew.id)) {
          crewsTasks.get(crew.id).map((task) => {
            crewTasks.push(
              <Alert success style={cellStyle}>
                {task.taskType.name + " "}
                {task.tsodd.typeName}
              </Alert>
            );
          });
        } else {
          crewTasks.push(<Alert success style={cellStyle}>{" "}</Alert>)
        }
        dayTaskList.push(<td>{crewTasks}</td>);
      });
      table.push(<tr>{dayTaskList}</tr>);
    });
  }

  return (
    <>
      <table>{table}</table>
    </>
  );
}

export default ScheduleTable;
