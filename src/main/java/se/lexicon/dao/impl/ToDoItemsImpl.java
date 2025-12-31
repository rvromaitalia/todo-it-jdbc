package se.lexicon.dao.impl;

import se.lexicon.dao.ToDoItems;
import se.lexicon.db.ConnectionFactory;
import se.lexicon.model.Person;
import se.lexicon.model.ToDo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC-based implementation of the {@link se.lexicon.dao.ToDoItems} interface.
 * <p>
 * This class provides CRUD operations and query methods for {@link se.lexicon.model.ToDo}
 * entities stored in the {@code todo_item} database table. All interactions with the
 * database are performed using JDBC through prepared statements.
 * </p>
 *
 * <p>
 * The implementation is responsible for mapping database rows to {@code ToDo} objects
 * and handling database-generated identifiers, while keeping persistence logic isolated
 * from the rest of the application.
 * </p>
 */
public class ToDoItemsImpl implements ToDoItems {

    /**
     * Persists a new {@link ToDo} entity to the database.
     * <p>
     * The todo item is inserted into the {@code todo_item} table and the
     * auto-generated primary key is retrieved and assigned to the given object.
     * </p>
     *
     * @param toDo the todo item to persist
     * @return the persisted todo item with its generated ID set
     * @throws RuntimeException if the insert operation fails
     */
    @Override
    public ToDo create(ToDo toDo) {
        String sql = "INSERT INTO todo_item (title, description, deadline, done, assignee_id) " +
                "VALUES(?,?,?,?,?)";
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            //Update sql query command with the missing information
            ps.setString(1,toDo.getTaskTittle());
            ps.setString(2,toDo.getTaskDescripton());
            //Do a basic check of some table field data before inserting into the table
            //1. If date is valid, insert else
            if (toDo.getTaskDeadline()!= null){
                ps.setDate(3, Date.valueOf(toDo.getTaskDeadline()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setBoolean(4,toDo.isDone());

            // assignee_id can be NULL
            if (toDo.getTaskAssignee()!= null && toDo.getTaskAssignee().getPersonId()>0)
                ps.setInt(5,toDo.getTaskAssignee().getPersonId());
            else
                ps.setNull(5, Types.INTEGER);

            ps.executeUpdate();

            // Retrieves the auto-generated primary key created by the database
            // and assigns it to the ToDo object to keep it in sync with the DB row.
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    toDo.setToDoId(keys.getInt(1));
                }
            }

            return toDo;

        } catch (SQLException e) {
            throw new RuntimeException("Could not create a tot item", e);
        }
    }

