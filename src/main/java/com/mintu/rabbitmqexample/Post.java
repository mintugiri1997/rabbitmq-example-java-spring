package com.mintu.rabbitmqexample;

public class Post {

    private Request request;
    private String status;
    private String message;

    @Override
    public String toString() {
        return "Post{" +
                "request=" + request +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
