package tw.dp103g4.partyPiece;

import java.util.List;

public interface PartyPieceDao {
	public List<PieceInfo> getAllByParty(int partyId);
	
	public PartyPiece getOneById(int pieceMsgId);
	
	public int insert(PartyPiece partyPiece);
	
	public int update(PartyPiece partyPiece);
	
	public int deleteOne(int id);
	
	public int delete(int id);
	
	

}
