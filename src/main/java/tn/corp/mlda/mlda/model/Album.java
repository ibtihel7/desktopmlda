package tn.corp.mlda.mlda.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Album {
	private String title;
	private String description;
	private String image;
	private Singer singer;
	private int year;
	private List<Song> songs = new ArrayList<>();
	public Album() {
		// TODO Auto-generated constructor stub
	}
	public Album(String title,String description,String image,int year,Singer singer,Song[] songs) {
		this.title=title;
		this.description=description;
		this.image=image;
		this.year=year;
		this.singer=singer;
		this.songs.addAll(Arrays.asList(songs));
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Singer getSinger() {
		return singer;
	}

	public void setSinger(Singer singer) {
		this.singer = singer;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}