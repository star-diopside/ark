package jp.gr.java_conf.stardiopside.ark.web.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.gr.java_conf.stardiopside.ark.data.entity.AttachedFile;
import jp.gr.java_conf.stardiopside.ark.service.AttachedFileService;
import jp.gr.java_conf.stardiopside.ark.web.exception.ResourceNotFoundException;
import jp.gr.java_conf.stardiopside.ark.web.form.FileCreateForm;
import jp.gr.java_conf.stardiopside.ark.web.util.HttpHeaderUtils;

@Controller
@RequestMapping("/files")
public class FilesController {

    @Inject
    private AttachedFileService attachedFileService;

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {
        AttachedFile file = attachedFileService.find(id).orElseThrow(ResourceNotFoundException::new);
        return new ModelAndView("files/show").addObject("file", file);
    }

    @GetMapping("/{id}/data")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        AttachedFile file = attachedFileService.find(id).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok().headers(HttpHeaderUtils.createContentDispositionHeader(file.getName()))
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(new InputStreamResource(file.newDataInputStream()));
    }

    @GetMapping("/create")
    public String create(FileCreateForm form) {
        return "files/create";
    }

    @PostMapping
    public String save(@Valid FileCreateForm form, BindingResult result, RedirectAttributes attr) throws IOException {
        if (result.hasErrors()) {
            return "files/create";
        }

        try (InputStream input = form.getFile().getInputStream()) {
            AttachedFile attachedFile = attachedFileService.create(input, form.getFile().getOriginalFilename(),
                    form.getFile().getContentType());
            attr.addAttribute("id", attachedFile.getId());
        }

        return "redirect:/files/{id}";
    }
}
