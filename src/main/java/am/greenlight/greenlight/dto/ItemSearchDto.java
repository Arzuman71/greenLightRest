package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.enumForItem.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemSearchDto {
    @NotNull
    private String outset;
    @NotNull
    private String end;
    @NotNull
    private Type type;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    private LocalDateTime dateTo;

}
