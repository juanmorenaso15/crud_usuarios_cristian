package com.usuario.crud_usuarios.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {

    /** Identificador único del usuario (autogenerado) */
    private Long id;

    /** Nombre completo del usuario */
    private String nombre;

    /** Correo electrónico del usuario (único) */
    private String email;

    /** Número de teléfono del usuario */
    private String telefono;

    /** Edad del usuario en años */
    private Integer edad;

    /** Fecha y hora en que se registró el usuario en el sistema */
    private LocalDateTime fechaRegistro;
}