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
  [45.020854, 39.032514, <div>Стадион КубГУ. <br /> Смотрим футбол</div>],
  [45.018927, 39.029092, <div>Табрис. <br /> Здесь вкусные сэндвичи.</div>]
];

export var linePositions = [
  [[45.020258, 39.024033], [45.018384, 39.032913], { color: "red" }, <div>Отрезок ул. Ставропольская</div>],
  [[45.017096, 39.027515], [45.019322, 39.028454], { color: "blue" }, <div>Отрезок ул. Димитрова</div>]
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
