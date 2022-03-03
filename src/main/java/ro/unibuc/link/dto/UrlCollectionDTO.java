package ro.unibuc.link.dto;

import lombok.Data;
import ro.unibuc.link.data.UrlCollectionEntity;

@Data
public class UrlCollectionDTO {
    private UrlCollectionEntity urlCollectionEntity;
    private String privateWord;

    public UrlCollectionDTO(UrlCollectionEntity urlCollectionEntity, String privateWord){
        this.privateWord = privateWord;
        this.urlCollectionEntity = urlCollectionEntity;
    }

}