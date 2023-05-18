package practice.javapractice.domain.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private int status;
    private boolean success;
    private String message;
    private Object data;

    public ResponseDto(int status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    public ResponseDto(int status, boolean success, String message, Object data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
