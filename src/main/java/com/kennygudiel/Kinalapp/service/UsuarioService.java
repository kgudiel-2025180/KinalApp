package com.kennygudiel.Kinalapp.service;

import com.kennygudiel.Kinalapp.entity.Usuario;
import com.kennygudiel.Kinalapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario actualizar(Integer id, Usuario usuario) {

        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id " + id);
        }

        usuario.setIdUsuario(id);

        validarUsuario(usuario);

        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Integer id) {

        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id " + id);
        }

        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Integer id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorEstado(Integer estado) {
        return usuarioRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }


    // VALIDACIONES
    private void validarUsuario(Usuario usuario) {

        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La password es obligatoria");
        }

    }

    /**
     * Método para autenticar usuarios
     * @param username Nombre de usuario
     * @param password Contraseña
     * @return Optional con el usuario si las credenciales son válidas
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> login(String username, String password) {
        // Buscar usuario por username
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        // Si existe, verificar contraseña y estado
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getPassword().equals(password) && usuario.getEstado() == 1) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

}
