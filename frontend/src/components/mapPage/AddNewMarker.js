import OpenModalForm from './ModalForm';

import React, { useState } from 'react';
import L from 'leaflet';
import { Marker, Popup, useMapEvents } from 'react-leaflet';
import ReactDOM from 'react-dom';

export var mapIcon1 = L.icon({
  iconUrl: 'https://neoneuro.com/images/map-marker-icon.png',
  iconSize: [40, 40],
  iconAnchor: [20, 41],
  popupAnchor: [0, -41]
});

export var markersPositions = [
  [45.019325, 39.028455, <div>Перекрёсток. <br /> Ставропольская / Димитрова</div>],
  [45.016650, 39.041828, <div>Перекрёсток. <br /> Ставропольская / Старокубанская</div>],
  [45.021274, 39.019029, <div>Перекрёсток. <br /> Ставропольская / Полины Осипенко</div>],
];

export var linePositions = [
  [[45.020258, 39.024033], [45.018384, 39.032913], { color: "DarkRed" }, <div>Отрезок ул. Ставропольская</div>],
  [[45.017096, 39.027515], [45.019322, 39.028454], { color: "DarkRed" }, <div>Отрезок ул. Димитрова</div>],
  [[45.018837, 39.042512], [45.016850, 39.042083], [45.016653, 39.041847], [45.016653, 39.041096], [45.017164, 39.038775], { color: "DarkBlue" }, <div>Отрезок ул. Старокубанская</div>],
  [[45.016231, 39.043914], [45.017194, 39.039247], { color: "DarkBlue" }, <div>Отрезок ул. Ставропольская</div>],
  [[45.021236, 39.015943], [45.021344, 39.018163], [45.021172, 39.019687], [45.020598, 39.022511], { color: "DarkGreen" }, <div>Отрезок ул. Ставропольская</div>],
  [[45.019871, 39.019209], [45.021274, 39.019029], { color: "DarkGreen" }, <div>Отрезок ул. Полины Осипенко</div>],
];

const AddMarker = () => {
  const [position, setPosition] = useState(null);

  useMapEvents({
    click: (e) => {
      setPosition(e.latlng);
      if (position !== null) {
        markersPositions.push([position['lat'], position['lng'], <div>Просто новый попап</div>]);
      }
      ReactDOM.render(
        <React.StrictMode>
          <OpenModalForm />
        </React.StrictMode>,
        document.getElementById('modalRoot')
      );
    },
  });

  return position === null ? null : (
    <Marker position={position} icon={mapIcon1}>
      <Popup>
        Новый попап
      </Popup>
    </Marker>
  );
};

export default AddMarker;
