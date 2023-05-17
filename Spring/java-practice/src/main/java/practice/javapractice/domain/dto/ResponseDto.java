package practice.javapractice.domain.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDto {
    private HttpStatus status;
    private boolean success;
    private String message;
    private Object data;

    public ResponseDto(HttpStatus status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    public ResponseDto(HttpStatus status, boolean success, String message, Object data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
