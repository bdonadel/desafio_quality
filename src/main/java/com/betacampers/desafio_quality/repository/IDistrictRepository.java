package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;

/**
 * Interface para a camada Repository do District.
 */
public interface IDistrictRepository {
    /**
     * Método do repository responsável por retornar um bairro dado um id.
     *
     * @param districtId Long que representa o id do bairro
     * @return Objeto District.
     */
    District getById(Long districtId);

    /**
     * Método do repository responsável por salvar um bairro na base de dados.
     *
     * @param district objeto District que será salvo
     * @return Objeto District que foi salvo.
     */
    District save(District district);

    /**
     * Método do repository responsável por retornar se um bairro existe na base de dados.
     *
     * @param district objeto District que será salvo
     * @return boolean
     */
    boolean exists(District district);
}
