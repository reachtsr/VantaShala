package com.vs.utils;

import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by GeetaKrishna on 12/1/2015.
 */
@Component
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        Throwable badRequestException = getBadRequestException(exception);
        if (badRequestException != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(badRequestException.getMessage()).build();
        }
        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }
        return Response.serverError().entity(exception.getMessage()).build();
    }

    private Throwable getBadRequestException(
            Throwable exception) {
        if (exception instanceof ValidationException) {
            return exception;
        }
        Throwable cause = exception.getCause();
        if (cause != null && cause != exception) {
            Throwable result = getBadRequestException(cause);
            if (result != null) {
                return result;
            }
        }
        if (exception instanceof IllegalArgumentException) {
            return exception;
        }
        if (exception instanceof BadRequestException) {
            return exception;
        }
        return null;
    }
}
