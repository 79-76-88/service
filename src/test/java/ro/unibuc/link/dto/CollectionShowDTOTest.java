package ro.unibuc.link.dto;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.unibuc.link.data.UrlCollectionEntity;

import java.util.ArrayList;

public class CollectionShowDTOTest extends TestCase {
    UrlCollectionEntity url1 = new UrlCollectionEntity("url1", "https://www.google.ro/?gws_rd=ssl");
    UrlCollectionEntity url2 = new UrlCollectionEntity("url2", "https://fmi.unibuc.ro/");
    ArrayList<UrlCollectionEntity> urlsList = new ArrayList<>(){{add(url1); add(url2);}};
    CollectionShowDTO  collectionShowDTO = new CollectionShowDTO("collection1", urlsList);

    ArrayList<UrlCollectionEntity> urlsEmptyList = new ArrayList<>();
    CollectionShowDTO  emptyCollectionShowDTO = new CollectionShowDTO("collection2", urlsEmptyList);

    @Test
    public void test_collectionName() {
        Assertions.assertSame("collection1", collectionShowDTO.getCollectionName());
    }

    @Test
    public void test_urlList() {
        Assertions.assertEquals(2, collectionShowDTO.getUrls().size());
    }

    @Test
    public void test_urlEmptyList() {
        Assertions.assertEquals(0, emptyCollectionShowDTO.getUrls().size());
    }


}