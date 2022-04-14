package ro.unibuc.link.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.link.dto.*;
import ro.unibuc.link.services.CollectionService;
import ro.unibuc.link.validators.CollectionValidator;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

@Controller
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private CollectionValidator validator;
    @Autowired
    MeterRegistry metricsRegistry;

    @GetMapping("/check/{collectionName}")
    public @ResponseBody
    @Timed(value = "hello.greeting.time", description = "Time taken to check collection")
    @Counted(value = "hello.greeting.count", description = "Times check was returned")
    IsAvailableDTO checkIfCollectionIsAvailable(@PathVariable String collectionName) {
        return collectionService.checkCollectionNameIsAvailable(collectionName);
    }

    @PostMapping("/set")
    public @ResponseBody
    CollectionShowDTO setMapping(@RequestBody CollectionSetDTO collectionSetDTO) {
        validator.validate(collectionSetDTO);
        return collectionService.setCollection(collectionSetDTO);
    }

    @DeleteMapping("")
    public @ResponseBody
    CollectionShowDTO deleteMapping(@RequestBody CollectionDeleteDTO collectionDeleteDTO) {
        validator.validate(collectionDeleteDTO);
        return collectionService.deleteCollection(collectionDeleteDTO.getCollectionName(), collectionDeleteDTO.getPrivateWord());
    }

    @PostMapping("/add/{collectionName}")
    public @ResponseBody
    CollectionShowDTO addUrl(@PathVariable String collectionName, @RequestBody UrlCollectionDTO urlCollectionDTO) {
        validator.validate(urlCollectionDTO);
        return collectionService.addUrlToCollection(collectionName, urlCollectionDTO.getUrlCollectionEntity(), urlCollectionDTO.getPrivateWord());
    }

    @DeleteMapping("/remove/{collectionName}")
    public @ResponseBody
    CollectionShowDTO removeUrl(@PathVariable String collectionName, @RequestBody UrlCollectionDTO urlCollectionDTO) {
        validator.validate(urlCollectionDTO);
        return collectionService.removeUrlFromCollection(collectionName, urlCollectionDTO.getUrlCollectionEntity(), urlCollectionDTO.getPrivateWord());
    }


    @GetMapping("/redirect/{collectionName}/{url}")
    public String getRedirectMapping(@PathVariable String collectionName, @PathVariable String url) {
        return collectionService.getRedirectMapping(collectionName, url);
    }

}