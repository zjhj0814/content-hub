package tibetyo.content_hub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tibetyo.content_hub.entity.Content;
import tibetyo.content_hub.entity.ContentCategory;

import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ContentResponseDto {
    private Long id;
    private String title;
    private ContentCategory category;

    public static ContentResponseDto of(Content content) {
        return new ContentResponseDto(
                content.getId(),
                content.getTitle(),
                content.getCategory()
        );
    }
}
