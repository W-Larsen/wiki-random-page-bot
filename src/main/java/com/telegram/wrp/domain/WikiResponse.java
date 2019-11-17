package com.telegram.wrp.domain;

/**
 * Wiki response pojo object.
 */
public class WikiResponse {

    private String title;
    private String url;

    /**
     * Default constructor.
     */
    public WikiResponse() {
    }

    /**
     * Constructor with parameters.
     *
     * @param title the title
     * @param url   the url
     */
    public WikiResponse(String title, String url) {
        this.title = title;
        this.url = url;
    }

    /**
     * Gets title.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
