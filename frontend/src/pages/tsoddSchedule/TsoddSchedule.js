import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { BDiv } from "bootstrap-4-react";

import ScheduleTable from "./components/ScheduleTable"

function TsoddSchedule() {
  let { id } = useParams();

  const [schedule, setSchedule] = useState(null);

  useEffect(() => {
    axios.get(`/api/tsodd/v1/schedule/${id}?size=240`).then((response) => {
      let scheduleResponse = response.data

      let schedule = new Map();
      scheduleResponse.tasks.data.forEach((task) => {

        if (!schedule.has(task.date)) {
          schedule.set(task.date, new Map())
        }
        if (!schedule.get(task.date).has(task.crew.id)) {
          schedule.get(task.date).set(task.crew.id, [])
        }
        schedule.get(task.date).get(task.crew.id).push({
          taskType: task.taskType,
          tsodd: task.tsodd
        })

      })
      setSchedule(schedule);
    });
  }, [setSchedule, id]);

  const [crewList, setCrewList] = useState(null);

  useEffect(() => {
    axios.get(`/api/tsodd/v1/crew`).then((response) => {
      setCrewList(response.data);
    });
  }, [setCrewList]);

  return (
    <BDiv mx="auto" className="tsoddSchedule">
      Schedule
      <br />
      <ScheduleTable crew={crewList} schedule={schedule}/>
    </BDiv>
  );
}

export default TsoddSchedule;
