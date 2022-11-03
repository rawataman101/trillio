package com.cognizant.trillio.entities;

import java.util.Arrays;

import com.cognizant.trillio.constants.MovieGenre;

public class Movie extends Bookmark {
	private int releaseYear;
	private String[] cast;
	private String[] directors;
	private double imdbRating;
	private MovieGenre genre;

	public int getReleaseYear() {
		return releaseYear;
	}

	public String[] getCast() {
		return cast;
	}

	public String[] getDirectors() {
		return directors;
	}

	public double getImdbRating() {
		return imdbRating;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public void setCast(String[] cast) {
		this.cast = cast;
	}

	public void setDirectors(String[] directors) {
		this.directors = directors;
	}

	public void setImdbRating(double imdbRating) {
		this.imdbRating = imdbRating;
	}

	public MovieGenre getGenre() {
		return genre;
	}

	public void setGenre(MovieGenre genre) {
		this.genre = genre;
	}

	@Override
	public String toString() {
//		return "Movie [releaseYear=" + releaseYear + ", cast=" + Arrays.toString(cast) + ", directors="
//				+ Arrays.toString(directors) + ", imdbRating=" + imdbRating + ", genre=" + genre + "]";
		return "Movie"+" is frindly -> "+isKidFriendlyEligible();
	}

	@Override
	public boolean isKidFriendlyEligible() {
		// TODO Auto-generated method stub
		return true;
	}

}
