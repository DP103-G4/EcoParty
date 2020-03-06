package tw.dp103g4.participant;

import java.util.List;

public interface ParticipantDao {	
	public List<ParticipantInfo> getAllByParty(int partyId);
	
	public Participant findById(int id);
	
	public int insert(Participant participant);
	
	public int delete(Participant participant);
	
	public int isIn(int userId, int partyId);
	
	public int setArrival(int userId, int partyId, boolean isArrival);

	public int setStaff(int userId, int partyId, boolean isStaff);
}
