package tw.dp103g4.party;

import java.util.List;

public interface PartyDao {
	public List<Party> getAll();
		
	public int insert(Party party, byte[] coverImg);

	public int update(Party party, byte[] coverImg, byte[] beforeImg, byte[] AfterImg);
	
	public int delete(int id);
	
	public byte[] getCoverImg(int id);
	
	public byte[] getBeforeImg(int id);
	
	public byte[] getAfterImg(int id);
	
	public int setPartyImg(int id, byte[] beforeImg, byte[] AfterImg);
	
}

