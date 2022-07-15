package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;

public interface IDistrictRepository {
    /**
     * Metódo do repository responsável por retornar um bairro dado um id.
     *
     * @param districtId - Long que representa o id do bairro
     * @return Objeto District.
     */
    District getById(Long districtId);

    /**
     * Metódo do repository responsável por salvar um bairro na base de dados.
     *
     * @param district - objeto District que será salvo
     * @return Objeto District que foi salvo salvo.
     */
    District save(District district);
}
