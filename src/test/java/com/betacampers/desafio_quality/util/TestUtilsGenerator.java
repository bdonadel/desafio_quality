package com.betacampers.desafio_quality.util;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;

import java.math.BigDecimal;
import java.util.List;

public class TestUtilsGenerator {
    public static Property getNewProperty() {
        var room1 = new Room("Sala", 10, 10);
        var room2 = new Room("Quarto", 20, 20);
        var rooms = List.of(room1, room2);
        var district = new District(1, "Centro", new BigDecimal("10"));

        return new Property(1, "Casa", district, rooms);
    }
}
