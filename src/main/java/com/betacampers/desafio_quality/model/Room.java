package com.betacampers.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private String roomName;
    private double roomWidth;
    private double roomLength;
}
