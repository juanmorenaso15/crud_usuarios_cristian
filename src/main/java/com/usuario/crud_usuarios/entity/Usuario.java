package com.usuario.crud_usuarios.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    /** Identificador único del usuario (autogenerado por la base de datos) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo del usuario. No puede ser nulo y tiene un máximo de 100
     * caracteres
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Correo electrónico del usuario. Debe ser único, no nulo y tiene un máximo de
     * 100 caracteres
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Número de teléfono del usuario. No puede ser nulo y tiene un máximo de 20
     * caracteres
     */
    @Column(nullable = false, length = 20)
    private String telefono;

    /** Edad del usuario en años. No puede ser nula */
    @Column(nullable = false)
    private Integer edad;

    /**
     * Fecha y hora en que se registró el usuario. Se asigna automáticamente antes
     * de persistir
     */
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    /**
     * Método callback que se ejecuta antes de persistir la entidad por primera vez.
     * Asigna automáticamente la fecha y hora actual del sistema.
     */
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}