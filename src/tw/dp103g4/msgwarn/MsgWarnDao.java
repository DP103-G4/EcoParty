package tw.dp103g4.msgwarn;

import java.util.List;

public interface MsgWarnDao {
	List<MsgWarn> getAll();
//	int getUserId(int id);
	int inster(MsgWarn msgWarn);
	int delete(int id);
	
}
