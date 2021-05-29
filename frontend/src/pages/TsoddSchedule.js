import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { BDiv, Alert, Row, Col, Container } from "bootstrap-4-react";

function TsoddSchedule() {
  let { id } = useParams();

  const [schedule, setSchedule] = useState(null);

  useEffect(() => {
    axios.get(`/api/tsodd/v1/schedule/${id}`).then((response) => {
      setSchedule(response.data);
    });
  }, [setSchedule]);

  const row = 7;
  const column = 15;
  let list = [];
  for (let i = 0; i < row; i++) {
    let inerlist = [];
    for (let j = 0; j < column; j++) {
      inerlist.push(
        <td >
          <Alert success style={{ width: "80px" }}> {i + " " + j} </Alert>
        </td>
      );
    }
    list.push(<tr md={2}>{inerlist}</tr>);
  }

  const gridStyle = {
    display: "grid",
    gridTemplateColumns: `repeat(${row}, 80px)`,
    gridTemplateRows: `repeat(${column}, 60px)`,
    gridGap: "5px",
  };

  return (
    <BDiv mx="auto" className="tableFlows">
      Schedule
      <br />
      <table>{list}</table>
    </BDiv>
  );
}

export default TsoddSchedule;
