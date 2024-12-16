package tibetyo.content_hub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "USER-001", "EMAIL DUPLICATED"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER-001", "INTERNAL SERVER ERROR"),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
