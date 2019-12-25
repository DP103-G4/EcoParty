package tw.dp103g4.partyLike;

public class PartyLike {
	private int id;
	private int partyId;
	
	public PartyLike(int id, int partyId) {
		super();
		this.id = id;
		this.partyId = partyId;
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
	
	
}
