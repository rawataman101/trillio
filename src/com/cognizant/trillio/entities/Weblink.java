package com.cognizant.trillio.entities;

import com.cognizant.trillio.partners.Shareable;

public class Weblink extends Bookmark implements Shareable {
	private String url;
	private String host;
	private String htmlPage;
	private DownloadStatus downloadStatus = DownloadStatus.NOT_ATTEMPTED;

	public enum DownloadStatus {
		NOT_ATTEMPTED, SUCCESS, FAILED, NOT_ELIGIBLE; // not eligible for download
	}

	public String getUrl() {
		return url;
	}

	public String getHost() {
		return host;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		// return "Weblink [url=" + url + ", host=" + host + "]";
		return "Weblink"+" is frindly -> "+isKidFriendlyEligible();
	}

	@Override
	public boolean isKidFriendlyEligible() {
		if (url.contains("porn") || getTitle().contains("porn") || host.contains("adult") || url.contains("porn"))
			return false;
		return true;

	}

	@Override
	public String getItemData() {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("<item>");
		builder.append("<type>WebLink</type>");
		builder.append("<title>").append(getTitle()).append("</title>");
		builder.append("<url>").append(getUrl()).append("</url>");
		builder.append("<title>").append(getHost()).append("</title>");
		builder.append("</item>");
		return builder.toString();
	}

	public String getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
	}

	public DownloadStatus getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(DownloadStatus downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

}
