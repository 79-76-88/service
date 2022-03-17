package ro.unibuc.link.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.dto.UrlDeleteDTO;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LinkValidatorTest {
    @Spy
    LinkValidator validator;

    @Test
    void validateUrlWithNullInternal() {
        var url = new UrlEntity(null, null, null);
        assertThrows(ResponseStatusException.class, () -> validator.validate(url));
    }

    @Test
    void validateUrlWithNullExternal() {
        var url = new UrlEntity("abcs", null, null);
        assertThrows(ResponseStatusException.class, () -> validator.validate(url));
    }

    @Test
    void validateDeleteDTOWithNullInternal() {
        var url = new UrlDeleteDTO(null, null);
        assertThrows(ResponseStatusException.class, () -> validator.validate(url));
    }


}
