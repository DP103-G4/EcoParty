package tw.dp103g4.party;

import java.util.List;

public interface PartyDao {
	public int setState(int id, int state);

	public PartyInfo findById(int partyId, int userId);
	
	public int insert(Party party, byte[] coverImg);

	public int update(Party party, byte[] coverImg);
	
	public int setCountCurrent(int partyId, int in);
	
	public List<Party> getCurrentParty(int participantId, int state);
	
	public List<Party> getMyParty(int userId);
	
	public List<Party> getPartyList(int state);
	
	public List<Party> getPieceList (int state);
	//審核
	public List<Party> getPartyCheck ();
		
	public int setImg(int id, byte[] beforeImg, byte[] afterImg);
	
	public byte[] getCoverImg(int id);
	
	public byte[] getBeforeImg(int id);
	
	public byte[] getAfterImg(int id);
	
}

