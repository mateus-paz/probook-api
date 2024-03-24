package com.mateuspaz.probook.strategy.validator;

import org.springframework.stereotype.Component;

import com.mateuspaz.probook.entity.Usuario;
import com.mateuspaz.probook.exception.RegraNegocioException;
import com.mateuspaz.probook.repository.UsuarioRepository;

@Component
public class ValidaDadosUsuario implements IValidator<Usuario> {

	private final UsuarioRepository usuarioRepository;

	public ValidaDadosUsuario(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public void execute(Usuario entity) {
		if (usuarioRepository.existsByEmail(entity.getEmail()))
			throw new RegraNegocioException("O Email informado já foi cadastrado");

		if (usuarioRepository.existsByNomeUsuario(entity.getNomeUsuario()))
			throw new RegraNegocioException("O Nome de Usuário informado já foi cadastrado");

		String senha = entity.getSenha();
		if (!senha.matches(".*\\d.*")) {
			throw new RegraNegocioException("A Senha deve conter pelo menos 1 número");
		}

		if (!senha.matches(".*[a-z].*")) {
			throw new RegraNegocioException("A Senha deve conter pelo menos 1 letra minúscula");
		}

		if (!senha.matches(".*[A-Z].*")) {
			throw new RegraNegocioException("A Senha deve conter pelo menos 1 letra maiúscula");
		}

		if (!senha.matches(".*[@#$%^&+=*].*")) {
			throw new RegraNegocioException("A Senha deve conter pelo menos 1 caractere especial");
		}
	}

}
