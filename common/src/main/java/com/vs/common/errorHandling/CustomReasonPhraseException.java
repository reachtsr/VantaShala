package com.vs.common.errorHandling;

import javax.ws.rs.core.Response;

public class CustomReasonPhraseException extends Exception {
		
	private static final long serialVersionUID = -271582074543512905L;
	
	private final Response.Status status;

	public CustomReasonPhraseException(Response.Status status, String message) {
		super(message);
		this.status = status;
	}

	public Response.Status getBusinessCode() {
		return status;
	}
		
}
