package trillio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

import com.cognizant.trillio.entities.Weblink;
import com.cognizant.trillio.managers.BookmarkManager;

class WebLinkTest {

	@Test
	void testIsKidFriendlyEligible() {		
		// test 1: porn in url -- false
		Weblink weblink = BookmarkManager.getInstance().createWeblink(2000, "Taming Tiger Part 2",
				"http://www.javaworld.com/article/2072759/core-java/taming--porn-part-2.html",
				"http://www.javaworld.com-");
		assertFalse("porn in url",weblink.isKidFriendlyEligible());
	 	
		// test 2: porn in title -- false
		weblink = BookmarkManager.getInstance().createWeblink(2000, "porn Tiger Part 2",
				"http://www.javaworld.com/article/2072759/core-java/taming-porn--part-2.html",
				"http://www.javaworld.com");
		assertFalse("porn in title",weblink.isKidFriendlyEligible());
		
		// test 3: adult in host -- false
		weblink = BookmarkManager.getInstance().createWeblink(2000, "Taming Tiger Part 2",
				"http://www.javaworld.com/article/2072759/core-java/taming-porn--part-2.html",
				"http://www.javaworld.adult");
		assertFalse("for adult in host",weblink.isKidFriendlyEligible());
		
		// test 4: adult in title but not in host -- true
		weblink = BookmarkManager.getInstance().createWeblink(2000, "adult Tiger Part 2",
				"http://www.javaworld.com/article/2072759/core-java/taming---part-2.html",
				"http://www.javaworld.com");
		assertTrue("adult in title but not in host false",weblink.isKidFriendlyEligible());
		
		// test 5: adult in title only -- true
		weblink = BookmarkManager.getInstance().createWeblink(2000, "Adult Tiger Part 2",
				"http://www.javaworld.com/article/2072759/core-java/taming---part-2.html",
				"http://www.javaworld.com");
		assertTrue("adult in title false",weblink.isKidFriendlyEligible());
		
	}

}
