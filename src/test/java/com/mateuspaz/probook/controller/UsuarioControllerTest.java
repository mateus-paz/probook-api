package com.mateuspaz.probook.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateuspaz.probook.dto.UsuarioDTO;
import com.mateuspaz.probook.entity.Usuario;
import com.mateuspaz.probook.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class UsuarioControllerTest {

	private static final String API_USUARIOS = "/api/usuarios";

	private static final String NOME = "User One";

	private static final String NOME_USUARIO = "user_one";

	private static final String EMAIL = "user_one@test.com";

	private static final String SENHA = "Pass123*";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@AfterEach
	public void setUp() {
		usuarioRepository.deleteAll();
	}

	@Test
	void deveSalvarUmNovoUsuario() throws Exception {
		UsuarioDTO user = new UsuarioDTO(NOME, NOME_USUARIO, EMAIL, SENHA);

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isCreated());
		//@formatter:on
	}

	@Test
	void naoDeveSalvarUmUsuarioComOsCamposNulos() throws Exception {
		UsuarioDTO user = new UsuarioDTO();

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("O preenchimento do Email é obrigatório; ")))
		.andExpect(content().string(containsString("O preenchimento do Nome é obrigatório; ")))
		.andExpect(content().string(containsString("O preenchimento do Nome de Usuário é obrigatório; ")))
		.andExpect(content().string(containsString("O preenchimento da Senha é obrigatório; ")));
		//@formatter:on

	}

	@Test
	void naoDeveSalvarUmUsuarioComOsCamposEmBranco() throws Exception {
		UsuarioDTO user = new UsuarioDTO("", "", "", "");

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("O preenchimento do Email é obrigatório; ")))
		.andExpect(content().string(containsString("O preenchimento do Nome é obrigatório; ")))
		.andExpect(content().string(containsString("O preenchimento do Nome de Usuário é obrigatório; ")))
		.andExpect(content().string(containsString("O preenchimento da Senha é obrigatório; ")))
		.andExpect(content().string(containsString("O Nome deve ter entre 3 e 200 caracteres; ")))
		.andExpect(content().string(containsString("O Nome de Usuário deve ter entre 3 e 200 caracteres;")))
		.andExpect(content().string(containsString("A Senha deve ter exatamente 8 caracteres; ")));
		//@formatter:on
	}

	@Test
	void naoDeveSalvarUmUsuarioComOsCamposUltrapassandoLimiteCaracteres() throws Exception {
		String STRING_COM_MAIS_DE_200_CARACTERES = new String(new char[201]).replace('\0', 'a');

		UsuarioDTO user = new UsuarioDTO(STRING_COM_MAIS_DE_200_CARACTERES, STRING_COM_MAIS_DE_200_CARACTERES,
				STRING_COM_MAIS_DE_200_CARACTERES, STRING_COM_MAIS_DE_200_CARACTERES);

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("O Nome deve ter entre 3 e 200 caracteres; ")))
		.andExpect(content().string(containsString("O Nome de Usuário deve ter entre 3 e 200 caracteres;")))
		.andExpect(content().string(containsString("A Senha deve ter exatamente 8 caracteres; ")))
		.andExpect(content().string(containsString("Email deve ter no máximo 200 caracteres; ")))
		.andExpect(content().string(containsString("O Email fornecido é inválido; ")));
		//@formatter:on
	}

	@Test
	void naoDeveSalvarUmUsuarioComEmailInvalido() throws Exception {
		final String EMAIL_INVALIDO = "user_onetestcom";

		UsuarioDTO user = new UsuarioDTO(NOME, NOME_USUARIO, EMAIL_INVALIDO, SENHA);

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("O Email fornecido é inválido; ")));
		//@formatter:on
	}

	@Test
	void naoDeveSalvarUmNovoUsuarioPorJaTerEmailCadastrado() throws Exception {
		UsuarioDTO user = new UsuarioDTO(NOME, NOME_USUARIO, EMAIL, SENHA);

		usuarioRepository.save(new Usuario(NOME, NOME_USUARIO, EMAIL, SENHA));

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("O Email informado já foi cadastrado")));
		//@formatter:on
	}

	@Test
	void naoDeveSalvarUmNovoUsuarioPorJaTerNomeUsuarioCadastrado() throws Exception {
		final String EMAIL_DIFERENTE = "user_two@test.com";

		UsuarioDTO user = new UsuarioDTO(NOME, NOME_USUARIO, EMAIL_DIFERENTE, SENHA);

		usuarioRepository.save(new Usuario(NOME, NOME_USUARIO, EMAIL, SENHA));

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(objectMapper.writeValueAsString(user)))
		.andDo(print())
	    .andExpect(status().isBadRequest())
	    .andExpect(content().string(containsString("O Nome de Usuário informado já foi cadastrado")));
		//@formatter:on
	}

	@Test
	void naoDeveSalvarUmNovoUsuarioPorTerSenhaComMaisDeOitoCaracteres() throws Exception {
		final String SENHA_COM_MAIS_DE_OITO_CARACTERES = "Pass1234*";

		UsuarioDTO user = new UsuarioDTO(NOME, NOME_USUARIO, EMAIL, SENHA_COM_MAIS_DE_OITO_CARACTERES);

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("A Senha deve ter exatamente 8 caracteres; ")));
		//@formatter:on
	}

	@Test
	void naoDeveSalvarUmNovoUsuarioPorTerSenhaComMenosDeOitoCaracteres() throws Exception {
		final String SENHA_COM_MENOS_DE_OITO_CARACTERES = "Pass12*";

		UsuarioDTO user = new UsuarioDTO(NOME, NOME_USUARIO, EMAIL, SENHA_COM_MENOS_DE_OITO_CARACTERES);

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("A Senha deve ter exatamente 8 caracteres; ")));
		//@formatter:on
	}

	@Test
	void naoDeveSalvarUmNovoUsuarioPorTerSenhaSemLetraMaiuscula() throws Exception {
		final String SENHA_SEM_LETRA_MAIUSCULA = "pass123*";

		UsuarioDTO user = new UsuarioDTO(NOME, NOME_USUARIO, EMAIL, SENHA_SEM_LETRA_MAIUSCULA);

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("A Senha deve conter pelo menos 1 letra maiúscula")));
		//@formatter:on
	}

	@Test
	void naoDeveSalvarUmNovoUsuarioPorTerSenhaSemLetraMinuscula() throws Exception {
		final String SENHA_SEM_LETRA_MAIUSCULA = "PASS123*";

		UsuarioDTO user = new UsuarioDTO(NOME, NOME_USUARIO, EMAIL, SENHA_SEM_LETRA_MAIUSCULA);

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("A Senha deve conter pelo menos 1 letra minúscula")));
		//@formatter:on
	}

	@Test
	void naoDeveSalvarUmNovoUsuarioPorTerSenhaSemNumero() throws Exception {
		final String SENHA_SEM_NUMERO = "PASSone*";

		UsuarioDTO user = new UsuarioDTO(NOME, NOME_USUARIO, EMAIL, SENHA_SEM_NUMERO);

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("A Senha deve conter pelo menos 1 número")));
		//@formatter:on
	}

	@Test
	void naoDeveSalvarUmNovoUsuarioPorTerSenhaSemCaractereEspecial() throws Exception {
		final String SENHA_SEM_CARACTERE_ESPECIAL = "Pass1234";

		UsuarioDTO user = new UsuarioDTO(NOME, NOME_USUARIO, EMAIL, SENHA_SEM_CARACTERE_ESPECIAL);

		//@formatter:off
		mockMvc.perform(post(API_USUARIOS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("A Senha deve conter pelo menos 1 caractere especial")));
		//@formatter:on
	}

}
