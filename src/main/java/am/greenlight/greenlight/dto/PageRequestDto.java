package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDto {
    private List<Integer> pageNumbers;
    private Page<Item> allAnnouncement;
}
