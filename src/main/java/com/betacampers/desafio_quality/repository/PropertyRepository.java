package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class PropertyRepository implements IPropertyRepository {

    private Map<Long, Property> properties;

    private void createProperties(){
        properties = new HashMap<>();

        District d1 = new District(1L, "Barreiros", new BigDecimal(4500));
        District d2 = new District(2L, "Campinas", new BigDecimal(5000));
        District d3 = new District(3L, "Kobrasol", new BigDecimal(4900));


        List<Room> rooms = new ArrayList<>(Arrays.asList(new Room("Quarto de solteiro", 2.5, 4.2),
                new Room("Quarto de casal", 3.5, 4.6), new Room("Cozinha", 3.6, 4.8),
                new Room("Banheiro", 1.8, 2.4)));

        Property p1 = new Property(1L, "Casa A", d1, rooms);


        rooms = new ArrayList<>(Arrays.asList(new Room("Quarto", 2.5, 4.2),
                new Room("Cozinha", 2.5, 3.0),
                new Room("Banheiro", 1.5, 2.2)));
        Property p2 = new Property(2L, "Apartamento 12", d2, rooms);

        rooms = new ArrayList<>(Arrays.asList(new Room("Quarto", 3.5, 3.2),
                new Room("Cozinha", 2.5, 3.2),
                new Room("Banheiro", 2.0, 1.6)));
        Property p3 = new Property(3L, "Apartamento 08", d3, rooms);

        properties.put(p1.getPropId(), p1);
        properties.put(p2.getPropId(), p2);
        properties.put(p3.getPropId(), p3);
    }


    @Override
    public Property getById(long propertyId) {
        this.createProperties();
        return null;
    }
}
