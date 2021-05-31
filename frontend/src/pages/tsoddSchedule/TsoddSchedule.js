import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { BDiv, Button, Badge } from "bootstrap-4-react";

import ScheduleTable from "./components/ScheduleTable";

function TsoddSchedule() {
  let { id } = useParams();

  const [schedule, setSchedule] = useState(null);
  const [scheduleInfo, setScheduleInfo] = useState(null);

  useEffect(() => {
    axios.get(`/api/tsodd/v1/schedule/${id}?size=240`).then((response) => {
      let scheduleResponse = response.data;

      setScheduleInfo({
        name: scheduleResponse.name,
        createdDate: scheduleResponse.createdDate,
        startDate: scheduleResponse.startDate,
        endDate: scheduleResponse.endDate,
      });

      let schedule = new Map();
      scheduleResponse.tasks.data.forEach((task) => {
        if (!schedule.has(task.date)) {
          schedule.set(task.date, new Map());
        }
        if (!schedule.get(task.date).has(task.crew.id)) {
          schedule.get(task.date).set(task.crew.id, []);
        }
        schedule.get(task.date).get(task.crew.id).push({
          taskType: task.taskType,
          tsodd: task.tsodd,
        });
      });
      setSchedule(schedule);
    });
  }, [setSchedule, id, setScheduleInfo]);

  const [crewList, setCrewList] = useState(null);

  useEffect(() => {
    axios.get(`/api/tsodd/v1/crew`).then((response) => {
      setCrewList(response.data);
    });
  }, [setCrewList]);

  return (
    <BDiv mx="auto" className="tsoddSchedule">
      <h3>План обслуживания ТСОДД</h3>
      Название: {scheduleInfo?.name} <br />
      Дата создания: {scheduleInfo?.createdDate} <br />
      Дата начала: {scheduleInfo?.startDate} <br />
      Дата окончания: {scheduleInfo?.endDate} <br />
      <p />
      <div>
        <Button primary style={{ marginRight: ".2rem" }}>
          Обновить <Badge primary>&#9998;</Badge>
        </Button>
        <Button danger>
          Удалить <Badge danger>&#10008;</Badge>
        </Button>
      </div>
      <p />
      <Button success>
        Добавить задачу <Badge success>+</Badge>
      </Button>
      <ScheduleTable crew={crewList} schedule={schedule} />
    </BDiv>
  );
}

export default TsoddSchedule;
