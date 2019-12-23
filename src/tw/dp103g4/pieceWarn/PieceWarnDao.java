package tw.dp103g4.pieceWarn;

import java.util.List;

public interface PieceWarnDao {
	List<PieceWarn> getAll();
//	int getUserId(int id);
	int insert(PieceWarn pieceWarn);
	int delete(int id);

}
