package ro.unibuc.link.dto;

import lombok.Data;

@Data
public class CollectionSetDTO {
    private String collectionName;
    private String privateWord;

    public CollectionSetDTO(String collectionName, String privateWord){
        this.collectionName = collectionName;
        this.privateWord = privateWord;
    }
}