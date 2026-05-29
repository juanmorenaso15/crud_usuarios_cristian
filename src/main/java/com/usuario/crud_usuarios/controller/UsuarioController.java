package com.usuario.crud_usuarios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.usuario.crud_usuarios.dto.UsuarioRequestDTO;
import com.usuario.crud_usuarios.dto.UsuarioResponseDTO;
import com.usuario.crud_usuarios.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "listar";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuarioRequest", new UsuarioRequestDTO());
        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("accion", "Crear");
        return "formulario";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("usuarioRequest") UsuarioRequestDTO request,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nuevo Usuario");
            model.addAttribute("accion", "Crear");
            return "formulario";
        }
        
        try {
            usuarioService.crear(request);
            redirectAttributes.addFlashAttribute("success", "Usuario creado exitosamente");
            return "redirect:/usuarios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/usuarios/nuevo";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
            UsuarioRequestDTO request = new UsuarioRequestDTO();
            request.setNombre(usuario.getNombre());
            request.setEmail(usuario.getEmail());
            request.setTelefono(usuario.getTelefono());
            request.setEdad(usuario.getEdad());
            
            model.addAttribute("usuarioRequest", request);
            model.addAttribute("usuarioId", id);
            model.addAttribute("titulo", "Editar Usuario");
            model.addAttribute("accion", "Actualizar");
            return "formulario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/usuarios";
        }
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("usuarioRequest") UsuarioRequestDTO request,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("usuarioId", id);
            model.addAttribute("titulo", "Editar Usuario");
            model.addAttribute("accion", "Actualizar");
            return "formulario";
        }
        
        try {
            usuarioService.actualizar(id, request);
            redirectAttributes.addFlashAttribute("success", "Usuario actualizado exitosamente");
            return "redirect:/usuarios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/usuarios/editar/" + id;
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Usuario eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuarios";
    }
    
    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
            model.addAttribute("usuario", usuario);
            return "ver";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/usuarios";
        }
    }
}
