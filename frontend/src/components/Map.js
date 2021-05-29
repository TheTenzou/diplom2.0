import AddMarker, { mapIcon1, markersPositions, linePositions } from './AddNewMarker';
import React, { Component } from 'react';
import { MapContainer, LayersControl, TileLayer, LayerGroup, Marker, Popup, Polyline } from 'react-leaflet';

export default class MyMap extends Component {
  render() {
    return (
      <MapContainer style={{height: '100%', width: '100%'}} center={markersPositions[0]} zoom={16} scrollWheelZoom={true}>
        <LayersControl position="topright">
          <LayersControl.BaseLayer checked name="OpenStreetMap.Mapnik">
            <TileLayer
              attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
          </LayersControl.BaseLayer>
          <LayersControl.Overlay checked name="Маркеры">
            <LayerGroup>
              <Markers/>
              <AddMarker />
            </LayerGroup>
          </LayersControl.Overlay>

          <LayersControl.Overlay checked name="Дороги">
            <LayerGroup>
              <Lines />
            </LayerGroup>
          </LayersControl.Overlay>
        </LayersControl>
      </MapContainer>
    );
  }
}

function Markers() {
  let items = [];

  for (let i = 0; i < markersPositions.length; ++i) {
    items.push(<Marker key={i} position={markersPositions[i]} icon={mapIcon1}><Popup>{markersPositions[i][2]}</Popup></Marker>);
  }

  return <div>{items}</div>;
}

const Lines = () => {
  let items = [];

  for (let i = 0; i < linePositions.length; ++i) {
    items.push(
      <Polyline key={i} pathOptions={linePositions[i].slice(-2, -1)[0]} positions={linePositions[i].slice(0, -2)}>
        <Popup>{ linePositions[i].slice(-1)[0] }</Popup>
      </Polyline>
    );
  }

  return <div>{items}</div>
}