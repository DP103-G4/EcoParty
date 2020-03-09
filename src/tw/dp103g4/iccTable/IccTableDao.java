package tw.dp103g4.iccTable;

import java.util.List;

public interface IccTableDao {
	public List<IccTableInfo> getAllByParty(int partyId);
	
	public IccTableInfo findById(int userId, int partyId);
	
	public int insert(int userId, int partyId);
	
	public int update(IccTable iccTable);

	public int delete(int userId, int partyId);
}
