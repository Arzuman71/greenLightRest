package am.greenlight.greenlight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutChangeDto {
    @NotBlank(message = "text is required")
    private String about;
}
