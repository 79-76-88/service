package ro.unibuc.link.dto;

import lombok.Data;
import ro.unibuc.link.data.CollectionEntity;
import ro.unibuc.link.data.UrlCollectionEntity;
import ro.unibuc.link.data.UrlEntity;

import java.util.List;

@Data
public class CollectionShowDTO {
    private String collectionName;
    private List<UrlCollectionEntity> urls;

    public CollectionShowDTO(String collectionName, List<UrlCollectionEntity> urls){
        this.collectionName = collectionName;
        this.urls = urls;
    }

    public CollectionShowDTO(CollectionEntity collectionEntity){
        this.collectionName = collectionEntity.getCollectionName();
        this.urls = collectionEntity.getUrls();
    }
}