package com.mateuspaz.probook.service;

import org.springframework.stereotype.Service;

import com.mateuspaz.probook.entity.Usuario;
import com.mateuspaz.probook.repository.UsuarioRepository;
import com.mateuspaz.probook.strategy.validator.ValidaDadosUsuario;

@Service
public class UsuarioService extends AbstractService<Usuario> {

	protected UsuarioService(UsuarioRepository repository, ValidaDadosUsuario validaDadosUsuario) {
		super(repository, validaDadosUsuario);
	}

}
