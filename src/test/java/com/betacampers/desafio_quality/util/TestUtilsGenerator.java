package com.betacampers.desafio_quality.util;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestUtilsGenerator {
    public static Property getNewProperty() {
        return getNewProperty(new Room("Sala", 10, 10), new Room("Quarto", 20, 20));
    }

    public static Property getNewProperty(Room... rooms) {
        District district = new District(1, "Centro", new BigDecimal("10"));
        return getNewProperty(district, rooms);
    }

    public static Property getNewProperty(District district, Room... rooms) {
        return new Property(1, "Casa", district, List.of(rooms));
    }

    public static Property getPropertyWithoutRoom() {
        var district = new District(2, "Jardim Paulista", new BigDecimal("5"));
        return new Property(3, "Casa", district, new ArrayList<>());

    }
}
