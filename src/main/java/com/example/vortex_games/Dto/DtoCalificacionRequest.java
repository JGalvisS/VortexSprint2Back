package com.example.vortex_games.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DtoCalificacionRequest {
    private String username;
    private Long productoId;
    private Integer valorCalificacion;
    private String comentario;
}
