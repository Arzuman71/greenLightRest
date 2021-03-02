package am.greenlight.greenlight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemSearchResDto {
    private long id;
    private String picture;
    private String name;
    private String surname;
    private String outset;
    private String end;
    private double rating;
}
