package com.mateuspaz.probook.mapper;

import org.mapstruct.Mapper;

import com.mateuspaz.probook.dto.UsuarioDTO;
import com.mateuspaz.probook.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends IMapper<Usuario, UsuarioDTO> {

}
