package tw.dp103g4.partyMessage;

import java.util.List;

public interface PartyMessageDao {
	public List<PartyMessage> getAllbyParty(int partyId);
	
	public int insert(PartyMessage partyMessage);
	
	public int update(PartyMessage partyMessage);
	
	public int delete(int id);
	
}
