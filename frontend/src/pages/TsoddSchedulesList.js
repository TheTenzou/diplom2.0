import "../App.css";
import axios from "axios";

import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { BDiv, Card, Button, ButtonGroup } from "bootstrap-4-react";

function TsoddSchedulesList() {
  let { page } = useParams();

  const [scheduleList, setScheduleList] = useState(null);

  useEffect(() => {
    axios.get(`/api/tsodd/v1/schedule?page=${page}`).then((response) => {
      setScheduleList(response.data);
    });
  }, [setScheduleList, page]);

  const itemList = scheduleList?.data.map((schedule) => {
    const scheduleStatus =
      schedule.status === "GENERATING"
        ? "состояние: расписание генерируется"
        : "";

    return (
      <Card as="a" href="test">
        <Card.Body>
          <Card.Title>{schedule.name}</Card.Title>
          <Card.Text>
            дата создания: {schedule.createdDate} {scheduleStatus}
          </Card.Text>
          <Button info as="a" href={"/tsoddSchedule/" + schedule.id}>Подробней</Button>
        </Card.Body>
      </Card>
    );
  });

  return (
    <BDiv mx="auto" className="tsoddSchedule">
      <h2>План обслуживания ТСОДД</h2>
      <br />
      <Button success>Сгенерировать расписание</Button> <p />
      {itemList}
      <p />
      <ButtonGroup>
        <Button
          secondary
          mr="sm-2"
          as="a"
          href={
            "/tsoddSchedules/" + (scheduleList ? scheduleList.previousPage : 0)
          }
        >
          Предыдущая страница
        </Button>
        <Button
          secondary
          mr="sm-2"
          as="a"
          href={"/tsoddSchedules/" + (scheduleList ? scheduleList.nextPage : 0)}
        >
          Следующая страница
        </Button>
      </ButtonGroup>
      <p />
    </BDiv>
  );
}

export default TsoddSchedulesList;
