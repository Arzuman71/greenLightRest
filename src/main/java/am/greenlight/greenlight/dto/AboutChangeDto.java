package am.greenlight.greenlight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutChangeDto {
    @NotNull(message = "text is required")
    private String about;
}
