package com.betacampers.desafio_quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomResponseDto {
    private String roomName;
    private double roomWidth;
    private double roomLength;
    private double roomArea;
}
