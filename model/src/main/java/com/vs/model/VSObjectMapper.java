package com.vs.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@Primary
@Slf4j
public class VSObjectMapper extends ObjectMapper {

    public VSObjectMapper() {
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        getSerializationConfig().withView(ObjectIdView.class);
        SimpleModule module = new SimpleModule("ObjectIdModule");
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());
        this.registerModule(module);
    }

    @PostConstruct
    public void init(){
        log.info("Custom Object Mapper Loaded.");
    }
}

class ObjectIdSerializer extends JsonSerializer<ObjectId> {

    @Override
    public void serialize(ObjectId value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(value.toString());
    }
}