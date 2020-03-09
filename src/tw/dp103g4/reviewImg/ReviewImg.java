package tw.dp103g4.reviewImg;

import java.util.Date;

public class ReviewImg {
	private int id;
	private int partyId;
	private Date time;
	
	public ReviewImg(int id, int partyId, Date time) {
		super();
		this.id = id;
		this.partyId = partyId;
		this.time = time;
	}
	
	public ReviewImg(int id) {
		super();
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPartyId() {
		return partyId;
	}
	public void setPartyId(int partyId) {
		this.partyId = partyId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
