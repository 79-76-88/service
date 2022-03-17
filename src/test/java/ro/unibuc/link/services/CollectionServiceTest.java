package ro.unibuc.link.services;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.CollectionEntity;
import ro.unibuc.link.data.CollectionRepository;
import ro.unibuc.link.data.UrlCollectionEntity;
import ro.unibuc.link.dto.CollectionSetDTO;
import ro.unibuc.link.dto.CollectionShowDTO;
import ro.unibuc.link.dto.IsAvailableDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.main.lazy-initialization=true",
        classes = {CollectionService.class})
public class CollectionServiceTest extends TestCase {
    @Mock
    CollectionRepository mockCollectionRepository = mock(CollectionRepository.class);

    @InjectMocks
    CollectionService collectionService = new CollectionService();

    @Test
    void setCollection() {
        CollectionSetDTO collectionSetDTO = new CollectionSetDTO("mycollection", "1234");
        when(mockCollectionRepository.findByName(collectionSetDTO.getCollectionName())).thenReturn(null);
        CollectionEntity collectionEntity = new CollectionEntity(collectionSetDTO);
        when(mockCollectionRepository.save(collectionEntity)).thenReturn(collectionEntity);
        CollectionShowDTO collectionShowDTO = collectionService.setCollection(collectionSetDTO);

        Assertions.assertEquals("mycollection", collectionShowDTO.getCollectionName());
        Assertions.assertEquals(0, collectionShowDTO.getUrls().size());
    }

    @Test
    void setCollection_whenNameExists() {
        CollectionSetDTO collectionSetDTO = new CollectionSetDTO("mycollection", "1234");
        when(mockCollectionRepository.findByName(collectionSetDTO.getCollectionName())).thenReturn(new CollectionEntity((collectionSetDTO)));
        try {
            // Act
            CollectionShowDTO collectionShowDTO = collectionService.setCollection(collectionSetDTO);
        } catch (Exception ex) {
            // Assert
            Assertions.assertEquals(new ResponseStatusException(HttpStatus.FORBIDDEN, "Collection name already exists").getMessage(), ex.getMessage());
        }
    }

    @Test
    void checkCollectionNameIsAvailable() {
        String name = "collection1";
        String privateWord = "123";
        CollectionEntity collectionEntity = new CollectionEntity(name, Collections.emptyList(), privateWord);
        when(mockCollectionRepository.findByName(name)).thenReturn(collectionEntity);

        IsAvailableDTO isAvailableDTO = collectionService.checkCollectionNameIsAvailable(name);

        Assertions.assertTrue(isAvailableDTO.isAvailable());
    }

    @Test
    void checkCollectionNameIsNotAvailable() {
        String name = "collection1";
        String privateWord = "123";
        CollectionEntity collectionEntity = new CollectionEntity(name, Collections.emptyList(), privateWord);
        when(mockCollectionRepository.findByName(name)).thenReturn(null);

        IsAvailableDTO isAvailableDTO = collectionService.checkCollectionNameIsAvailable(name);

        Assertions.assertFalse(isAvailableDTO.isAvailable());
    }

    @Test
    void deleteCollection() {
        String collectionName = "collection1";
        String privateWord = "abc";

        CollectionEntity collectionEntity = new CollectionEntity(collectionName,privateWord);
        when(mockCollectionRepository.findByName(collectionName)).thenReturn(collectionEntity);

        CollectionShowDTO collectionShowDTO = collectionService.deleteCollection(collectionName, privateWord);

        Assertions.assertEquals("collection1", collectionShowDTO.getCollectionName());
        Assertions.assertEquals(Collections.EMPTY_LIST, collectionShowDTO.getUrls());
    }

    @Test
    void deleteCollection_PrivateWordIsWrong() {
        String collectionName = "collection1";
        String privateWord = "abc";
        String wrongPrivateWord = "aaa";

        CollectionEntity collectionEntity = new CollectionEntity(collectionName,privateWord);
        when(mockCollectionRepository.findByName(collectionName)).thenReturn(collectionEntity);

        try{
            CollectionShowDTO collectionShowDTO = collectionService.deleteCollection(collectionName, wrongPrivateWord);
        }
        catch (Exception ex){
            ResponseStatusException responseStatusException =new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
            Assertions.assertEquals(responseStatusException.getMessage(), ex.getMessage());
        }

    }

