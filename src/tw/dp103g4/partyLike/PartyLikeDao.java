package tw.dp103g4.partyLike;

import java.util.List;

public interface PartyLikeDao {
	public List<PartyLike> getAllByUser(int id);
	
	public int insert(PartyLike partylike);
	
	public int delete(int id, int partyId);

}
