package com.vs.rest.api.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by gopi on 11/6/2016.
 */
@Slf4j
public class FileFolderCreateTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void saveFile() throws Exception {
        String uploadPath="../../fileUpload/"+"testFile.txt";
        Path currentRelativePath = Paths.get(uploadPath);
        String currentPath = currentRelativePath.toAbsolutePath().toString();
        File f = new File(currentPath);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        log.info("{}",currentPath);
    }

}