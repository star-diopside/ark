package jp.gr.java_conf.star_diopside.spark.web.mvc.file.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.gr.java_conf.star_diopside.spark.data.entity.AttachedFile;
import jp.gr.java_conf.star_diopside.spark.service.logic.file.AttachedFileManager;
import jp.gr.java_conf.star_diopside.spark.web.exception.ResourceNotFoundException;
import jp.gr.java_conf.star_diopside.spark.web.mvc.file.form.FileCreateForm;
import jp.gr.java_conf.star_diopside.spark.web.mvc.file.form.FileShowForm;

@Controller
@RequestMapping("files")
public class FilesController {

    @Inject
    private AttachedFileManager attachedFileManager;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable("id") Long id) {
        AttachedFile file = attachedFileManager.find(id).orElseThrow(ResourceNotFoundException::new);
        ModelAndView mav = new ModelAndView("files/show");
        mav.addObject(new FileShowForm(file));
        return mav;
    }

    @RequestMapping(value = "{id}/data", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) throws UnsupportedEncodingException {
        AttachedFile file = attachedFileManager.find(id).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename*=utf-8''" + URLEncoder.encode(file.getName(), "UTF-8"))
                .body(new InputStreamResource(file.newDataInputStream()));
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create() {
        return "files/create";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(@Valid FileCreateForm form, Errors errors) throws IOException {
        if (errors.hasErrors()) {
            return "files/create";
        }

        AttachedFile attachedFile;
        Path tempFile = Files.createTempFile(null, null);

        try {
            try (InputStream input = form.getFile().getInputStream();
                    OutputStream output = Files.newOutputStream(tempFile)) {
                IOUtils.copyLarge(input, output);
            }
            attachedFile = attachedFileManager.create(tempFile, form.getFile().getOriginalFilename());
        } finally {
            Files.delete(tempFile);
        }

        return "redirect:/files/" + attachedFile.getAttachedFileId();
    }
}
