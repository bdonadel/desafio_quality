package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PropertyRepository implements IPropertyRepository {
    private Map<Long, Property> properties;

    public PropertyRepository() {
        createProperties();
    }

    private void createProperties(){
        properties = new HashMap<>();
        DistrictRepository districts = new DistrictRepository();

        // Propriedade 1
        List<Room> rooms = new ArrayList<>(Arrays.asList(new Room("Quarto de solteiro", 2.5, 4.2),
                new Room("Quarto de casal", 3.5, 4.6),
                new Room("Cozinha", 3.6, 4.8),
                new Room("Banheiro", 1.8, 2.4)));
        Property p1 = new Property(1L, "Casa A", districts.getById(1) , rooms);

        // Propriedade 2
        rooms = new ArrayList<>(Arrays.asList(new Room("Quarto", 2.5, 4.2),
                new Room("Cozinha", 2.5, 3.0),
                new Room("Banheiro", 1.5, 2.2)));
        Property p2 = new Property(2L, "Apartamento 12", districts.getById(2), rooms);

        // Propriedade 3
        rooms = new ArrayList<>(Arrays.asList(new Room("Quarto", 3.5, 3.2),
                new Room("Cozinha", 2.5, 3.2),
                new Room("Banheiro", 2.0, 1.6)));
        Property p3 = new Property(3L, "Apartamento 08", districts.getById(3), rooms);

        // Propriedade 4
        rooms = new ArrayList<>();
        Property p4 = new Property(4L, "Apartamento 08", districts.getById(3), rooms);

        properties.put(p1.getPropId(), p1);
        properties.put(p2.getPropId(), p2);
        properties.put(p3.getPropId(), p3);
        properties.put(p4.getPropId(), p4);
    }


    @Override
    public Property getById(long propertyId) {
        if (!properties.containsKey(propertyId)){
            throw new RuntimeException("Não existe");
        }
        return properties.get(propertyId);
    }

    @Override
    public List<Property> getAll() {
        return new ArrayList<>(properties.values());
    }
}
