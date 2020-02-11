package tw.dp103g4.participant;

import java.util.List;

public interface ParticipantDao {	
	public List<Participant> getAllByParty(int partyId);
	
	public Participant findById(int id);
	
	public int insert(Participant participant);
	
	public int delete(Participant participant);
	
	public int setArrival(int id);
}
