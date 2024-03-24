package com.mateuspaz.probook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO extends PBEntityDTO {

	@NotBlank(message = "O preenchimento do Nome é obrigatório")
	@Size(min = 3, max = 200, message = "O Nome deve ter entre 3 e 200 caracteres")
	private String nome;

	@NotBlank(message = "O preenchimento do Nome de Usuário é obrigatório")
	@Size(min = 3, max = 200, message = "O Nome de Usuário deve ter entre 3 e 200 caracteres")
	private String nomeUsuario;

	@NotBlank(message = "O preenchimento do Email é obrigatório")
	@Email(message = "O Email fornecido é inválido")
	@Size(max = 200, message = "O Email deve ter no máximo 200 caracteres")
	private String email;

	@NotBlank(message = "O preenchimento da Senha é obrigatório")
	@Size(min = 8, max = 8, message = "A Senha deve ter exatamente 8 caracteres")
	private String senha;

}
