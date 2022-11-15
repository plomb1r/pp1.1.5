package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static final String DB_USER = "root";
    public static final String USER_PASSWORD = "1234";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/users1";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static Connection connection = null;
    private static SessionFactory concreteSessionFactory;

    /**
     * JDBC connection
     */
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, USER_PASSWORD);
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Hibernate configuration
     */
    static {
        try {
            Configuration configuration = new Configuration().addAnnotatedClass(User.class);
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/users1");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "1234");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL57Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_AUTO, "update");
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            concreteSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Session getSession() throws HibernateException {
        return concreteSessionFactory.openSession();
    }

}
