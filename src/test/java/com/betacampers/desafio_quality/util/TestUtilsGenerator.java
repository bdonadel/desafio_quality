package com.betacampers.desafio_quality.util;

import ch.qos.logback.core.net.ObjectWriter;
import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

public class TestUtilsGenerator {

    private static String SCOPE;
    private static ObjectWriter mapper;

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
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        writerDistrict.print("{}");
        writerDistrict.close();

        writerProperty.print("{}");
        writerProperty.close();
    }


    public static Property getNewProperty() {
        return getNewProperty(new Room("Sala", 10, 10), new Room("Quarto", 20, 20));
    }

    public static District getNewDistrict() {
        return new District("Bairrão", new BigDecimal(10));
    }

    public static District getNewDistrictWithId() {
        return new District(1L, "Bairrão", new BigDecimal(10));
    }

    public static Property getNewProperty(Room... rooms) {
        var district = new District(1L, "Centro", new BigDecimal("10"));
        return new Property(1L, "Casa", district, List.of(rooms));
    }
}
