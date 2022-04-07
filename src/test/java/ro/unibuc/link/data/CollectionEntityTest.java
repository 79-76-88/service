package ro.unibuc.link.data;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionEntityTest extends TestCase {
    CollectionEntity collectionEntity = new CollectionEntity("collection1", new ArrayList<>(),"abcd");
    @Test
    public void test_collectionName() {
        Assertions.assertSame("collection1", collectionEntity.getCollectionName());
    }

    @Test
    public void test_privateWord() {
        Assertions.assertEquals("abcd", collectionEntity.getPrivateWord()); }

    @Test
    public void test_urlEmptyList() {
        Assertions.assertEquals(0, collectionEntity.getUrls().size());
        Assertions.assertEquals(Collections.EMPTY_LIST, collectionEntity.getUrls());
    }

    @Test
    public void test_urlList() {
        collectionEntity.getUrls().add(new UrlCollectionEntity("url1", "https://www.youtube.com/"));
        List<UrlCollectionEntity> urls = new ArrayList<>(){{add(new UrlCollectionEntity("url1", "https://www.youtube.com/"));}};
        Assertions.assertEquals(1, collectionEntity.getUrls().size());
        Assertions.assertEquals(urls, collectionEntity.getUrls());
    }

}