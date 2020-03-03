package tw.dp103g4.location;

import java.util.List;

public interface InfoLocationDao {
	public List<InfoLocation> getAll(int partyId);

	public InfoLocation getById(int id);

	public int insert(InfoLocation infoLocation);

	public int update(InfoLocation infoLocation);

	public int deleteById(int id);

}
