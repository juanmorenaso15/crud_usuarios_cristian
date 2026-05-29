package com.usuario.crud_usuarios.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usuario.crud_usuarios.dto.UsuarioRequestDTO;
import com.usuario.crud_usuarios.dto.UsuarioResponseDTO;
import com.usuario.crud_usuarios.entity.Usuario;
import com.usuario.crud_usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    /** Repositorio para acceder a la base de datos de usuarios */
    private final UsuarioRepository usuarioRepository;

    /**
     * Obtiene una lista con todos los usuarios registrados en el sistema.
     * 
     * @return Lista de UsuarioResponseDTO con los datos de todos los usuarios
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su ID.
     * 
     * @param id ID del usuario a buscar
     * @return UsuarioResponseDTO con los datos del usuario encontrado
     * @throws RuntimeException Si no se encuentra el usuario con el ID especificado
     */
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return convertirAResponseDTO(usuario);
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * 
     * @param request DTO con los datos del usuario a crear
     * @return UsuarioResponseDTO con los datos del usuario creado (incluyendo ID y
     *         fecha)
     * @throws RuntimeException Si ya existe un usuario con el mismo email
     */
    @Transactional
    public UsuarioResponseDTO crear(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + request.getEmail());
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        usuario.setEdad(request.getEdad());

        Usuario saved = usuarioRepository.save(usuario);
        return convertirAResponseDTO(saved);
    }

    /**
     * Actualiza los datos de un usuario existente.
     * 
     * @param id      ID del usuario a actualizar
     * @param request DTO con los nuevos datos del usuario
     * @return UsuarioResponseDTO con los datos actualizados del usuario
     * @throws RuntimeException Si el usuario no existe o si el email ya está en uso
     *                          por otro usuario
     */
    @Transactional
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Verificar email duplicado (excluyendo el actual)
        if (!usuario.getEmail().equals(request.getEmail()) &&
                usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + request.getEmail());
        }

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        usuario.setEdad(request.getEdad());

        Usuario updated = usuarioRepository.save(usuario);
        return convertirAResponseDTO(updated);
    }

    /**
     * Elimina un usuario del sistema.
     * 
     * @param id ID del usuario a eliminar
     * @throws RuntimeException Si no se encuentra el usuario con el ID especificado
     */
    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Usuario a un DTO de respuesta.
     * Método privado de utilidad para la transformación de datos.
     * 
     * @param usuario Entidad Usuario a convertir
     * @return UsuarioResponseDTO con los datos formateados para la respuesta
     */
    private UsuarioResponseDTO convertirAResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .edad(usuario.getEdad())
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }
}
