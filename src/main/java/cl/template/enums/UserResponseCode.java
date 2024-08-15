package cl.template.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.util.Locale;

@Getter
@AllArgsConstructor
public enum UserResponseCode {

    USER_CREATED(HttpStatus.CREATED, "00", "user.created"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "01", "user.already_exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "99", "internal_server_error"),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    @Setter
    private static MessageSource messageSource;

    public String getMessage() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(this.message, null, locale);
    }

}
