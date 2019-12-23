package tw.dp103g4.party;

import java.util.List;

public interface PartyDao {
	public List<Party> getAll();
	
	public Party findById(int id);
		
	public int insert(Party party, byte[] coverImg);

	public int update(Party party, byte[] coverImg);
	
	public int delete(int id);
	
	public int setImg(int id, byte[] beforeImg, byte[] afterImg);
	
	public byte[] getCoverImg(int id);
	
	public byte[] getBeforeImg(int id);
	
	public byte[] getAfterImg(int id);
	
}

