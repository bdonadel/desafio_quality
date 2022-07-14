package com.betacampers.desafio_quality.util;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;

import java.math.BigDecimal;
import java.util.List;

public class TestUtilsGenerator {
    public static Property getNewProperty() {
        return getNewProperty(new Room("Sala", 10, 10), new Room("Quarto", 20, 20));
    }

    public static Property getNewProperty(Room... rooms) {
        var district = new District(1, "Centro", new BigDecimal("10"));
        return new Property(1, "Casa", district, List.of(rooms));
    }
}
