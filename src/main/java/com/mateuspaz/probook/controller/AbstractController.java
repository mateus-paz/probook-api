package com.mateuspaz.probook.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mateuspaz.probook.dto.PBEntityDTO;
import com.mateuspaz.probook.entity.PBEntity;
import com.mateuspaz.probook.facade.AbstractFacade;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public abstract class AbstractController<ENTITY extends PBEntity, DTO extends PBEntityDTO> {

	private final AbstractFacade<ENTITY, DTO> facade;

	protected AbstractController(AbstractFacade<ENTITY, DTO> facade) {
		this.facade = facade;
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DTO> salvar(@RequestBody @Valid DTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(facade.salvar(dto));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DTO> atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid DTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(facade.atualizar(id, dto));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DTO>> buscarTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(facade.buscarTodos());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DTO> buscarPorId(@PathVariable(value = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(facade.buscarPorId(id));
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> excluir(@PathVariable(value = "id") Long id) {
		facade.excluir(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
