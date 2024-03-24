package com.mateuspaz.probook.entity.listener;

import java.time.LocalDateTime;

import com.mateuspaz.probook.entity.PBEntity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class PBEntityListener {

	@PrePersist
	public void prePersist(PBEntity entity) {
		entity.setDataInsercao(LocalDateTime.now());
	}

	@PreUpdate
	public void preUpdate(PBEntity entity) {
		entity.setDataAtualizacao(LocalDateTime.now());
	}

}
