package com.mateuspaz.probook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mateuspaz.probook.dto.UsuarioDTO;
import com.mateuspaz.probook.entity.Usuario;
import com.mateuspaz.probook.facade.UsuarioFacade;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController extends AbstractController<Usuario, UsuarioDTO> {

	protected UsuarioController(UsuarioFacade facade) {
		super(facade);
	}

}
