package tw.dp103g4.partyPiece;

import java.util.List;

public interface PartyPieceDao {
	public List<PartyPiece> getAllByParty(int partyId);
	
	public int insert(PartyPiece partyPiece);
	
	public int update(PartyPiece partyPiece);
	
	public int delete(int id);
	
	

}
