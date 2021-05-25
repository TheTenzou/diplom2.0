import AddMarker, { mapIcon1, positions } from './AddNewMarker';
import React, { Component } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';

export default class MyMap extends Component {
  render() {
    return (
      <MapContainer style={{height: '100%', width: '100%'}} center={positions[0]} zoom={16} scrollWheelZoom={true}>
        <TileLayer
          attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        <Markers/>
        <AddMarker />
      </MapContainer>
    );
  }
}

function Markers() {
  let items = [];

  for (let i = 0; i < positions.length; ++i) {
    items.push(<Marker key={i} position={positions[i]} icon={mapIcon1}><Popup>{positions[i][2]}</Popup></Marker>);
  }

  return <div>{items}</div>;
}
