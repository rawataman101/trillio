package com.cognizant.trillio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cognizant.trillio.DataStore;
import com.cognizant.trillio.entities.Book;
import com.cognizant.trillio.entities.Bookmark;
import com.cognizant.trillio.entities.Movie;
import com.cognizant.trillio.entities.UserBookmark;
import com.cognizant.trillio.entities.Weblink;

public class BookmarkDao {
	public List<List<Bookmark>> getBookmarks() {
		return DataStore.getBookmarks();
	}

	public void saveUserBookmark(UserBookmark userBookmark) {
		// DataStore.add(userBookmark);

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false",
				"root", "1234"); Statement stmt = conn.createStatement();) {
			if (userBookmark.getBookmark() instanceof Book) {
				saveUserBook(userBookmark, stmt);

			} else if (userBookmark.getBookmark() instanceof Movie) {
				saveUserMovie(userBookmark, stmt);
			} else {
				saveUserWeblink(userBookmark, stmt);
			}
		} catch (SQLException e) {
			System.out.println("Error here Not connected!!");
			e.printStackTrace();
		}
	}

	private void saveUserWeblink(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "insert into user_weblink (user_id, webLink_id) values (" + userBookmark.getUser().getId() + ", "
				+ userBookmark.getBookmark().getId() + ");";
		stmt.executeUpdate(query);

	}

	private void saveUserMovie(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "insert into user_movie (user_id, movie_id) values (" + userBookmark.getUser().getId() + ", "
				+ userBookmark.getBookmark().getId() + ");";
		stmt.executeUpdate(query);

	}

	private void saveUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query = "insert into user_book (user_id, book_id) values (" + userBookmark.getUser().getId() + ", "
				+ userBookmark.getBookmark().getId() + " );";
		stmt.executeUpdate(query);
	}

	public List<Weblink> getAllWeblinks() {
		List<Weblink> result = new ArrayList<>();
		List<List<Bookmark>> bookmarks = DataStore.getBookmarks();
		List<Bookmark> allWeblinks = bookmarks.get(0);
		for (Bookmark bookmark : allWeblinks) {
			result.add((Weblink) bookmark);
		}
		return result;
	}

	public List<Weblink> getWeblinks(Weblink.DownloadStatus downloadStatus) {
		List<Weblink> result = new ArrayList<>();
		List<Weblink> allWeblinks = getAllWeblinks();
		for (Weblink link : allWeblinks) {
			result.add(link);
		}

		return result;
	}

	public void updateKidFriendlyStatus(Bookmark bookmark) {
		int kidFriendlyStatus = bookmark.getKidFriendlyStatus().ordinal();
		long userId = bookmark.getKidFriendlyMarkedBy().getId();

		String tableUpdate = "Book";
		if (bookmark instanceof Movie) {
			tableUpdate = "Movie";
		} else if (bookmark instanceof Weblink) {
			tableUpdate = "Weblink";
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false",
				"root", "1234"); Statement stmt = conn.createStatement();) {

			String query = "update " + tableUpdate + " set kid_friendly_status = " + kidFriendlyStatus
					+ ", kid_friendly_marked_by = " + userId + " where id = " + bookmark.getId() + ";";
			System.out.println("query (updateKidFiendlyStatus): " + query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void shareByInfo(Bookmark bookmark) {
		long userId = bookmark.getShareBy().getId();
		String tableUpdate = "book";
		if (bookmark instanceof Weblink) {
			tableUpdate = "weblink";
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false",
				"root", "1234"); Statement stmt = conn.createStatement();) {
			String query = "update " + tableUpdate + " set shared_by = " + userId + " where id =" + bookmark.getId() + ";";
			stmt.executeUpdate(query);
			System.out.println("updated: share_by -> " + userId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
