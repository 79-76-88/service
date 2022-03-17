package ro.unibuc.link.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.data.UrlRepository;
import ro.unibuc.link.dto.IsAvailableDTO;
import ro.unibuc.link.dto.UrlShowDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @Mock
    private UrlRepository repository;
    @InjectMocks
    private UrlService service;

    @Test
    void testGetRedirectMapping() {
        when(repository.findById("internal"))
                .thenReturn(Optional.of(new UrlEntity("internal", "https://map.ro", null)));
        assertEquals("redirect:https://map.ro", service.getRedirectMapping("internal"));
    }

    @Test
    void testGetRedirectMappingThrowsOnEmpty() {
        when(repository.findById("internal"))
                .thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.getRedirectMapping("internal"));
    }

    @Test
    void testSetRedirectMapping() {
        UrlEntity urlEntity = new UrlEntity("internal", "", null);
        when(repository.findById(any())).thenReturn(Optional.empty());
        when(repository.save(urlEntity)).thenReturn(urlEntity);
        assertEquals(new UrlShowDTO(urlEntity), service.setRedirectMapping(urlEntity));
    }

    @Test
    void testSetRedirectMappingThrows() {
        UrlEntity urlEntity = new UrlEntity("internal", "", null);
        when(repository.findById(any())).thenReturn(Optional.of(urlEntity));
        assertThrows(ResponseStatusException.class, () -> service.setRedirectMapping(urlEntity));
    }

    @Test
    void testIsAvailable() {
        when(repository.findById("internal")).thenReturn(Optional.empty());
        assertEquals(new IsAvailableDTO(true), service.checkInternalUrlIsAvailable("internal"));
    }

    @Test
    void testIsNotAvailable() {
        UrlEntity urlEntity = new UrlEntity("internal", "", null);
        when(repository.findById("internal")).thenReturn(Optional.of(urlEntity));
        assertEquals(new IsAvailableDTO(false), service.checkInternalUrlIsAvailable("internal"));
    }

    @Test
    void testDeleteWhereInternalIsNull() {
        assertThrows(ResponseStatusException.class, () -> service.deleteRedirectMapping(null, null));
    }

    @Test
    void testDeleteWhereInternalDoesNotExist() {
        when(repository.findById("internal")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.deleteRedirectMapping("internal", ""));
    }

    @Test
    void testDeleteWhereDeleteWordsDoNotMatch() {
        UrlEntity urlEntity = new UrlEntity("internal", "link", "pass");
        when(repository.findById("internal")).thenReturn(Optional.of(urlEntity));
        assertThrows(ResponseStatusException.class, () -> service.deleteRedirectMapping("internal", ""));
    }

    @Test
    void testDeleteWorks() {
        UrlEntity urlEntity = new UrlEntity("internal", "link", "pass");
        when(repository.findById("internal")).thenReturn(Optional.of(urlEntity));
        assertEquals(new UrlShowDTO(urlEntity), service.deleteRedirectMapping("internal", "pass"));
    }


}
