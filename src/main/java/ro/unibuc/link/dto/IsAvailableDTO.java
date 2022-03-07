package ro.unibuc.link.dto;

import lombok.Data;

@Data
public class IsAvailableDTO {
    private boolean isAvailable;

    public IsAvailableDTO(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
