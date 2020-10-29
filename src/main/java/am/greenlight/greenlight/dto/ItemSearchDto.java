package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.enumForItem.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemSearchDto {
    private String outset;
    private String end;
    private Type type;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;

}
