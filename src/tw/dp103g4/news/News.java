package tw.dp103g4.news;

import java.sql.Timestamp;

public class News {
	private int id;
	private String title;
	private String content;
	private Timestamp time;

	public News(int id, String title, String content, Timestamp time) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}
