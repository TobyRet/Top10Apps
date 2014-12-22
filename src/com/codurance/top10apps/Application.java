package com.codurance.top10apps;

public class Application {
	private String name;
	private String artist;
	private String releasedate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getReleasedate() {
		return releasedate;
	}
	public void setReleasedate(String releasedate) {
		this.releasedate = releasedate;
	}
	
	public String toString() {
		return "Name: " + this.name + "\n" + "Artist: " + this.artist + "\n" + "Release Date: " + this.releasedate +"\n";
	}
}
