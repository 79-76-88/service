package ro.unibuc.link.dto;

import lombok.Data;
import ro.unibuc.link.data.CollectionEntity;
import ro.unibuc.link.data.UrlCollectionEntity;
import ro.unibuc.link.data.UrlEntity;

import java.util.List;

@Data
public class WrapperDTO {
    private UrlCollectionEntity urlCollectionEntity;
    private String privateWord;

    public WrapperDTO(UrlCollectionEntity urlCollectionEntity, String privateWord){
        this.privateWord = privateWord;
        this.urlCollectionEntity = urlCollectionEntity;
    }

}