    @Test
    void deleteCollection_PrivateWordIsNull() {
        String collectionName = "collection1";
        String privateWord = "abc";

        CollectionEntity collectionEntity = new CollectionEntity(collectionName,privateWord);
        when(mockCollectionRepository.findByName(collectionName)).thenReturn(collectionEntity);

        try{
            CollectionShowDTO collectionShowDTO = collectionService.deleteCollection(collectionName, null);
        }
        catch (Exception ex){
            ResponseStatusException responseStatusException =new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required Information Is Lacking");
            Assertions.assertEquals(responseStatusException.getMessage(), ex.getMessage());
        }

    }

    @Test
    void deleteCollection_CollectionNameIsNull() {
        String collectionName = "collection1";
        String privateWord = "abc";

        CollectionEntity collectionEntity = new CollectionEntity(collectionName,privateWord);
        when(mockCollectionRepository.findByName(collectionName)).thenReturn(collectionEntity);

        try{
            CollectionShowDTO collectionShowDTO = collectionService.deleteCollection(null, privateWord);
        }
        catch (Exception ex){
            ResponseStatusException responseStatusException =new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required Information Is Lacking");
            Assertions.assertEquals(responseStatusException.getMessage(), ex.getMessage());
        }

    }

    @Test
    void deleteCollection_CollectionNotFound() {
        String collectionName = "collection1";
        String privateWord = "abc";

        CollectionEntity collectionEntity = new CollectionEntity(collectionName,privateWord);
        when(mockCollectionRepository.findByName(collectionName)).thenReturn(null);

        try{
            CollectionShowDTO collectionShowDTO = collectionService.deleteCollection(collectionName, privateWord);
        }
        catch (Exception ex){
            ResponseStatusException responseStatusException =new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
            Assertions.assertEquals(responseStatusException.getMessage(), ex.getMessage());
        }

    }

    @Test
    void addUrlToCollection() {
        String collectionName = "collection1";
        String privateWord = "abc";
        UrlCollectionEntity urlCollectionEntity = new UrlCollectionEntity("url1", "https://fmi.unibuc.ro/");
        CollectionEntity collectionEntity = new CollectionEntity(collectionName,privateWord);
        List<UrlCollectionEntity> urls = new ArrayList<>(){{add(urlCollectionEntity);}};

        when(mockCollectionRepository.findByName(collectionName)).thenReturn(collectionEntity);

        CollectionShowDTO collectionShowDTO = collectionService.addUrlToCollection(collectionName,urlCollectionEntity,privateWord);
        Assertions.assertEquals(collectionName, collectionShowDTO.getCollectionName());
        Assertions.assertEquals(urls, collectionShowDTO.getUrls());
    }

    @Test
    void addUrlToCollection_WrongPrivateWord() {
        String collectionName = "collection1";
        String privateWord = "abc";
        String wrongPrivateWord = "abb";
        UrlCollectionEntity urlCollectionEntity = new UrlCollectionEntity("url1", "https://fmi.unibuc.ro/");
        CollectionEntity collectionEntity = new CollectionEntity(collectionName,privateWord);


        when(mockCollectionRepository.findByName(collectionName)).thenReturn(collectionEntity);
        try{
            CollectionShowDTO collectionShowDTO = collectionService.addUrlToCollection(collectionName,urlCollectionEntity,wrongPrivateWord);
        } catch (Exception ex){
            ResponseStatusException response = new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
            Assertions.assertEquals(response.getMessage(), ex.getMessage());
        }

    }

