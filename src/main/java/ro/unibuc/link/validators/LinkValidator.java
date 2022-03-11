package ro.unibuc.link.validators;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.dto.UrlDeleteDTO;

@Component
public class LinkValidator {
    public void validate(UrlEntity entity) {
        if (entity.getInternalUrl() == null || entity.getInternalUrl().length() <= 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Internal url must be at least over 1 character");
        }
        if (entity.getExternalUrl() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "External url must exist");
        }
    }

    public void validate(UrlDeleteDTO urlDeleteDTO) {
        if (urlDeleteDTO.getInternalUrl() == null || urlDeleteDTO.getInternalUrl().length() <= 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Internal url must be at least over 1 character");
        }
    }
}
