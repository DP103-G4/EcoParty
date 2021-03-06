package tw.dp103g4.partyMessage;

import java.util.List;

public interface PartyMessageDao {
	public List<PartyMsgInfo> getAllbyParty(int partyId);
	
	public PartyMessage getOneById(int partyMsgId);
	
	public int insert(PartyMessage partyMessage);
	
	public int delete(int id);
	
}
