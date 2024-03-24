package com.mateuspaz.probook.facade;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mateuspaz.probook.dto.PBEntityDTO;
import com.mateuspaz.probook.entity.PBEntity;
import com.mateuspaz.probook.mapper.IMapper;
import com.mateuspaz.probook.service.AbstractService;

import jakarta.persistence.EntityNotFoundException;

public abstract class AbstractFacade<ENTITY extends PBEntity, DTO extends PBEntityDTO> {

	private final AbstractService<ENTITY> service;

	private final IMapper<ENTITY, DTO> mapper;

	protected AbstractFacade(AbstractService<ENTITY> service, IMapper<ENTITY, DTO> mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public DTO salvar(DTO dto) {
		return mapper.toDTO(service.salvar(mapper.toEntity(dto)));
	}

	public DTO atualizar(Long id, DTO dto) {
		ENTITY entity = service.buscarPorId(id);

		if (Objects.isNull(entity))
			throw new EntityNotFoundException("O id informado não corresponde a nenhum registro salvo");

		dto.setId(id);

		return mapper.toDTO(service.atualizar(mapper.toEntity(dto)));
	}

	public List<DTO> buscarTodos() {
		return service.buscarTodos().stream().map((entity) -> mapper.toDTO(entity)).collect(Collectors.toList());
	}

	public DTO buscarPorId(Long id) {
		return mapper.toDTO(service.buscarPorId(id));
	}

	public void excluir(Long id) {
		service.excluir(id);
	}

}
