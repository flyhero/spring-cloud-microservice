package io.github.flyhero.common.exception;

import lombok.Data;


@Data
public class ArgumentError {
	
	private String argumentName;
	
	private String message;
}
