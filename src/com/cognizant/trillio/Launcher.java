package com.cognizant.trillio;

import java.util.List;

import com.cognizant.trillio.bgjobs.WebpageDownloaderTask;
import com.cognizant.trillio.entities.Bookmark;
import com.cognizant.trillio.entities.User;
import com.cognizant.trillio.managers.BookmarkManager;
import com.cognizant.trillio.managers.UserManager;

public class Launcher {
	private static List<User> users;
	private static List<List<Bookmark>> bookmarks;

	public static void loadData() {
		System.out.println("1. Loading data ...");
		DataStore.loadData();

		users = UserManager.getInstance().getUsers();
		bookmarks = BookmarkManager.getInstance().getBookmarks();

		// System.out.println("Printing data .....");
		// printUserData();
		// printBookMarkData();
	}

	private static void printBookMarkData() {
		System.out.println("------Bookmark Data -----");

		for (List<Bookmark> bookmarkList : bookmarks) {
			for (Bookmark bookmark : bookmarkList) {
				System.out.println(bookmark);
			}
		}
	}

	private static void printUserData() {
		System.out.println("------User Data -----");
		for (User user : users) {
			System.out.println(user);
		}
	}

	public static void main(String[] args) {
		loadData();
		start();
		// runDownloaderJob(); //back processing
	}

	private static void start() {
		System.out.println("\n2. Bookmarking .....");
		for (User user : users) {
			View.browse(user, bookmarks);
		}
	}

	private static void runDownloaderJob() {
		WebpageDownloaderTask task = new WebpageDownloaderTask(true);
		(new Thread(task)).start();
	}
}
