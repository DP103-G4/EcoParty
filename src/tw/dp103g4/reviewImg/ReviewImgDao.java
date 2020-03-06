package tw.dp103g4.reviewImg;

import java.util.List;

public interface ReviewImgDao {
	public List<ReviewImg> getAllByParty(int partyId);
	
	public int insert(int partyId, byte[] data);
	
	public int delete(int id);
	
	public byte[] getData(int id);

}
