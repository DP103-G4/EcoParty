package tw.dp103g4.talk;

import java.util.List;

public interface TalkDao {
	List<Talk> getAll();
	int insert(Talk talk);
//	int delete(int id);
	int updateIsRead();

}
