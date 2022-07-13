package com.betacampers.desafio_quality.model;

import java.util.List;
import java.util.UUID;

public class Property {
    UUID propId;
    String propName;
    UUID propDistrict;
    List<Room> propRooms;
}
