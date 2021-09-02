package tn.corp.mlda.mlda.model;

public class Song {
	private String title;
	private Category category;
	private int duration;

	public Song() {
	}

	public Song(String title, Category category, int duration) {
		this.title=title;
		this.category=category;
		this.duration=duration;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}