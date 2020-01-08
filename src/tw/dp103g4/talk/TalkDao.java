package tw.dp103g4.talk;

import java.util.List;

public interface TalkDao {
	List<Talk> getAll(int userId, int friendId);
	List<NewestTalk> getNewestTalk(int userId);
	int insert(Talk talk);
	int updateIsRead(int userId);

	

}
