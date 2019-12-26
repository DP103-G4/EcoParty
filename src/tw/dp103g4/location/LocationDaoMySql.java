package tw.dp103g4.location;

import java.util.List;
import static tw.dp103g4.main.Common.*;


public class LocationDaoMySql implements LocationDao{

	public LocationDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Loaction> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Loaction getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Loaction loaction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Loaction loaction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
