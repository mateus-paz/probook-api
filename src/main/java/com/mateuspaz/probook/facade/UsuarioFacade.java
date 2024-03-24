package com.mateuspaz.probook.facade;

import org.springframework.stereotype.Service;

import com.mateuspaz.probook.dto.UsuarioDTO;
import com.mateuspaz.probook.entity.Usuario;
import com.mateuspaz.probook.mapper.UsuarioMapper;
import com.mateuspaz.probook.service.UsuarioService;

@Service
public class UsuarioFacade extends AbstractFacade<Usuario, UsuarioDTO> {

	protected UsuarioFacade(UsuarioService service, UsuarioMapper mapper) {
		super(service, mapper);
	}

}
