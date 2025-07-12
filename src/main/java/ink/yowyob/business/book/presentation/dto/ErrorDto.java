package ink.yowyob.business.book.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private Map<String, Object> details;

    public static ErrorDto of(int status, String message) {
        return ErrorDto.builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorDto of(HttpStatus status, String message) {
        return ErrorDto.builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorDto of(int status, String message, String path) {
        return ErrorDto.builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    public static ErrorDto of(HttpStatus status, String message, String path) {
        return ErrorDto.builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    public static ErrorDto of(int status, String message, String path, Map<String, Object> details) {
        return ErrorDto.builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .details(details)
                .build();
    }

    public static ErrorDto of(HttpStatus status, String message, String path, Map<String, Object> details) {
        return ErrorDto.builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .details(details)
                .build();
    }
}