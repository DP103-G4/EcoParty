package tw.dp103g4.pieceWarn;

import java.sql.Date;

public class PieceWarn {
	private int id;
	private int pieceId;
	private int userId;
	private Date time;
	private String content;
	private String account;

	public PieceWarn(int id, int pieceId, int userId, Date time, String content) {
		super();
		this.id = id;
		this.pieceId = pieceId;
		this.userId = userId;
		this.time = time;
		this.content = content;
	}
	
	public PieceWarn(int id, int pieceId, int userId, Date time, String content, String account) {
		super();
		this.id = id;
		this.pieceId = pieceId;
		this.userId = userId;
		this.time = time;
		this.content = content;
		this.account = account;
	}

	@Override
	public String toString() {
		return "PieceWarn [id=" + id + ", pieceId=" + pieceId + ", userId=" + userId + ", time=" + time + ", content="
				+ content + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPieceId() {
		return pieceId;
	}

	public void setPieceId(int pieceId) {
		this.pieceId = pieceId;
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
