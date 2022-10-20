package uz.mh.click.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import uz.mh.click.dtos.ApiErrorResponse;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ApiResponse<T> implements Serializable {

    private T body;

    private Integer status;

    private Integer total;

    private boolean success;

    private ApiErrorResponse error;

    public ApiResponse() {
    }

    public ApiResponse(Integer statusCode) {
        this.status = statusCode;
    }

    public ApiResponse(T body) {
        this(body,200,null);
    }

    public ApiResponse(T body, Integer status) {
        this.body = body;
        this.status = status;
    }

    public ApiResponse(T body, @NonNull final Integer status, Integer total) {
        this.body = body;
        this.status = status;
        this.total = total;
        this.success = true;
    }
}
