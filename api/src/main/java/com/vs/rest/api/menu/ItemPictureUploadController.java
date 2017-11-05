package com.vs.rest.api.menu;

import com.vs.common.filters.AppConstants;
import com.vs.model.SaveFileModel;
import com.vs.model.enums.FileUploadTypeEnum;
import com.vs.rest.api.BaseController;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@Slf4j
//@RequestMapping("/rest/menu/{menuId}/item")
public class ItemPictureUploadController extends BaseController {

    //@PutMapping("/{itemId}/uploadItemPicture")
    public Response handleFileUpload(@RequestParam("file") MultipartFile file) {

        try {
        log.info(file.getOriginalFilename());
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return build204Response();


    }


}
