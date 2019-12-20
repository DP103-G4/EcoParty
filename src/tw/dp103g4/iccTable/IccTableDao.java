package tw.dp103g4.iccTable;

import java.util.List;

public interface IccTableDao {
	public List<IccTable> getAllByParty(int partyId);
	
	public int insert(IccTable iccTable);
	
	public int update(IccTable iccTable);

	public int delete(int userId, int partyId);
}
