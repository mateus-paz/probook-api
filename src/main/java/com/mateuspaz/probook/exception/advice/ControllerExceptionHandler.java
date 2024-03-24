package com.mateuspaz.probook.exception.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mateuspaz.probook.exception.RegraNegocioException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(RegraNegocioException.class)
	public ResponseEntity<ErrorResponse> regraNegocioExceptionHandler(RegraNegocioException ex,
			HttpServletRequest request) {
		//@formatter:off
		ErrorResponse errorResponse = new ErrorResponse(
			LocalDateTime.now(), 
			HttpStatus.BAD_REQUEST.value(),
			HttpStatus.BAD_REQUEST.getReasonPhrase(), 
			ex.getMessage(), 
			request.getRequestURI()
		);
		//@formatter:on

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
				.body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> TransactionSystemExceptionHandler(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();

		ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
			sb.append(fieldError.getDefaultMessage());
			sb.append("; ");
		});

		//@formatter:off
		ErrorResponse errorResponse = new ErrorResponse(
			LocalDateTime.now(), 
			HttpStatus.BAD_REQUEST.value(),
			HttpStatus.BAD_REQUEST.getReasonPhrase(), 
			sb.toString(), 
			request.getRequestURI()
		);
		//@formatter:on

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
				.body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex, HttpServletRequest request) {
		//@formatter:off
		ErrorResponse errorResponse = new ErrorResponse(
			LocalDateTime.now(), 
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), 
			ex.getMessage(), 
			request.getRequestURI()
		);
		//@formatter:on

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
				.body(errorResponse);
	}

	@Data
	@AllArgsConstructor
	private static class ErrorResponse {

		private LocalDateTime timestamp;

		private int status;

		private String error;

		private String message;

		private String path;

	}

}
