package tw.dp103g4.partyImg;

import java.util.List;

public interface PartyImgDao {
	public List<PartyImg> getAllByParty(int partyId);
	
	public int insert(PartyImg pieceImg, byte[] data);
	
	public int delete(int id);
	
	public byte[] getImage(int id);
	
}
