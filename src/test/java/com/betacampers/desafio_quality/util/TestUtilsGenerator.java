package com.betacampers.desafio_quality.util;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtilsGenerator {


    public static Property getPropertyWithId() {
        List<Room> rooms = new ArrayList<>(Arrays.asList(new Room("Quarto de solteiro", 2.5, 4.2),
                new Room("Quarto de casal", 3.5, 4.6), new Room("Cozinha", 3.6, 4.8),
                new Room("Banheiro", 1.8, 2.4)));

        District district = new District(1, "Centro", new BigDecimal("10"));
        return new Property(5l, "Casa", district, rooms);
    }
}
