package com.vs.common.errorHandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Component
@Provider
@Slf4j
public class CustomReasonPhraseExceptionMapper implements ExceptionMapper<CustomReasonPhraseException> {

    public Response toResponse(CustomReasonPhraseException bex) {
        log.info("Error: {} -- Code: {}", bex.getMessage(), bex.getBusinessCode());
        Map<String, String> map = new HashMap<>();
        map.put("message", bex.getMessage());
        return Response.status(new CustomReasonPhraseExceptionStatusType(bex.getBusinessCode()))
                .entity(map)
                .build();
    }

}
