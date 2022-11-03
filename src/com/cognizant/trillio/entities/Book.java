package com.cognizant.trillio.entities;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.cognizant.trillio.constants.BookGenre;
import com.cognizant.trillio.partners.Shareable;

public class Book extends Bookmark implements Shareable {
	private int publicationYear;
	private String publisher;
	private String[] authors;
	private BookGenre genre;
	private double amazonRating;

	public int getPublicationYear() {
		return publicationYear;
	}

	public String getPublisher() {
		return publisher;
	}

	public String[] getAuthors() {
		return authors;
	}

	public BookGenre getGenre() {
		return genre;
	}

	public double getAmazonRating() {
		return amazonRating;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public void setGenre(BookGenre genre) {
		this.genre = genre;
	}

	public void setAmazonRating(double amazonRating) {
		this.amazonRating = amazonRating;
	}

	@Override
	public String toString() {
//		return "Book [publicationYear=" + publicationYear + ", publisher=" + publisher + ", authors="
//				+ Arrays.toString(authors) + ", genre=" + genre + ", amazonRating=" + amazonRating + "]";
		return "Book" + " is frindly -> "+isKidFriendlyEligible();
	}

	@Override
	public boolean isKidFriendlyEligible() {
		if (genre.equals(BookGenre.PHILOSOPHY) || genre.equals(BookGenre.SELF_HELP))
			return false;
		else
			return true;
	}

	@Override
	public String getItemData() {
		StringBuilder builder = new StringBuilder();
		builder.append("<item>");
		builder.append("<type>Book</type>");
		builder.append("<title>").append(getTitle()).append("</title>");
		builder.append("<publisher>").append(getPublisher()).append("</publisher>");
		builder.append("<publicationYear>").append(getPublicationYear()).append("</publicationYear>");
		builder.append("<authors>").append(StringUtils.join(authors, ",")).append("</authors>");
		builder.append("<genre>").append(getGenre()).append("</genre>");
		builder.append("<amazonRating>").append(getAmazonRating()).append("</amazonRating>");
		builder.append("</item>");
		return builder.toString();
	}

}
