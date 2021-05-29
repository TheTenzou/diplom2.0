import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { BDiv } from "bootstrap-4-react";

function TsoddSchedule() {
  let { id } = useParams();

  const [schedule, setSchedule] = useState(null);

  useEffect(() => {
    axios.get(`/api/tsodd/v1/schedule/${id}`).then((response) => {
      setSchedule(response.data);
    });
  }, [setSchedule]);

  return (
    <BDiv mx="auto" className="tableFlows">
      Schedule
      <br />
      <pre>{JSON.stringify(schedule, null, 2)}</pre>
    </BDiv>
  );
}

export default TsoddSchedule;
