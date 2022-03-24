package ro.unibuc.link.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IsAvailableDTO {
    @JsonProperty("available")
    private boolean isAvailable;

    public IsAvailableDTO(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
