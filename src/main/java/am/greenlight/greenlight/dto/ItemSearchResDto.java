package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemSearchResDto {
    private String picture;
    private String name;
    private String surname;
    private String outset;
    private String end;
    private Rating Rating;
}
