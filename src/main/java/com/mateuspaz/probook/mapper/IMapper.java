package com.mateuspaz.probook.mapper;

import com.mateuspaz.probook.dto.PBEntityDTO;
import com.mateuspaz.probook.entity.PBEntity;

public interface IMapper<ENTITY extends PBEntity, DTO extends PBEntityDTO> {

	ENTITY toEntity(DTO dto);

	DTO toDTO(ENTITY entity);

}
