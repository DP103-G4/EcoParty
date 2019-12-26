package tw.dp103g4.friendship;

public class FriendShip {

	private int idOne;
	private int idTwo;
	private boolean isInvite;

	public FriendShip(int idOne, int idTwo, boolean isInvite) {
		super();
		this.idOne = idOne;
		this.idTwo = idTwo;
		this.isInvite = isInvite;
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
