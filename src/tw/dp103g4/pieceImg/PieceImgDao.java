package tw.dp103g4.pieceImg;

import java.util.List;

public interface PieceImgDao {
	public List<PieceImg> getAllByPiece(int pieceId);
	
	public int insert(PieceImg pieceImg, byte[] data);
	
	public int delete(int id);
	
	public byte[] getData(int id);
	
}
