package com.vs.model;

import com.vs.model.enums.FileUploadTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;

/**
 * Created by gopi on 11/6/16.
 */

@Data
@Slf4j
public class SaveFileModel {
    InputStream inputStream;
    FormDataContentDisposition contentDisposition;
    FileUploadTypeEnum fileUploadTypeEnum;

    public void validate() throws Exception {
        if (inputStream != null && contentDisposition != null && fileUploadTypeEnum != null) return;

        throw new Exception("Missing Fields");
    }
}
