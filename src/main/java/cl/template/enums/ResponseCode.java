package cl.template.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(HttpStatus.CREATED, "00", "Usuario creado correctamente"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "01", "El usuario ya existe"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "99", "Error interno del servidor");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
