package ro.unibuc.link.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.UrlCollectionEntity;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.dto.CollectionDeleteDTO;
import ro.unibuc.link.dto.CollectionSetDTO;
import ro.unibuc.link.dto.UrlCollectionDTO;
import ro.unibuc.link.dto.UrlDeleteDTO;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CollectionValidatorTest {
    @Spy
    CollectionValidator validator;

    @Test
    void validateCollectionSetDTOWithNullName() {
        var collection = new CollectionSetDTO();
        assertThrows(ResponseStatusException.class, () -> validator.validate(collection));
    }

    @Test
    void validateCollectionDeleteDTOWithNullName() {
        var collection = new CollectionDeleteDTO(null, null);
        assertThrows(ResponseStatusException.class, () -> validator.validate(collection));
    }

    @Test
    void validateUrlCollectionDTOWithNullEntity() {
        var collection = new UrlCollectionDTO(null, null);
        assertThrows(ResponseStatusException.class, () -> validator.validate(collection));
    }

    @Test
    void validateUrlCollectionDTOWithEntityWithNullUrl() {
        var collection = new UrlCollectionDTO(new UrlCollectionEntity(null, null), null);
        assertThrows(ResponseStatusException.class, () -> validator.validate(collection));
    }

    @Test
    void validateUrlCollectionDTOWithEntityWithNullExternalUrl() {
        var collection = new UrlCollectionDTO(new UrlCollectionEntity("abcd", null), null);
        assertThrows(ResponseStatusException.class, () -> validator.validate(collection));
    }


}
