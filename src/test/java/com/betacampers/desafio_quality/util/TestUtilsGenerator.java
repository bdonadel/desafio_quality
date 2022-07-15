package com.betacampers.desafio_quality.util;

import ch.qos.logback.core.net.ObjectWriter;
import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import com.betacampers.desafio_quality.repository.IDistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestUtilsGenerator {
    private static String SCOPE;
    @Autowired
    private IDistrictRepository districtRepository;

    public static void emptyUsersFile() {
        Properties properties = new Properties();

        try {
            properties.load(new ClassPathResource("application.properties").getInputStream());
            SCOPE = properties.getProperty("api.scope");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter writerDistrict = null;
        PrintWriter writerProperty = null;

        try {
            writerDistrict = new PrintWriter(ResourceUtils.getFile("./src/" + SCOPE + "/resources/district.json"));
            writerProperty = new PrintWriter(ResourceUtils.getFile("./src/" + SCOPE + "/resources/property.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        writerDistrict.print("{}");
        writerDistrict.close();

        writerProperty.print("{}");
        writerProperty.close();
    }


    public static Property getNewProperty() {
        return getNewProperty(getRooms().get(0), getRooms().get(1));
    }

    public static Property getNewProperty(Room... rooms) {
        District district = new District(1L, "Centro", new BigDecimal("10"));
        return getNewProperty(district, rooms);
    }

    public static Property getNewProperty(District district) {
        List<Room> rooms = getRooms();
        return new Property(1L, "Casa", district, rooms);
    }

    public static Property getNewProperty(District district, Room... rooms) {
        return new Property(1L, "Casa", district, List.of(rooms));
    }

    public static Property getPropertyWithoutRoom() {
        District district = new District(2L, "Jardim Paulista", new BigDecimal("5"));
        return new Property(3L, "Casa", district, new ArrayList<>());

    }

    public static PropertyRequestDto getNewPropertyRequest() {
        return new PropertyRequestDto("Casa", 1L, getRooms());
    }

    public static District getNewDistrict() {
        return new District("Bairro", new BigDecimal(10));
    }

    public static District getNewDistrictWithId() {
        return new District(1L, "Bairro", new BigDecimal(10));
    }

    public static List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Cozinha", 4, 3.5));
        rooms.add(new Room("Quarto", 2.5, 3.5));
        return rooms;
    }
}
