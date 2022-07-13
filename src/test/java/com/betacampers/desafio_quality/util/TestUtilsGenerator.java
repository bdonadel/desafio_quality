package com.betacampers.desafio_quality.util;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtilsGenerator {

    public static Property getPropertyOk() {
        District d = new District(10000L, "Teste", new BigDecimal(10));

        List<Room> rooms = new ArrayList<>(Arrays.asList(new Room("Quarto 1", 3.0, 3.0), new Room("Cozinha", 2.0, 2.0)));

        Property p = new Property(1000L, "Casa Teste", d, rooms);

        return p;
    }
}
