import React from "react";

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
              <div>
                {task.taskType.name + " "}
                <br />
                {task.tsodd.typeName}
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
