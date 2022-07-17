package com.betacampers.desafio_quality.dto;

import com.betacampers.desafio_quality.model.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de resposta para cômodo do imóvel, contendo a área
 */
@Getter
@Setter
@NoArgsConstructor
public class RoomResponseDto {
    private String roomName;
    private double roomWidth;
    private double roomLength;
    private double roomArea;

    public RoomResponseDto(Room room) {
        roomName = room.getRoomName();
        roomWidth = room.getRoomWidth();
        roomLength = room.getRoomLength();
        roomArea = roomWidth * roomLength;
    }
}
