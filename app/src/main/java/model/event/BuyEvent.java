package model.event;

/**
 * Created by nico on 16/11/17.
 */
public class BuyEvent {
    private String type;
    private String msg;

    public BuyEvent(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
    public String getType(){
        return type;
    }
}
