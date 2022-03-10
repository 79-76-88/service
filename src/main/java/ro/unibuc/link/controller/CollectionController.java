package ro.unibuc.link.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.link.data.CollectionEntity;
import ro.unibuc.link.dto.*;
import ro.unibuc.link.services.CollectionService;

@Controller
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionService CollectionService;

    @GetMapping("/check/{collectionName}")
    public @ResponseBody
    IsAvailableDTO checkIfCollectionIsAvailable(@PathVariable String collectionName) {
        return CollectionService.checkCollectionNameIsAvailable(collectionName);
    }

    @PostMapping("/set")
    public @ResponseBody
    CollectionShowDTO setMapping(@RequestBody CollectionSetDTO collectionSetDTO) {
        return CollectionService.setCollection(collectionSetDTO);
    }

    @DeleteMapping("")
    public @ResponseBody
    CollectionShowDTO deleteMapping(@RequestBody CollectionDeleteDTO collectionDeleteDTO) {
        return CollectionService.deleteCollection(collectionDeleteDTO.getCollectionName(), collectionDeleteDTO.getPrivateWord());
    }

    @PostMapping("/add/{collectionName}")
    public @ResponseBody
    CollectionShowDTO addUrl(@PathVariable String collectionName, @RequestBody UrlCollectionDTO urlCollectionDTO) {
        return CollectionService.addUrlToCollection(collectionName, urlCollectionDTO.getUrlCollectionEntity(), urlCollectionDTO.getPrivateWord());
    }

    @DeleteMapping("/remove/{collectionName}")
    public @ResponseBody
    CollectionShowDTO removeUrl(@PathVariable String collectionName, @RequestBody UrlCollectionDTO urlCollectionDTO) {
        return CollectionService.removeUrlFromCollection(collectionName, urlCollectionDTO.getUrlCollectionEntity(), urlCollectionDTO.getPrivateWord());
    }

    @GetMapping("/redirect/{collectionName}/{url}")
    public String redirect(@PathVariable String collectionName, @PathVariable String url) {
        return CollectionService.getRedirectMapping(collectionName, url);
    }
}