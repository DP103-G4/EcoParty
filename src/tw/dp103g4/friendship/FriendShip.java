package tw.dp103g4.friendship;



public class FriendShip {

	private int idOne;
	private int idTwo;
	private int friendId; 
	private boolean isInvite;
	private String account;
		
	

	public FriendShip(int idOne, int idTwo, int friendId, boolean isInvite, String account) {
		super();
		this.idOne = idOne;
		this.idTwo = idTwo;
		this.friendId = friendId;
		this.isInvite = isInvite;
		this.account = account;
	}
	

	public int getFriendId() {
		return friendId;
	}


	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public boolean getIsInvite() {
		return isInvite;
	}

	public void setIsInvite(boolean isInvite) {
		this.isInvite = isInvite;
	}

	public int getIdOne() {
		return idOne;
	}

	public void setIdOne(int idOne) {
		this.idOne = idOne;
	}

	public int getIdTwo() {
		return idTwo;
	}

	public void setIdTwo(int idTwo) {
		this.idTwo = idTwo;
	}

}
