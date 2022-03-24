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
        CollectionEntity collectionEntity = new CollectionEntity(collectionSetDTO);
        return new CollectionShowDTO(collectionRepository.save(collectionEntity));
    }

    public IsAvailableDTO checkCollectionNameIsAvailable(String name) {
        return new IsAvailableDTO(collectionRepository.findById(name).isEmpty());
    }

    public CollectionShowDTO deleteCollection(String collectionName, String privateWord) {
        if (collectionName == null || privateWord == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required Information Is Lacking");
        }
        var result = collectionRepository.findById(collectionName);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }
        if (!privateWord.equals(result.get().getPrivateWord())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
        }
        collectionRepository.delete(result.get());
        return new CollectionShowDTO(result.get());
    }

    public CollectionShowDTO addUrlToCollection(String collectionName, UrlCollectionEntity urlCollectionEntity, String privateWord) {
        var collectionEntity = collectionRepository.findById(collectionName);
        if (collectionEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }
        if (!privateWord.equals(collectionEntity.get().getPrivateWord())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
        }
        collectionEntity.get().getUrls().add(urlCollectionEntity);
        collectionRepository.save(collectionEntity.get());
        return new CollectionShowDTO(collectionEntity.get());
    }

    public CollectionShowDTO removeUrlFromCollection(String collectionName, UrlCollectionEntity urlCollectionEntity, String privateWord) {
        var collectionEntity = collectionRepository.findById(collectionName);
        if (collectionEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }

        if (!privateWord.equals(collectionEntity.get().getPrivateWord())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
        }
        collectionEntity.get().getUrls().remove(urlCollectionEntity);
        collectionRepository.save(collectionEntity.get());
        return new CollectionShowDTO(collectionEntity.get());
    }

    public String getRedirectMapping(String collectionName, String url) {
        var redirectedUrl = collectionRepository.findById(collectionName)
                .map(CollectionEntity::getUrls).flatMap(list -> list.stream().filter(collection -> collection.getUrlName().equals(url)).findFirst())
                .map(entity -> "redirect:" + entity.getExternalUrl());
        if (redirectedUrl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return redirectedUrl.get();
    }
}