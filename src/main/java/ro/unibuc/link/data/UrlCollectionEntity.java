package ro.unibuc.link.data;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UrlCollectionEntity {

    private String urlName;
    private String externalUrl;


    public UrlCollectionEntity(String urlName, String externalUrl) {
        this.urlName = urlName;
        this.externalUrl = externalUrl;
    }
}