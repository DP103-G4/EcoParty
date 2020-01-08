package tw.dp103g4.party;

import java.util.List;

public interface PartyDao {

	public Party findById(int id);
	
	public int insert(Party party, byte[] coverImg);

	public int update(Party party, byte[] coverImg);
	
	public List<Party> getCurrentParty(int participantId, int state);
	
	public List<Party> getPartyList(int state);
	
	public List<Party> getPieceList (int state);
		
	public int setImg(int id, byte[] beforeImg, byte[] afterImg);
	
	public byte[] getCoverImg(int id);
	
	public byte[] getBeforeImg(int id);
	
	public byte[] getAfterImg(int id);
	
}

