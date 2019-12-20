package tw.dp103g4.reviewImg;

import java.util.List;

public interface ReviewImgDao {
	public List<ReviewImg> getAllByParty(int partyId);
	
	public int insert(ReviewImg reviewImg, byte[] data);
	
	public int delete(int id);
	
	public byte[] getData(int id);

}
