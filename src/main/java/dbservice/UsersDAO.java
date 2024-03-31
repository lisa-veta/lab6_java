package dbservice;

import accounts.UserProfile;

import java.sql.*;

public class UsersDAO {

    private final Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UserProfile get(String login) throws SQLException {
        return executor.execQuery("select * from users1 where login = '" + login + "'", result -> {
            if(result.next()){
                return new UserProfile(result.getString(1), result.getString(2), result.getString(3));
            }
            else {
                return null;
            }
        });
    }

    public void insertUser(UserProfile user) throws SQLException {
        executor.execUpdate("INSERT INTO users1 (login, pass, email) VALUES ('"
                + user.getLogin() + "', '" + user.getPass() + "', '" + user.getEmail() + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users1 (login VARCHAR(50) NOT NULL,\n" +
                "    pass VARCHAR(50) NOT NULL,\n" +
                "    email VARCHAR(100))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users1");
    }
}
