package am.greenlight.greenlight.dto;

import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImagesDto {

    private long id;
    private String name;

    @Override
    public String toString() {
        return "Images{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}