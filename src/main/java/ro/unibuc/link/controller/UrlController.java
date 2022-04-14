package ro.unibuc.link.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
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
    @Timed(value = "link.url.available.time", description = "Time taken to check urls")
    @Counted(value = "link.url.available.count", description = "Times check was returned")
    public @ResponseBody
    IsAvailableDTO checkIfUrlIsAvailable(@PathVariable String url) {
        return urlService.checkInternalUrlIsAvailable(url);
    }

    @Timed(value = "link.url.set.time", description = "Time taken to set urls")
    @Counted(value = "link.url.set.count", description = "Times url was set")
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
    @Timed(value = "link.url.redirect.time", description = "Time taken to redirect urls")
    @Counted(value = "link.url.redirect.count", description = "Times redirect was called")
    public String redirect(@PathVariable String url) {
        return urlService.getRedirectMapping(url);
    }
}
