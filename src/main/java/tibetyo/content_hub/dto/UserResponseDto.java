package tibetyo.content_hub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tibetyo.content_hub.entity.User;


@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String email;
    private String password;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getEmail(), user.getPassword());
    }
}
