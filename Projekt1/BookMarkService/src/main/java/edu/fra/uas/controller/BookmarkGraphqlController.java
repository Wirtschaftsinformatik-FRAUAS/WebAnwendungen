package edu.fra.uas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import edu.fra.uas.model.Bookmark;
import edu.fra.uas.service.BookmarkService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookmarkGraphqlController {

    private static final Logger log = LoggerFactory.getLogger(BookmarkGraphqlController.class);

    @Autowired
    private BookmarkService bookmarkService;

    // Query to retrieve all bookmarks for a specific user
    @QueryMapping(name = "getAllBookmarksByUserId")
    public List<Bookmark> getAllBookmarksByUserId(@Argument Long userId) {
        log.debug("getAllBookmarksByUserId() is called with userId: {}", userId);
        Iterable<Bookmark> bookmarksIter = bookmarkService.getAllBookmarksByUserId(userId);
        List<Bookmark> bookmarks = new ArrayList<>();
        for (Bookmark bookmark : bookmarksIter) {
            bookmarks.add(bookmark);
        }
        return bookmarks;
    }

    // Query to retrieve a specific bookmark by userId and bookmarkId
    @QueryMapping(name = "getBookMarkByUserId")
    public Bookmark getBookMarkByUserId(@Argument Long userId, @Argument Long bookmarkId) {
        log.debug("getBookMarkByUserId() is called with userId: {} and bookmarkId: {}", userId, bookmarkId);
        return bookmarkService.getBookMarkByUserId(userId, bookmarkId);
    }

    // Mutation to create a new bookmark
    @MutationMapping(name = "createBookmark")
    public Bookmark createBookmark(@Argument Long userId, @Argument String title, @Argument String link) {
        log.debug("createBookmark() is called with userId: {}, title: {}, link: {}", userId, title, link);
        Bookmark newBookmark = new Bookmark();
        newBookmark.setTitle(title);
        newBookmark.setLink(link);
        return bookmarkService.createBookmark(userId, newBookmark);
    }

    // Mutation to update an existing bookmark
    @MutationMapping(name = "updateBookmarkForUserId")
    public Bookmark updateBookmarkForUserId(@Argument Long userId, @Argument Long bookmarkId, @Argument String title, @Argument String link) {
        log.debug("updateBookmarkForUserId() is called with userId: {}, bookmarkId: {}, title: {}, link: {}", userId, bookmarkId, title, link);
        Bookmark updatedBookmark = new Bookmark();
        updatedBookmark.setTitle(title);
        updatedBookmark.setLink(link);
        return bookmarkService.updateBookmarkForUserId(userId, bookmarkId, updatedBookmark);
    }

    // Mutation to delete a bookmark
    @MutationMapping(name = "deleteBookmarkByUserId")
    public Bookmark deleteBookmarkByUserId(@Argument Long userId, @Argument Long bookmarkId) {
        log.debug("deleteBookmarkByUserId() is called with userId: {} and bookmarkId: {}", userId, bookmarkId);
        return bookmarkService.deleteBookmarkByUserId(userId, bookmarkId);
    }
}
