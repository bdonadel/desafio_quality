package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import org.springframework.stereotype.Repository;
import static com.betacampers.desafio_quality.repository.DistrictRepository.getDistricts;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class PropertyRepository implements IPropertyRepository {

    private Map<UUID, Property> properties;

    public void createProperties(){

        // Propriedade 1
        List<Room> rooms = new ArrayList<>(Arrays.asList(new Room("Quarto de solteiro", 2.5, 4.2),
                new Room("Quarto de casal", 3.5, 4.6), new Room("Cozinha", 3.6, 4.8),
                new Room("Banheiro", 1.8, 2.4)));
        Property p1 = new Property(UUID.randomUUID(), "Casa A", getDistricts().get(1234L), rooms);

        // Propriedade 2
        rooms = new ArrayList<>(Arrays.asList(new Room("Quarto", 2.5, 4.2),
                new Room("Cozinha", 2.5, 3.0),
                new Room("Banheiro", 1.5, 2.2)));
        Property p2 = new Property(UUID.randomUUID(), "Apartamento 12", getDistricts().get(4567L), rooms);

        // Propriedade 3
        rooms = new ArrayList<>(Arrays.asList(new Room("Quarto", 3.5, 3.2),
                new Room("Cozinha", 2.5, 3.2),
                new Room("Banheiro", 2.0, 1.6)));
        Property p3 = new Property(UUID.randomUUID(), "Apartamento 08", getDistricts().get(8890L), rooms);

        properties.put(p1.getPropId(), p1);
        properties.put(p2.getPropId(), p2);
        properties.put(p3.getPropId(), p3);
    }


    @Override
    public Property getById(UUID propertyId) {
        this.createProperties();
        return null;
    }
}
