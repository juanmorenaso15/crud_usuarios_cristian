package com.usuario.crud_usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usuario.crud_usuarios.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param email Correo electrónico del usuario a buscar
     * @return Optional que contiene el usuario si existe, o vacío si no se
     *         encuentra
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si ya existe un usuario registrado con el correo electrónico
     * especificado.
     * Útil para validar unicidad de email antes de crear o actualizar un usuario.
     * 
     * @param email Correo electrónico a verificar
     * @return true si ya existe un usuario con ese email, false en caso contrario
     */
    boolean existsByEmail(String email);
}
