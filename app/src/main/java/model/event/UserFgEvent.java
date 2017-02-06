package model.event;

public class UserFgEvent {

	private String type;
	private String msg;



	public UserFgEvent(String type, String msg) {
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
