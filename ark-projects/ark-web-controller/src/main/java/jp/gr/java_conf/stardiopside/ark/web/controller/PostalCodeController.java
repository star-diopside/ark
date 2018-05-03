package jp.gr.java_conf.stardiopside.ark.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.gr.java_conf.stardiopside.ark.data.entity.PostalCode;
import jp.gr.java_conf.stardiopside.ark.service.PostalCodeService;
import jp.gr.java_conf.stardiopside.ark.web.util.PageWrapper;

@Controller
@RequestMapping("/postal-codes")
public class PostalCodeController {

    private final PostalCodeService postalCodeService;

    public PostalCodeController(PostalCodeService postalCodeService) {
        this.postalCodeService = postalCodeService;
    }

    @GetMapping
    public ModelAndView index(@PageableDefault(page = 0, size = 100) Pageable pageable) {
        Page<PostalCode> postalCodes = postalCodeService.search(pageable);
        return new ModelAndView("postal-codes/index").addObject("postalCodes", new PageWrapper<>(postalCodes));
    }
}