    @Test
    void addUrlToCollection_CollectionNotFound() {
        String collectionName = "collection1";
        String privateWord = "abc";
        UrlCollectionEntity urlCollectionEntity = new UrlCollectionEntity("url1", "https://fmi.unibuc.ro/");

        when(mockCollectionRepository.findByName(collectionName)).thenReturn(null);
        try{
            CollectionShowDTO collectionShowDTO = collectionService.addUrlToCollection(collectionName,urlCollectionEntity,privateWord);
        } catch (Exception ex){
            ResponseStatusException response = new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
            Assertions.assertEquals(response.getMessage(), ex.getMessage());
        }

    }

    @Test
    void removeUrlFromCollection() {

        String collectionName = "collection1";
        String privateWord = "abc";
        UrlCollectionEntity urlCollectionEntity = new UrlCollectionEntity("url1", "https://fmi.unibuc.ro/");
        CollectionEntity collectionEntity = new CollectionEntity(collectionName,privateWord);
        List<UrlCollectionEntity> urls = new ArrayList<>();

        when(mockCollectionRepository.findByName(collectionName)).thenReturn(collectionEntity);

        CollectionShowDTO collectionShowDTO = collectionService.removeUrlFromCollection(collectionName,urlCollectionEntity,privateWord);
        Assertions.assertEquals(collectionName, collectionShowDTO.getCollectionName());
        Assertions.assertEquals(urls, collectionShowDTO.getUrls());
    }

    @Test
    void removeUrlFromCollection_ListNotEmpty() {

        String collectionName = "collection1";
        String privateWord = "abc";
        UrlCollectionEntity urlCollectionEntity = new UrlCollectionEntity("url1", "https://fmi.unibuc.ro/");
        UrlCollectionEntity urlCollectionEntity2 = new UrlCollectionEntity("url2", "https://fmi.unibuc.ro/");
        List<UrlCollectionEntity> urls = new ArrayList<>(){{
            add(urlCollectionEntity);
            add(urlCollectionEntity2);
        }};
        CollectionEntity collectionEntity = new CollectionEntity(collectionName, urls, privateWord);
        urls.remove(urlCollectionEntity);

        when(mockCollectionRepository.findByName(collectionName)).thenReturn(collectionEntity);

        CollectionShowDTO collectionShowDTO = collectionService.removeUrlFromCollection(collectionName,urlCollectionEntity,privateWord);
        Assertions.assertEquals(collectionName, collectionShowDTO.getCollectionName());
        Assertions.assertEquals(urls, collectionShowDTO.getUrls());
    }

    @Test
    void removeUrlFromCollection_CollectionNotFound() {
        String collectionName = "collection1";
        String privateWord = "abc";
        UrlCollectionEntity urlCollectionEntity = new UrlCollectionEntity("url1", "https://fmi.unibuc.ro/");

        when(mockCollectionRepository.findByName(collectionName)).thenReturn(null);
        try{
            CollectionShowDTO collectionShowDTO = collectionService.removeUrlFromCollection(collectionName,urlCollectionEntity,privateWord);
        } catch (Exception ex){
            ResponseStatusException response = new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
            Assertions.assertEquals(response.getMessage(), ex.getMessage());
        }

    }

    @Test
    void RemoveUrlFromCollection_WrongPrivateWord() {
        String collectionName = "collection1";
        String privateWord = "abc";
        String wrongPrivateWord = "abb";
        UrlCollectionEntity urlCollectionEntity = new UrlCollectionEntity("url1", "https://fmi.unibuc.ro/");
        CollectionEntity collectionEntity = new CollectionEntity(collectionName,privateWord);


        when(mockCollectionRepository.findByName(collectionName)).thenReturn(collectionEntity);
        try{
            CollectionShowDTO collectionShowDTO = collectionService.removeUrlFromCollection(collectionName,urlCollectionEntity,wrongPrivateWord);
        } catch (Exception ex){
            ResponseStatusException response = new ResponseStatusException(HttpStatus.FORBIDDEN, "Private word doesn't match requested collection");
            Assertions.assertEquals(response.getMessage(), ex.getMessage());
        }

    }

    @Test
    void getRedirectMapping() {
    }
}