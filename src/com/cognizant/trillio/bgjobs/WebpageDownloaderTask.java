package com.cognizant.trillio.bgjobs;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.cognizant.trillio.dao.BookmarkDao;
import com.cognizant.trillio.entities.Weblink;
import com.cognizant.trillio.entities.Weblink.DownloadStatus;
import com.congnizant.trillio.util.HttpConnect;
import com.congnizant.trillio.util.IOUtil;

public class WebpageDownloaderTask implements Runnable {
	private static BookmarkDao dao = new BookmarkDao();
	private static final long TIME_FRAME = 3000000000L; // 3secs
	private boolean downloadAll = false;

	// executors
	ExecutorService downloadExecutor = Executors.newFixedThreadPool(5);

	private static class Downloader<T extends Weblink> implements Callable<T> {
		private T weblink;

		public Downloader(T weblink) {
			this.weblink = weblink;
		}

		public T call() {
			try {
				if (!weblink.getUrl().endsWith(".pdf")) {
					weblink.setDownloadStatus(DownloadStatus.FAILED);
					String htmlPage = HttpConnect.download(weblink.getUrl());
					weblink.setHtmlPage(htmlPage);
				} else {
					weblink.setDownloadStatus(DownloadStatus.NOT_ELIGIBLE);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return weblink;
		}
	}

	public WebpageDownloaderTask(boolean downloadAll) {
		this.downloadAll = downloadAll;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			// get weblinks
			List<Weblink> weblinks = getWeblinks();

			// download concurrently
			if (weblinks.size() > 0) {
				download(weblinks);
			} else {
				System.out.println("No new Web link to download");
			}
			// wait
			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		downloadExecutor.shutdown();

	}

	private void download(List<Weblink> weblinks) {
		List<Downloader<Weblink>> tasks = getTask(weblinks);
		List<Future<Weblink>> futures = new ArrayList<>();

		try {
			futures = downloadExecutor.invokeAll(tasks, TIME_FRAME, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (Future<Weblink> future : futures) {
			try {
				if (!future.isCancelled()) {
					Weblink weblink = future.get();
					String webPage = weblink.getHtmlPage();
					if (webPage != null) {
						IOUtil.write(webPage, weblink.getId());
						weblink.setDownloadStatus(Weblink.DownloadStatus.SUCCESS);
						System.out.println("Download Success: " + weblink.getUrl());

					} else {
						System.out.println("Webpage not downloaded: " + weblink.getUrl());
					}
				} else {
					System.out.println("\n\nTask is cancelled --> " + Thread.currentThread());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	private List<Downloader<Weblink>> getTask(List<Weblink> weblinks) {
		List<Downloader<Weblink>> tasks = new ArrayList<>();
		for (Weblink weblink : weblinks) {
			tasks.add(new Downloader<Weblink>(weblink));
		}
		return tasks;
	}

	private List<Weblink> getWeblinks() {
		List<Weblink> weblinks = null;
		if (downloadAll) {
			weblinks = dao.getAllWeblinks();
			downloadAll = false;
		} else {
			weblinks = dao.getWeblinks(Weblink.DownloadStatus.NOT_ATTEMPTED);
		}
		return weblinks;
	}
}
