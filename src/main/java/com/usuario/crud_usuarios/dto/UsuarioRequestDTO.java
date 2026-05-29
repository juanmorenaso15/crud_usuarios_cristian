package com.usuario.crud_usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    /**
     * Nombre completo del usuario.
     * No puede estar vacío y debe tener entre 2 y 100 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    /**
     * Correo electrónico del usuario.
     * No puede estar vacío y debe tener un formato de email válido.
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    private String email;

    /**
     * Número de teléfono del usuario.
     * No puede estar vacío y debe contener solo números entre 7 y 15 dígitos.
     */
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Teléfono inválido")
    private String telefono;

    /**
     * Edad del usuario en años.
     * No puede ser nula y debe estar entre 1 y 120 años.
     */
    @NotNull(message = "La edad es obligatoria")
    @Min(value = 1, message = "Edad mínima 1 año")
    @Max(value = 120, message = "Edad máxima 120 años")
    private Integer edad;
}