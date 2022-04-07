package ro.unibuc.link.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollectionSetDTOTest {

    CollectionDeleteDTO collectionDeleteDTO = new CollectionDeleteDTO("collection1", "abcd");
    @Test
    void test_collectionName() {
        Assertions.assertSame("collection1", collectionDeleteDTO.getCollectionName());
    }

    @Test
    void test_privateWord() {
        Assertions.assertEquals("abcd", collectionDeleteDTO.getPrivateWord());
    }
}
