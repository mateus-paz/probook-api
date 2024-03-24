package com.mateuspaz.probook.entity;

import java.time.LocalDateTime;

import com.mateuspaz.probook.entity.listener.PBEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@EntityListeners({ PBEntityListener.class })
public class PBEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private LocalDateTime dataInsercao;

	private LocalDateTime dataAtualizacao;

}
