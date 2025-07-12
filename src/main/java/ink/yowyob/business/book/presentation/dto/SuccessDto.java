package ink.yowyob.business.book.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessDto<T> {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private T data;
    
    // Factory method for quick creation with just status and message
    public static <T> SuccessDto<T> of(int status, String message) {
        return SuccessDto.<T>builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> SuccessDto<T> of(HttpStatus status, String message) {
        return SuccessDto.<T>builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    // Factory method with path information
    public static <T> SuccessDto<T> of(int status, String message, String path) {
        return SuccessDto.<T>builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    public static <T> SuccessDto<T> of(HttpStatus status, String message, String path) {
        return SuccessDto.<T>builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }
    
    // Factory method with response data
    public static <T> SuccessDto<T> of(int status, String message, T data) {
        return SuccessDto.<T>builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }

    public static <T> SuccessDto<T> of(HttpStatus status, String message, T data) {
        return SuccessDto.<T>builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }
    
    // Factory method with both path and data
    public static <T> SuccessDto<T> of(int status, String message, String path, T data) {
        return SuccessDto.<T>builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .data(data)
                .build();
    }

    public static <T> SuccessDto<T> of(HttpStatus status, String message, String path, T data) {
        return SuccessDto.<T>builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .data(data)
                .build();
    }
}
