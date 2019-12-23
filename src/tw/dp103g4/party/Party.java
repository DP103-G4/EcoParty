package tw.dp103g4.party;

import java.sql.Date;

public class Party {
	private int id;
	private int ownerId;
	private String name;
	private Date startTime;
	private Date endTime;
	private Date postTime;
	private Date postEndTime;
	private String location;
	private String address;
	private double longitude;
	private double latitude;
	private String content;
	private int countUpperLimit;
	private int countLowerLimit;
	private int countCurrent;
	private int state;
	private double distance;
	
	public Party(int ownerId, String name, Date startTime, Date endTime, Date postEndTime, String location,
			String address, String content, int countUpperLimit, int countLowerLimit, int countCurrent, int state,
			double distance) {
		super();
		this.ownerId = ownerId;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.postEndTime = postEndTime;
		this.location = location;
		this.address = address;
		this.content = content;
		this.countUpperLimit = countUpperLimit;
		this.countLowerLimit = countLowerLimit;
		this.countCurrent = countCurrent;
		this.state = state;
		this.distance = distance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public Date getPostEndTime() {
		return postEndTime;
	}

	public void setPostEndTime(Date postEndTime) {
		this.postEndTime = postEndTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCountUpperLimit() {
		return countUpperLimit;
	}

	public void setCountUpperLimit(int countUpperLimit) {
		this.countUpperLimit = countUpperLimit;
	}

	public int getCountLowerLimit() {
		return countLowerLimit;
	}

	public void setCountLowerLimit(int countLowerLimit) {
		this.countLowerLimit = countLowerLimit;
	}

	public int getCountCurrent() {
		return countCurrent;
	}

	public void setCountCurrent(int countCurrent) {
		this.countCurrent = countCurrent;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	
}
