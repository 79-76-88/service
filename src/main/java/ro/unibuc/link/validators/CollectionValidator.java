package ro.unibuc.link.validators;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.dto.CollectionDeleteDTO;
import ro.unibuc.link.dto.CollectionSetDTO;
import ro.unibuc.link.dto.UrlCollectionDTO;

@Component
public class CollectionValidator {
    public void validate(CollectionSetDTO collectionSetDTO) {
        if (collectionSetDTO.getCollectionName() == null || collectionSetDTO.getCollectionName().length() >= 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Collection name must be at least over 3 characters");
        }
    }

    public void validate(CollectionDeleteDTO collectionDeleteDTO) {
        if (collectionDeleteDTO.getCollectionName() == null || collectionDeleteDTO.getCollectionName().length() >= 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Collection name must be at least over 3 characters");
        }
    }

    public void validate(UrlCollectionDTO urlCollectionDTO) {
        if (urlCollectionDTO.getUrlCollectionEntity() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There must be a collectionEntity");
        }

        if (urlCollectionDTO.getUrlCollectionEntity().getUrlName() == null
                || urlCollectionDTO.getUrlCollectionEntity().getUrlName().length() > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Url name must over 3 characters");
        }
        if (urlCollectionDTO.getUrlCollectionEntity().getExternalUrl() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "External url must exist");
        }


    }
}
