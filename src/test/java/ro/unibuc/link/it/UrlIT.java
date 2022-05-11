package ro.unibuc.link.it;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.CollectionEntity;
import ro.unibuc.link.data.UrlCollectionEntity;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.data.UrlRepository;
import ro.unibuc.link.services.UrlService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Tag("IT")
public class UrlIT {
    String internalUrl = "inURL";
    String externalUrl = "exURL";

    @Autowired
    UrlService urlService;
    @Autowired
    UrlRepository repository;

    @Test
    void testCheckIfNotAvailable() {
        repository.save(new UrlEntity(internalUrl, externalUrl, null));
        Assertions.assertFalse(urlService.checkInternalUrlIsAvailable(internalUrl).isAvailable());
    }

    @Test
    void testCheckIfAvailable() {
        repository.deleteById(internalUrl);
        Assertions.assertTrue(urlService.checkInternalUrlIsAvailable(internalUrl).isAvailable());
    }

    @Test
    void testRedirect() {
        repository.save(new UrlEntity(internalUrl, externalUrl, null));
        Assertions.assertEquals("redirect:" + externalUrl, urlService.getRedirectMapping(internalUrl));
    }

    @Test
    void testRedirectFails() {
        repository.deleteById(internalUrl);
        Assertions.assertThrows(ResponseStatusException.class, () -> urlService.getRedirectMapping(internalUrl));
    }

    @Test
    void testDelete() {
        repository.save(new UrlEntity(internalUrl, externalUrl, "pass"));
        urlService.deleteRedirectMapping(internalUrl, "pass");
        Assertions.assertTrue(repository.findById(internalUrl).isEmpty());
    }


}