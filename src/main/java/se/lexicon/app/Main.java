package se.lexicon.app;

import se.lexicon.dao.People;
import se.lexicon.dao.ToDoItems;
import se.lexicon.dao.impl.PeopleImpl;
import se.lexicon.dao.impl.ToDoItemsImpl;
import se.lexicon.model.Person;
import se.lexicon.model.ToDo;

import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Create new DAO instances
        People people = new PeopleImpl();
        ToDoItems toDoItems = new ToDoItemsImpl();

        Person roman = people.findbById(17)
                .orElseThrow(() -> new IllegalArgumentException("Person Roman not found"));
        Person artur = people.findbById(18)
                .orElseThrow(() -> new IllegalArgumentException("Person Artur not found"));

        // Create todos (DB rows created with assignee_id = NULL)
        ToDo toDo1 = toDoItems.create(new ToDo(
                "Hand cabinets",
                "Continue kitchen renovation",
                LocalDate.now().plusMonths(2)));

        ToDo toDo2 = toDoItems.create(new ToDo(
                "Install dishwasher",
                "Buy a Siemens dishwasher and install it",
                LocalDate.now().plusMonths(1)));

        System.out.println("Created: " + toDo1);
        System.out.println("Created: " + toDo2);

        //Now lets assign and update DB
        // Assign + persist
        toDo1.setTaskAssignee(roman);
        toDoItems.update(toDo1);

        toDo2.setTaskAssignee(artur);
        toDoItems.update(toDo2);

        System.out.println("After update:");
        System.out.println(toDoItems.findById(toDo1.getToDoId()));
        System.out.println(toDoItems.findById(toDo2.getToDoId()));

        //Change todo1 job status done(true) status:
        toDo1.setDone(true);
        toDoItems.update(toDo1);
        System.out.println("Updated todo1: " + toDoItems.findById(toDo1.getToDoId()));

        // 5) Verify queries
        System.out.println("Todos for Roman:");
        toDoItems.findByAssignee(roman).forEach(System.out::println);

        System.out.println("Unassigned todos:");
        toDoItems.findByUnassignedToDoItems().forEach(System.out::println);

//        //Create 2 persons for testing
//        Person person1 = people.create(new Person("Roman", "Vanoyan"));
//        Person person2 = people.create(new Person("Artur", "Vanoyan"));

//        System.out.println("Created " + person2);
//        System.out.println();
//
//        //print all people in the table
//        System.out.println("Find all: " + people.findAll());
//
//        System.out.println("Find by id: " + people.findbById(person1.getPersonId()).orElse(null));
//        System.out.println("Find by name 'Ro': " + people.findByName("Ro"));
//        System.out.println("Find by name 'Va': " + people.findByName("Ro"));
//        System.out.println("Delete by name 'Ro' after deleting one: " + people.deleteById(1));

    }
}