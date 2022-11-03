package com.cognizant.trillio;

import java.util.List;

import com.cognizant.trillio.constants.KidFriendlyStatus;
import com.cognizant.trillio.constants.UserType;
import com.cognizant.trillio.controllers.BookmarkController;
import com.cognizant.trillio.entities.Bookmark;
import com.cognizant.trillio.entities.User;
import com.cognizant.trillio.partners.Shareable;

public class View {
	public static void browse(User user, List<List<Bookmark>> bookmarks) {
		System.out.println("\n" + user.getEmail() + " is browsing....");
		int bookmarkCount = 0;

		for (List<Bookmark> bookmarkList : bookmarks) {
			for (Bookmark bookmark : bookmarkList) {
				// if (bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
				boolean isBookmarked = getBookmarkDescription(bookmark);
				if (isBookmarked) {
					bookmarkCount++;
					BookmarkController.getInstance().saveUserBookmark(user, bookmark);
					System.out.println("-- New Item bookmarked -- " + bookmark);
				}
				// }
				// mark as kid friendly
				if (user.getUserType().equals(UserType.EDITOR) || user.getUserType().equals(UserType.CHIEF_EDITOR)) {
					if (bookmark.isKidFriendlyEligible()
							&& bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
						KidFriendlyStatus kidFriendlyStatus = getKidFriendlyStatusDecision(bookmark);
						if (!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {
							BookmarkController.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);

						}
					}
					// sharing
					if (bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED)
							&& bookmark instanceof Shareable) {
						boolean isShared = getShareDecision();
						if (isShared) {
							BookmarkController.getInstance().share(user, bookmark);
						}

					}

				}
			}
		}

//	public static void bookmark (User user, Bookmark[][] bookmarks) {
//		System.out.println("\n"+user.getEmail()+" is bookmarking");
//		for(int i = 0; i < DataStore.USER_BOOKMARK_LIMIT; i++ ) {
//			int typeOffset = (int)(Math.random() * DataStore.BOOKMARK_TYPES_COUNT);
//			int bookmarkOffset = (int) (Math.random() * DataStore.BOOKMARK_PER_COUNT);
//			Bookmark bookmark = bookmarks[typeOffset][bookmarkOffset];
//			
//			BookmarkController.getInstance().saveUserBookmark(user,bookmark);
//			
//			System.out.println(bookmark);
//		}

	}

	private static boolean getShareDecision() {
		return Math.random() < 0.5 ? true : false;

	}

	private static KidFriendlyStatus getKidFriendlyStatusDecision(Bookmark bookmark) {
		double decision = Math.random();
		return decision < 0.4 ? KidFriendlyStatus.APPROVED
				: (decision >= 0.4 && decision < 0.8) ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN;
//		return Math.random() < 0.4 ? KidFriendlyStatus.APPROVED
//				: (Math.random() >= 0.4 && Math.random() < 0.8) ? KidFriendlyStatus.REJECTED
//						: KidFriendlyStatus.UNKNOWN;
	}

	private static boolean getBookmarkDescription(Bookmark bookmark) {
		return Math.random() < 0.5 ? true : false;
	}
}
