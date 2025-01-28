package edu.fra.uas.service;

import edu.fra.uas.model.Bookmark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.FieldAccessException;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {

    private static final Logger log = LoggerFactory.getLogger(BookmarkService.class);
    private static final String baseURI = "http://localhost:8083/graphql";
    private final HttpSyncGraphQlClient graphQlClient;

    public BookmarkService() {
        this.graphQlClient = HttpSyncGraphQlClient.builder().url(baseURI).build();
    }

    private void logExceptionalResponse(ClientGraphQlResponse response) {
        if (!response.isValid()) {
            log.error("Request failure ...");
        }
        response.getErrors().forEach(error -> log.error(error.getMessage()));
    }

    public List<Bookmark> getAllBookmarksByUserId(Long userId) {
        String query = "query { getAllBookmarksByUserId(userId: " + userId + ") { bookmarkId userId title link } }";

        try {
            return graphQlClient.document(query).retrieveSync("getAllBookmarksByUserId").toEntityList(Bookmark.class);
        } catch (FieldAccessException ex) {
            ClientGraphQlResponse response = ex.getResponse();
            logExceptionalResponse(response);
        }
        return null;
    }

    public Bookmark getBookMarkByUserId(Long userId, Long bookmarkId) {
        String query = "query { getBookMarkByUserId(userId: " + userId + ", bookmarkId: " + bookmarkId + ") { bookmarkId userId title link } }";

        try {
            return graphQlClient.document(query).retrieveSync("getBookMarkByUserId").toEntity(Bookmark.class);
        } catch (FieldAccessException ex) {
            ClientGraphQlResponse response = ex.getResponse();
            logExceptionalResponse(response);
        }
        return null;
    }

    public Bookmark createBookmark(Long userId, String title, String link) {
        String mutation = "mutation { createBookmark(userId: " + userId + ", title: \"" + title.replace("\"", "\\\"") + "\", link: \"" + link.replace("\"", "\\\"") + "\") { bookmarkId userId title link } }";

        try {
            return graphQlClient.document(mutation).retrieveSync("createBookmark").toEntity(Bookmark.class);
        } catch (FieldAccessException ex) {
            ClientGraphQlResponse response = ex.getResponse();
            logExceptionalResponse(response);
        }
        return null;
    }

    public Bookmark updateBookmarkForUserId(Long userId, Long bookmarkId, String title, String link) {
        String mutation = "mutation { updateBookmarkForUserId(userId: " + userId + ", bookmarkId: " + bookmarkId + ", title: \"" + title.replace("\"", "\\\"") + "\", link: \"" + link.replace("\"", "\\\"") + "\") { bookmarkId userId title link } }";

        try {
            return graphQlClient.document(mutation).retrieveSync("updateBookmarkForUserId").toEntity(Bookmark.class);
        } catch (FieldAccessException ex) {
            ClientGraphQlResponse response = ex.getResponse();
            logExceptionalResponse(response);
        }
        return null;
    }

    public Bookmark deleteBookmarkByUserId(Long userId, Long bookmarkId) {
        String mutation = "mutation { deleteBookmarkByUserId(userId: " + userId + ", bookmarkId: " + bookmarkId + ") { bookmarkId userId title link } }";

        try {
            return graphQlClient.document(mutation).retrieveSync("deleteBookmarkByUserId").toEntity(Bookmark.class);
        } catch (FieldAccessException ex) {
            ClientGraphQlResponse response = ex.getResponse();
            logExceptionalResponse(response);
        }
        return null;
    }
}
