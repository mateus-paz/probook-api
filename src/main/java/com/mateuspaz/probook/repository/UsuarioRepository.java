package com.mateuspaz.probook.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mateuspaz.probook.entity.Usuario;

@Repository
public interface UsuarioRepository extends PBRepository<Usuario> {

	Optional<Usuario> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByNomeUsuario(String nomeUsuario);

}
