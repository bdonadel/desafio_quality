package com.betacampers.desafio_quality.dto;

import com.betacampers.desafio_quality.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
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
