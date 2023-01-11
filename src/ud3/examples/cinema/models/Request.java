package ud3.examples.cinema.models;

import java.io.Serializable;

public class Request implements Serializable {
    private RequestType type;
    private Object object;
    private String message;

    public Request(RequestType type, Object object) {
        this.type = type;
        this.object = object;
    }

    public Request(RequestType type, Object object, String message) {
        this.type = type;
        this.object = object;
        this.message = message;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
