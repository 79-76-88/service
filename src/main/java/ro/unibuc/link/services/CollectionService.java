package ro.unibuc.link.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.CollectionEntity;
import ro.unibuc.link.data.CollectionRepository;
import ro.unibuc.link.data.UrlCollectionEntity;
import ro.unibuc.link.dto.CollectionSetDTO;
import ro.unibuc.link.dto.CollectionShowDTO;
import ro.unibuc.link.dto.IsAvailableDTO;

@Service
public class CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;

    public CollectionShowDTO setCollection(CollectionSetDTO collectionSetDTO) {
        if (collectionRepository.findById(collectionSetDTO.getCollectionName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Collection name already exists");
        }

        return new CollectionShowDTO(collectionRepository.save(new CollectionEntity(collectionSetDTO)));
    }

    public IsAvailableDTO checkCollectionNameIsAvailable(String name) {
        return new IsAvailableDTO(collectionRepository.findById(name).isEmpty());
    }

    public CollectionShowDTO deleteCollection(String collectionName, String privateWord) {
        if (collectionName == null || privateWord == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required Information Is Lacking");
        }
        var optionalName = collectionRepository.findById(collectionName);
        if (optionalName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }
        var result = optionalName.get();
        if (!privateWord.equals(result.getPrivateWord())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
        }
        collectionRepository.delete(result);
        return new CollectionShowDTO(result);
    }

    public CollectionShowDTO addUrlToCollection(String collectionName, UrlCollectionEntity urlCollectionEntity, String privateWord) {
        var optionalName = collectionRepository.findById(collectionName);
        if (optionalName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }
        var collection = optionalName.get();
        if (!privateWord.equals(collection.getPrivateWord())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
        }
        collection.getUrls().add(urlCollectionEntity);
        collectionRepository.save(collection);
        return new CollectionShowDTO(collection);
    }

    public CollectionShowDTO removeUrlFromCollection(String collectionName, UrlCollectionEntity urlCollectionEntity, String privateWord) {
        var optionalName = collectionRepository.findById(collectionName);
        if (optionalName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }
        var collection = optionalName.get();
        if (!privateWord.equals(collection.getPrivateWord())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
        }
        collection.getUrls().remove(urlCollectionEntity);
        collectionRepository.save(collection);
        return new CollectionShowDTO(collection);
    }

    public String getRedirectMapping(String collectionName, String url) {
        var redirectedUrl = collectionRepository.findById(collectionName)
                .map(CollectionEntity::getUrls).flatMap(list -> list.stream().filter(collection -> collection.getUrlName().equals(url)).findFirst())
                .map(entity -> "redirect:" + entity.getExternalUrl());
        return redirectedUrl.get();
    }
}