package se.lexicon.dao;

import se.lexicon.model.Person;
import se.lexicon.model.ToDo;

import java.util.List;

public interface ToDoItems {
    ToDo create(ToDo toDo);
    List<ToDo> findAll();
    ToDo findById(int id);
    List<ToDo> findByDoneStatus(boolean done);
    List<ToDo> findByAssignee(int i);
    List<ToDo> findByAssignee(Person person);
    List<ToDo> findByUnassignedToDoItems();
    ToDo update(ToDo toDo);
    boolean deleteById(int id);
}
