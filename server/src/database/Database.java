package database;

import models.User;

public abstract class Database {
    public abstract User createUser(String displayName, String username, String password) throws Exception;
    public abstract User getUser(String username, String password) throws Exception ;
}
