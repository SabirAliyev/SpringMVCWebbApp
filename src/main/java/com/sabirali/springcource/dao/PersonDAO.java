package com.sabirali.springcource.dao;

import com.sabirali.springcource.model.Person;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private static final String URL = "jdbc:postgresql://172.17.0.2:5432/first-db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "mypass";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            // Get data from DB
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    public Person show(int id) {
        return null;
    }

    public void save(Person person) {
//        person.setId(++PEOPLE_COUNT); // temporary
//        people.add(person);

        try {
            Statement statement = connection.createStatement();
            // Very bad code (testing insert)
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
            "'," + person.getAge() + ", '" + person.getEmail() + "')";

            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(int id, Person updatePerson) {
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(updatePerson.getName());
        personToBeUpdated.setAge(updatePerson.getAge());
        personToBeUpdated.setEmail(updatePerson.getEmail());
    }

    public void delete(int id) {
//         people.removeIf(p -> p.getId() == id);
    }
}
