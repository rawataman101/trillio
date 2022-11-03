package com.cognizant.trillio.managers;

import java.util.List;

import com.cognizant.trillio.constants.BookGenre;
import com.cognizant.trillio.constants.KidFriendlyStatus;
import com.cognizant.trillio.constants.MovieGenre;
import com.cognizant.trillio.dao.BookmarkDao;
import com.cognizant.trillio.entities.Book;
import com.cognizant.trillio.entities.Bookmark;
import com.cognizant.trillio.entities.Movie;
import com.cognizant.trillio.entities.User;
import com.cognizant.trillio.entities.UserBookmark;
import com.cognizant.trillio.entities.Weblink;

public class BookmarkManager {
	private static BookmarkManager instance = new BookmarkManager();
	private static BookmarkDao bookmarkDao = new BookmarkDao();

	private BookmarkManager() {
	}

	public static BookmarkManager getInstance() {
		return instance;
	}

	public Movie createMovie(long id, String title, int releaseYear, String[] cast, String[] directors,
			MovieGenre genre, double imdbRating) {
		Movie movie = new Movie();
		movie.setCast(cast);
		movie.setDirectors(directors);
		movie.setId(id);
		// movie.setProfileUrl(profileUrl);
		movie.setReleaseYear(releaseYear);
		movie.setTitle(title);
		movie.setImdbRating(imdbRating);
		movie.setGenre(genre);
		return movie;
	}

	public Book createBook(long id, String title, int publicationYear, String publishers, String[] authors,
			BookGenre genre, double amazonRating) {
		Book book = new Book();
		book.setAmazonRating(amazonRating);
		book.setAuthors(authors);
		book.setGenre(genre);
		book.setId(id);
		// book.setProfileUrl(profileUrl);
		book.setPublicationYear(publicationYear);
		book.setPublisher(publishers);
		book.setTitle(title);
		return book;
	}

	public Weblink createWeblink(long id, String title, String url, String host) {
		Weblink weblink = new Weblink();
		weblink.setHost(host);
		weblink.setId(id);
		weblink.setProfileUrl(url);
		weblink.setTitle(title);
		weblink.setUrl(url);
		return weblink;
	}

	public List<List<Bookmark>> getBookmarks() {
		return bookmarkDao.getBookmarks();
	}

	public void saveUserBookmark(User user, Bookmark bookmark) {
		UserBookmark userBookmark = new UserBookmark();
		userBookmark.setBookmark(bookmark);
		userBookmark.setUser(user);

		/*
		 * if (bookmark instanceof Weblink) { try { String url =
		 * ((Weblink)bookmark).getUrl(); if (!url.endsWith(".pdf")) { String webpage =
		 * HttpConnect.download(((Weblink)bookmark).getUrl()); if (webpage != null) {
		 * IOUtil.write(webpage, bookmark.getId()); } } } catch (MalformedURLException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (URISyntaxException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */

		bookmarkDao.saveUserBookmark(userBookmark);
	}

	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
		// TODO Auto-generated method stub
		bookmark.setKidFriendlyStatus(kidFriendlyStatus);
		bookmark.setKidFriendlyMarkedBy(user);

		bookmarkDao.updateKidFriendlyStatus(bookmark);
		System.out.println("Kid Friendly Status: " + kidFriendlyStatus + " marked by : " + user.getEmail());

	}

	public void share(User user, Bookmark bookmark) {
		bookmark.setShareBy(user);
		System.out.println("Data to be shared.....");
		if (bookmark instanceof Book) {
			System.out.println(((Book) bookmark).getItemData());
		} else if (bookmark instanceof Weblink) {
			System.out.println(((Weblink) bookmark).getItemData());
		}
		bookmarkDao.shareByInfo(bookmark);

	}
}
