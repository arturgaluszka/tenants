package com.example.tenantsproject.flatmates.model.rest;

public class Response {
    public static final int MESSAGE_OK = 200;
    public static final int MESSAGE_UNAUTHORIZED = 401;
    public static final int MESSAGE_FORBIDDEN = 403;
    public static final int MESSAGE_NOT_FOUND = 404;
    public static final int MESSAGE_CONFLICT = 409;
    private int messageCode;
    private Object object;

    public Response() {
        messageCode = -1;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
