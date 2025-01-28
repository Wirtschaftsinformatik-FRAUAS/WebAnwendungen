package edu.fra.uas.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import edu.fra.uas.model.Bookmark;
import edu.fra.uas.repository.BookmarkRepository;

@Service
public class BookmarkService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BookmarkService.class);

    @Autowired
    private BookmarkRepository bookmarkRepository;

    private long nextbookmarkId = 1;

    public Bookmark createBookmark(long userId, Bookmark bookmark) {
        bookmark.setBookmarkId(nextbookmarkId++);
        bookmark.setUserId(userId);
        log.debug("createBookmark: " + bookmark.getBookmarkId() + " for User: " + userId);
        bookmarkRepository.put(bookmark.getBookmarkId(), bookmark);
        return bookmarkRepository.get(bookmark.getBookmarkId());
    }

    public Iterable<Bookmark> getAllBookmarksByUserId(long userId) {
        log.debug("getAllBookmarksByUserId" + userId);
        Iterable<Bookmark> bookmarkIter = bookmarkRepository.values();
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        for (Bookmark bookmark : bookmarkIter) {
            if (bookmark.getUserId() == userId) {
                bookmarks.add(bookmark);
            }
        }

        return bookmarks;
    }

    public Bookmark getBookMarkByUserId(long userId, long bookmarkId) {
        log.debug("get Bookmark : " + bookmarkId + " for User: " + userId);
        for (Bookmark bookmark : bookmarkRepository.values()) {
            if (bookmark.getUserId() == userId && bookmark.getBookmarkId() == bookmarkId) {
                return bookmark;
            }
        }
        return null;
    }

    public Bookmark updateBookmarkForUserId(long userId, long bookmarkId, Bookmark newBookmark) {
        log.debug("update Todo: " + bookmarkId + " for User: " + userId);
        Bookmark bookmark = getBookMarkByUserId(userId, bookmarkId);
        bookmark.setLink(newBookmark.getLink());
        bookmark.setTitle(newBookmark.getTitle());
        bookmarkRepository.put(bookmark.getBookmarkId(), bookmark);
        return bookmarkRepository.get(bookmark.getBookmarkId());
    }

    public Bookmark deleteBookmarkByUserId(long userId, long bookmarkId) {
        log.debug("delete Todo: " + bookmarkId + " for User: " + userId);
        Bookmark bookmark = getBookMarkByUserId(userId, bookmarkId);
        return bookmarkRepository.remove(bookmark.getBookmarkId());
    }
}
