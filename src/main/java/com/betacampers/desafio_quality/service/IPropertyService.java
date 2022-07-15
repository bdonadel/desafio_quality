package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.Property;

import java.math.BigDecimal;
import java.util.List;

public interface IPropertyService {

    /**
     * Metódo do service responsável pela lógica de salvar um imóvel na base de dados.
     * @param property - objeto PropertyRequestDto com informações de um imóvel
     * @return Objeto Property que foi salvo salvo.
     */
    Property saveProperty(PropertyRequestDto property);

    /**
     * Metódo do service responsável pela lógica de calcular a área de um imóvel dado seu id.
     * @param propertyId - Long que representa id do imóvel
     * @return Valor Double da área.
     */
    Double getPropertyArea(Long propertyId);

    /**
     * Metódo do service responsável pela lógica de calcular o valor de um imóvel (área * valor m^2 n o bairro) dado seu id.
     * @param propertyId - Long que representa id do imóvel
     * @return Valor BigDecimal representando o valor.
     */
    BigDecimal getPropertyValue(Long propertyId);

    /**
     * Metódo do service responsável pela lógica de retornar o maior cômodo do imóvel dado seu id.
     * @param propertyId - Long que representa id do imóvel
     * @return Objeto RoomResponseDto representando o maior cômodo.
     */
    RoomResponseDto getLargestRoom(Long propertyId);

    /**
     * Metódo do service responsável pela lógica de calcular a área de cada cômodo do imóvel dado seu id.
     * @param propertyId - Long que representa id do imóvel
     * @return Lista de RoomResponseDto representando os cômodos com suas áreas.
     */
    List<RoomResponseDto> getRoomsArea(Long propertyId);

}
