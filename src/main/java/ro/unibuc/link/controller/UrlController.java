package ro.unibuc.link.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.dto.IsAvailableDTO;
import ro.unibuc.link.dto.UrlDeleteDTO;
import ro.unibuc.link.dto.UrlShowDTO;
import ro.unibuc.link.services.UrlService;
import ro.unibuc.link.validators.LinkValidator;


@Controller
@RequestMapping("/short-link")
public class UrlController {
    @Autowired
    private UrlService urlService;
    @Autowired
    private LinkValidator validator;

    @GetMapping("/check/{url}")
    public @ResponseBody
    IsAvailableDTO checkIfUrlIsAvailable(@PathVariable String url) {
        return urlService.checkInternalUrlIsAvailable(url);
    }

    @PostMapping("/set")
    public @ResponseBody
    UrlShowDTO setMapping(@RequestBody UrlEntity urlEntity) {
        validator.validate(urlEntity);
        return urlService.setRedirectMapping(urlEntity);
    }

    @DeleteMapping("")
    public @ResponseBody
    UrlShowDTO deleteMapping(@RequestBody UrlDeleteDTO urlDeleteDTO) {
        validator.validate(urlDeleteDTO);
        return urlService.deleteRedirectMapping(urlDeleteDTO.getInternalUrl(), urlDeleteDTO.getDeleteWord());
    }

    @GetMapping("/redirect/{url}")
    public String redirect(@PathVariable String url) {
        return urlService.getRedirectMapping(url);
    }
}
