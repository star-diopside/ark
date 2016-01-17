package jp.gr.java_conf.star_diopside.spark.web.mvc.file.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
        return new ModelAndView("files/show").addObject(new FileShowForm(file));
    }

    @RequestMapping(value = "{id}/data", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) throws UnsupportedEncodingException {
        AttachedFile file = attachedFileManager.find(id).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment;filename*=utf-8''" + URLEncoder.encode(file.getName(), "UTF-8").replace("+", "%20"))
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(new InputStreamResource(file.newDataInputStream()));
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("files/create").addObject(new FileCreateForm());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(@Valid FileCreateForm form, Errors errors) throws IOException {
        if (errors.hasErrors()) {
            return "files/create";
        }

        try (InputStream input = form.getFile().getInputStream()) {
            AttachedFile attachedFile = attachedFileManager.create(input, form.getFile().getOriginalFilename(),
                    form.getFile().getContentType());
            return "redirect:/files/" + attachedFile.getId();
        }
    }
}
