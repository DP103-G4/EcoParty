package tw.dp103g4.news;

import java.sql.Date;

public class News {
	private int news_id;
	private String news_title;
	private String news_content;
	private Date news_time;
	
	public News(int news_id, String news_title, String news_content, Date news_time) {
		super();
		this.news_id = news_id;
		this.news_title = news_title;
		this.news_content = news_content;
		this.news_time = news_time;
	}

	public int getNews_id() {
		return news_id;
	}

	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}

	public String getNews_title() {
		return news_title;
	}

	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}

	public String getNews_content() {
		return news_content;
	}

	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}

	public Date getNews_time() {
		return news_time;
	}

	public void setNews_time(Date news_time) {
		this.news_time = news_time;
	}
	
	
	
}
