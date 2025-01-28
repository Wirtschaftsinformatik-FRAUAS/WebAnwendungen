package edu.fra.uas.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import edu.fra.uas.model.Bookmark;

@Repository
public class BookmarkRepository extends HashMap<Long, Bookmark> {

}
