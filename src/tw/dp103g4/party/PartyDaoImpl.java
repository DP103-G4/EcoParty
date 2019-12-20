package tw.dp103g4.party;

import java.util.List;

public class PartyDaoImpl implements PartyDao{

	@Override
	public List<Party> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Party party, byte[] image) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Party party, byte[] coverImg, byte[] beforeImg, byte[] AfterImg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getCoverImg(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBeforeImg(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getAfterImg(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int setPartyImg(int id, byte[] beforeImg, byte[] AfterImg) {
		// TODO Auto-generated method stub
		return 0;
	}

}
