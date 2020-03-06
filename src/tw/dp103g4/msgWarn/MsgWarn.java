package tw.dp103g4.msgWarn;

import java.sql.Date;

public class MsgWarn {
	private int id;
	private int messageId;
	private int userId;
	private Date time;
	private String content;
	private String account;
	
	
	public MsgWarn(int id,int messageId,int userId,Date time,String content) {
		super();
		this.id = id;
		this.messageId = messageId;
		this.userId = userId;
		this.time = time;
		this.content = content;
		
		}
	public MsgWarn(int id, int messageId, int userId, Date time, String content, String account) {
		super();
		this.id = id;
		this.messageId = messageId;
		this.userId = userId;
		this.time = time;
		this.content = content;
		this.account = account;
	}
	@Override
	public String toString() {
		String text = "ID"+id+"\n content"+ content;
		return text;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	
}
