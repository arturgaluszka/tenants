/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flatmatesrest;

/**
 *
 * @author Karol
 */
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
