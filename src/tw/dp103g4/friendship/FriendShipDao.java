package tw.dp103g4.friendship;

import java.util.List;

public interface FriendShipDao {
	int insert(int idOne, int idTwo);

	int delete(int idOne, int idTwo);
	
	int updateIsInvite(int idOne, int idTwo);
	
	public FriendShip isInviteById(int idOne, int idTwo);
	
	boolean searchFriendShipAdd(int idOne, int idTwo);

	List<FriendShip> getAllFriend(int userId);
	
	List<FriendShip> getAllInvite(int userId);

}
