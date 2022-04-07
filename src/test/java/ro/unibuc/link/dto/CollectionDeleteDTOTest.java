package ro.unibuc.link.dto;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class CollectionDeleteDTOTest extends TestCase {
    CollectionDeleteDTO collectionDeleteDTO = new CollectionDeleteDTO("collection1", "abcd");
    @Test
    public void test_collectionName() {
        Assertions.assertSame("collection1", collectionDeleteDTO.getCollectionName());
    }

    @Test
    public void test_privateWord() {
        Assertions.assertEquals("abcd", collectionDeleteDTO.getPrivateWord());
    }
}