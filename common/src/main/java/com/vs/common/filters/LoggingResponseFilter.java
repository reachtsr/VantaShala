package com.vs.common.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Slf4j
public class LoggingResponseFilter
		implements ContainerResponseFilter {

	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		String method = requestContext.getMethod();

		log.info("Requesting: {} for path: {}" ,method, requestContext.getUriInfo().getPath());
		Object entity = responseContext.getEntity();
		if (entity != null) {
			//log.info("Response: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(entity));
		}
		
	}

}