package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.Property;

/**
 * Interface para a camada Repository da Property.
 */
public interface IPropertyRepository {

    /**
     * Método do repository responsável por retornar um imóvel dado um id.
     *
     * @param propertyId Long que representa o id do imóvel
     * @return Objeto Property.
     */
    Property getById(Long propertyId);

    /**
     * Método do repository responsável por salvar um imóvel na base de dados.
     *
     * @param property objeto Property que será salvo
     * @return Objeto Property que foi salvo salvo.
     */
    Property save(Property property);
}