    /**
     * Retrieves all todo items from the database.
     *
     * @return a list containing all {@link ToDo} records
     * @throws RuntimeException if the query fails
     */
    @Override
    public List<ToDo> findAll() {
        String sql = "SELECT todo_id, title, description, deadline, deon, assignee_id from todo_item";
        List<ToDo> result = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet res = ps.executeQuery()){

            while (res.next()){
                result.add(mapToDo(res));
            }
            return result;

        } catch (SQLException e){
            throw new RuntimeException("Could not fecth table data", e);
        }
    }

    /**
     * Finds a todo item by its unique identifier.
     *
     * @param id the primary key of the todo item
     * @return the matching {@link ToDo}, or {@code null} if no match exists
     * @throws RuntimeException if the query fails
     */
    @Override
    public ToDo findById(int id) {
        String sql = "SELECT todo_id, title, description, deadline, done, assignee_id from todo_item " +
                "where todo_id = ? ";

        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            //Execute query now
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapToDo(rs);
                return null;
            }
        } catch (SQLException e){
            throw new RuntimeException("Failed to find todo item", e);
        }
    }

    /**
     * Retrieves all todo items filtered by completion status.
     *
     * @param done {@code true} to find completed items, {@code false} otherwise
     * @return a list of todo items matching the given status
     * @throws RuntimeException if the query fails
     */
    @Override
    public List<ToDo> findByDoneStatus(boolean done) {
        String sql = "SELECT todo_id, title, description, deadline, done, assignee_id from todo_item " +
                "where done = ? ";
        List<ToDo> result = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setBoolean(1, done);

            //Execute query now
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(mapToDo(rs));
            }
            return result;

        } catch (SQLException e){
            throw new RuntimeException("Failed to find todo item", e);
        }
    }

    /**
     * Retrieves all todo items assigned to the specified person ID.
     *
     * @param personId the identifier of the assignee
     * @return a list of todo items assigned to the given person
     * @throws RuntimeException if the query fails
     */
    @Override
    public List<ToDo> findByAssignee(int personId) {

        String sql = "SELECT todo_id, title, description, deadline, done, assignee_id from todo_item " +
                "where assignee = ? ";
        List<ToDo> result = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, personId);

            //Execute query now
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(mapToDo(rs));
            }
            return result;

        } catch (SQLException e){
            throw new RuntimeException("Failed to find todo items by assignee id", e);
        }
    }

    /**
     * Retrieves all todo items assigned to the specified {@link Person}.
     * <p>
     * This method delegates to {@link #findByAssignee(int)} using the person's ID.
     * </p>
     *
     * @param person the assignee
     * @return a list of todo items assigned to the given person
     */
    @Override
    public List<ToDo> findByAssignee(Person person) {
        List<ToDo> result = new ArrayList<>();

        if (person == null) {
            return result;
        }

        String sql = """
        SELECT todo_id, title, description, deadline, done, assignee_id
        FROM todo_item
        WHERE assignee_id = ?
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, person.getPersonId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapToDo(rs));
                }
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find todo items by assignee", e);
        }
    }

    /**
     * Retrieves all todo items that are not assigned to any person.
     *
     * @return a list of unassigned todo items
     * @throws RuntimeException if the query fails
     */
    @Override
    public List<ToDo> findByUnassignedToDoItems() {
        List<ToDo> result = new ArrayList<>();

        String sql = """
        SELECT todo_id, title, description, deadline, done, assignee_id
        FROM todo_item
        WHERE assignee_id = NULL
        """;

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapToDo(rs));
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find unassigned todo items by assignee", e);
        }
    }

    /**
     * Updates an existing todo item in the database.
     * <p>
     * All fields of the given {@link ToDo} are synchronized with the corresponding
     * database record identified by its ID.
     * </p>
     *
     * @param toDo the todo item containing updated values
     * @return the updated todo item
     * @throws RuntimeException if no matching record exists or the update fails
     */
    @Override
    public ToDo update(ToDo toDo) {
        String sql = """
        UPDATE todo_item
        SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ?
        WHERE todo_id = ?
        """;
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            //Fill in a query with the missing parameters
            ps.setString(1, toDo.getTaskTittle());
            ps.setString(2, toDo.getTaskDescripton());

            if (toDo.getTaskDeadline()!= null)
                ps.setDate(3, Date.valueOf(toDo.getTaskDeadline()));
            else
                ps.setNull(3, Types.DATE);

            ps.setBoolean(4,toDo.isDone());

            if (toDo.getTaskAssignee() != null && toDo.getTaskAssignee().getPersonId()>0)
                ps.setInt(5, toDo.getTaskAssignee().getPersonId());
            else
                ps.setNull(5, Types.INTEGER);

            ps.setInt(6, toDo.getToDoId());

            //Lets execute our query
            int updated = ps.executeUpdate();
            if (updated == 0) { //No rows were updated
                throw new RuntimeException(
                        "No todo item found to update with id " + toDo.getToDoId()
                );
            }

            return toDo;

        } catch (SQLException e) {
            throw new RuntimeException("Update failed", e);
        }
    }

    /**
     * Deletes a todo item from the database by its unique identifier.
     *
     * @param id the primary key of the todo item to delete
     * @return {@code true} if a record was deleted, {@code false} otherwise
     * @throws RuntimeException if the delete operation fails
     */
    public boolean deleteById(int id) {
        String sql = "DELETE FROM todo_item WHERE todo_id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete todo item", e);
        }
    }

    /**
     * Maps the current {@link ResultSet} row to a {@link ToDo} object.
     * The {@code assignee_id} column is mapped to a lightweight {@link Person}
     * containing only {@code personId}. (No extra query is executed here.)
     */
    private ToDo mapToDo(ResultSet rs) throws SQLException {
        int todoId = rs.getInt("todo_id");
        String title = rs.getString("title");
        String description = rs.getString("description");

        Date date = rs.getDate("deadline");
        LocalDate deadline = (date != null) ? date.toLocalDate() : null;

        boolean done = rs.getBoolean("done");

        Integer assigneeId = rs.getObject("assignee_id", Integer.class);
        Person assignee = (assigneeId != null) ? new Person(assigneeId, null, null) : null;

        return new ToDo(todoId, title, description, done, assignee, deadline);
    }
}
