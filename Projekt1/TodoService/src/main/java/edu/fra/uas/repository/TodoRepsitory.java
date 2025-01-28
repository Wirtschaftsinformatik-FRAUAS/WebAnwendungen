package edu.fra.uas.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import edu.fra.uas.model.Todo;

@Repository
public class TodoRepsitory extends HashMap<Long, Todo> {

}
