package com.developerstack.edumanage.db;

import com.developerstack.edumanage.model.Program;
import com.developerstack.edumanage.model.Student;
import com.developerstack.edumanage.model.Teacher;
import com.developerstack.edumanage.model.User;
import com.developerstack.edumanage.util.security.PasswordManager;

import java.util.ArrayList;

public class Database {
    public static ArrayList<User> userTable = new ArrayList();
    public static ArrayList<Student> studentTable = new ArrayList();
    public static ArrayList<Teacher> teacherTable = new ArrayList();
    public static ArrayList<Program> programTable = new ArrayList();

    static {
        userTable.add(
                new User("a","a","a",new PasswordManager().encryptPassword("a"))
        );
    }


}
