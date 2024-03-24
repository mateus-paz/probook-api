package com.mateuspaz.probook.service;

import java.util.List;
import java.util.Objects;

import com.mateuspaz.probook.entity.PBEntity;
import com.mateuspaz.probook.repository.PBRepository;
import com.mateuspaz.probook.strategy.validator.IValidator;

public abstract class AbstractService<ENTITY extends PBEntity> {

	protected final PBRepository<ENTITY> repository;

	protected final IValidator<ENTITY> validator;

	protected AbstractService(PBRepository<ENTITY> repository, IValidator<ENTITY> validator) {
		this.repository = repository;
		this.validator = validator;
	}

	public ENTITY salvar(ENTITY entity) {
		if (Objects.nonNull(this.validator))
			validator.execute(entity);

		return repository.save(entity);
	}

	public ENTITY atualizar(ENTITY entity) {
		Objects.requireNonNull(entity.getId(), "O id informado não pode ser nulo");
		return this.salvar(entity);
	}

	public List<ENTITY> buscarTodos() {
		return repository.findAll();
	}

	public ENTITY buscarPorId(Long id) {
		Objects.requireNonNull(id, "O id informado não pode ser nulo");
		return repository.findById(id).orElse(null);
	}

	public void excluir(Long id) {
		Objects.requireNonNull(id, "O id informado não pode ser nulo");
		repository.deleteById(id);
	}

}
