package com.vs.service;

import com.vs.model.SaveFileModel;
import com.vs.model.enums.FileUploadTypeEnum;
import com.vs.model.props.ReadYML;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gopi on 11/6/16.
 */

@Slf4j
@Component
public class SaveFile {


    @Autowired
    ReadYML readYML;

    public String saveFile(String id, SaveFileModel saveFileModel) {

        String filePath="";

        try {
            FileUploadTypeEnum fileUploadTypeEnum = saveFileModel.getFileUploadTypeEnum();

            InputStream uploadedInputStream = saveFileModel.getInputStream();
            FormDataContentDisposition fileDisposition = saveFileModel.getContentDisposition();

            String fileName = fileDisposition.getFileName();
            log.info("Name of the file: {}", fileName);
             filePath = fileUploadTypeEnum.getCompletePath(readYML, id, fileName);

            File file = new File(filePath);

            FileUtils.copyInputStreamToFile(uploadedInputStream, file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;

    }
}
