package edu.fra.uas.model;

public class Bookmark {

    private long bookmarkId;
    private long userId;
    private String title;
    private String link;


    
    public Bookmark(long bookmarkId, long userId, String title, String link) {
        this.bookmarkId = bookmarkId;
        this.userId = userId;
        this.title = title;
        this.link = link;
    }


    public Bookmark() {
    }

    
    public long getBookmarkId() {
        return bookmarkId;
    }
    public void setBookmarkId(long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }


    

}
