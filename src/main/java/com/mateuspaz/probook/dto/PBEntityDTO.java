package com.mateuspaz.probook.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PBEntityDTO {

	private Long id;

	private LocalDateTime dataInsercao;

	private LocalDateTime dataAtualizacao;

}
