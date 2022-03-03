package ro.unibuc.link.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import ro.unibuc.link.dto.UrlShowDTO;

import java.util.List;

@Data
public class CollectionEntity {
    @Id
    private String collectionName;
    private List<UrlCollectionEntity> urls;
    private String privateWord;

    public CollectionEntity(String collectionName, List<UrlCollectionEntity> urls, String privateWord) {
        this.collectionName = collectionName;
        this.urls = urls;
        this.privateWord = privateWord;
    }
}