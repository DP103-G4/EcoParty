package tw.dp103g4.talk;

import java.sql.Date;

public class Talk {
	private int id;
	private int receiverId;
	private int senderId;
	private int partyId;
	private String content;
	private Date time;
	private Boolean isRead;

	public Talk(int id, int receiverId, int senderId, int partyId, String content, Date time, Boolean isRead) {
		super();
		this.id = id;
		this.receiverId = receiverId;
		this.senderId = senderId;
		this.partyId = partyId;
		this.content = content;
		this.time = time;
		this.isRead = isRead;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getPartyId() {
		return partyId;
	}

	public void setPartyId(int partyId) {
		this.partyId = partyId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Boolean getRead() {
		return isRead;
	}

	public void setRead(Boolean read) {
		this.isRead = read;
	}

}
