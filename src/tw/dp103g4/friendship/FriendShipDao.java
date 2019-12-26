package tw.dp103g4.friendship;

import java.util.List;

public interface FriendShipDao {
	int insert(int idOne, int idTwo);

	int delete(int idOne, int idTwo);
	
	int updateIsInvite();

	List<FriendShip> getAll();

}
