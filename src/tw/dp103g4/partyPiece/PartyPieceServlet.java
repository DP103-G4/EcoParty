package tw.dp103g4.partyPiece;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import tw.dp103g4.pieceImg.PieceImgDao;
import tw.dp103g4.pieceImg.PieceImgDaoImpl;

@SuppressWarnings("serial")
@WebServlet("/PartyPieceServlet")
public class PartyPieceServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
       private PartyPieceDao partyPieceDao = null;
       private PieceImgDao pieceImgDao = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder()  
				  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
				  .create(); 
		StringBuilder jsonIn = new StringBuilder();
		BufferedReader br = request.getReader();
		String line = "";
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (partyPieceDao == null) {
			partyPieceDao = new PartyPieceDaoImpl();
		}
		if (pieceImgDao == null) {
			pieceImgDao = new PieceImgDaoImpl();
		}
		
		
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getPieceInfoList")) {
			int partyId = jsonObject.get("partyId").getAsInt();
			List<PieceInfo> pieceInfoList = partyPieceDao.getAllByParty(partyId);
			writeText(response, gson.toJson(pieceInfoList));
			
		} else if (action.equals("pieceInsert") || action.equals("pieceUpdate")) {
			String pieceJson = jsonObject.get("piece").getAsString();
			System.out.println("pieceJson = " + pieceJson);

			PartyPiece partyPiece = gson.fromJson(pieceJson, PartyPiece.class);
			int count = 0;
			int ai = 0;
			if (action.equals("pieceInsert")) {
				ai = partyPieceDao.insert(partyPiece);
				
				if (ai != 0) {
				
					String imgsJson = jsonObject.get("imagesBase64").getAsString();
					List<String> imagesBase64 = gson.fromJson(imgsJson, new TypeToken<List<String>>() {}.getType());
	
					byte[] image = null;
					if (jsonObject.get("imagesBase64") != null) {
						for (String imgBase64: imagesBase64) {
							if (imgBase64 != null && !imgBase64.isEmpty()) {
								image = Base64.getMimeDecoder().decode(imgBase64);
								count = pieceImgDao.insert(ai, image);
							}
						}
					} 
				}
				
			} else if (action.equals("pieceUpdate")) {
				count = partyPieceDao.update(partyPiece);
			}
			writeText(response, String.valueOf(count));
			
		} else if (action.equals("pieceDelete")) {
			int pieceId = jsonObject.get("id").getAsInt();
			int count = partyPieceDao.delete(pieceId);
			writeText(response, String.valueOf(count));
			
		}else if (action.equals("deleteById")) {
			int pieceId = jsonObject.get("id").getAsInt();
			int count = partyPieceDao.delete(pieceId);
			writeText(response, String.valueOf(count));
			
		} else if (action.equals("getOneById")) {
			Integer id = jsonObject.get("id").getAsInt();
			PartyPiece partyPiece = partyPieceDao.getOneById(id);
			writeText(response, gson.toJson(partyPiece));
		}else {
			writeText(response, "");
		}
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
