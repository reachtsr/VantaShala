package com.vs.common.errorHandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class CustomReasonPhraseExceptionMapper implements ExceptionMapper<CustomReasonPhraseException> {

	public Response toResponse(CustomReasonPhraseException bex) {
		bex.printStackTrace();
		Map<String, String> map = new HashMap<>();
		map.put("message", bex.getMessage());
		return Response.status(new CustomReasonPhraseExceptionStatusType(bex.getBusinessCode()))
				.entity(map)
				.build();
//		return Response.status(new CustomReasonPhraseExceptionStatusType(Status.BAD_REQUEST))
//				.entity("Exception: " + bex.getMessage())
//				.build();
	}

}
