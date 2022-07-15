package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.model.District;

public interface IDistrictService {

    /**
     * Método do service responsável pela lógica de salvar um bairro na base de dados.
     * @param district objeto District que será salvo
     * @return Objeto District que foi salvo salvo.
     */
    District save(District district);

    /**
     * Método do service responsável pela lógica de retornar um bairro dado um id.
     * @param districtId Long que representa id do bairro
     * @return Objeto District.
     */
    District getById(Long districtId);
}
