package example.com.qiyicom;

/**
 * Created by nico on 16/11/10.
 */
public class MessageEvent {
    private String type;
    private String msg;

    public MessageEvent(String type, String msg) {
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
