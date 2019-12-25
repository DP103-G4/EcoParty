package tw.dp103g4.location;

import java.util.List;

public interface LocationDao {
	public List<Loaction> getAll();

	public Loaction getById(int id);

	public int insert(Loaction loaction);

	public int update(Loaction loaction);

	public int deleteById(int id);

}
