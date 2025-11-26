package com.apod.common;

import java.time.Instant;
import java.util.Objects;

public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;
    private String timestamp;

    public ApiResponse() {
        this.timestamp = Instant.now().toString();
    }

    public ApiResponse(int statusCode, String message, T data) {
        this();
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(int statusCode, String message, T data) {
        return new ApiResponse<>(statusCode, message, data);
    }

    public static <T> ApiResponse<T> error(int statusCode, String message) {
        return new ApiResponse<>(statusCode, message, null);
    }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiResponse<?> that = (ApiResponse<?>) o;
        return statusCode == that.statusCode &&
                Objects.equals(message, that.message) &&
                Objects.equals(data, that.data) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, message, data, timestamp);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
