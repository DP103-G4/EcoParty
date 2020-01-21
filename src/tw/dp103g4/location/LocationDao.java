package tw.dp103g4.location;

import java.util.List;

public interface LocationDao {
	public List<Location> getAll(int partyId);

	public Location getById(int id);

	public int insert(Location location);

	public int update(Location location);

	public int deleteById(int id);

}
