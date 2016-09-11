package com.vs.common.errorHandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomReasonPhraseExceptionMapper implements ExceptionMapper<CustomReasonPhraseException> {

	public Response toResponse(CustomReasonPhraseException bex) {
		return Response.status(new CustomReasonPhraseExceptionStatusType(Status.BAD_REQUEST))
				.entity("Exception: " + bex.getMessage())
				.build();
	}

}
