package com.romware.hibernatetests;

import com.romware.hibernatetests.model.User;
import com.romware.hibernatetests.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class HibernateUserDemo {

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        HibernateUserDemo application = new HibernateUserDemo();

        User user = application.getUserByLogin("hibernate_test");

        if(user != null) {
            application.deleteUser(user.getId());
        }

        /*
         * Save few objects with hibernate
         */
        int userId1 = application.saveUser("hibernate_test");

        /*
         * Retrieve all saved objects
         */
        List<User> users = application.getAllUsers();
        System.out.println("List of all persisted Users >>>");
        for (User curUser : users) {
            System.out.println("Persisted User :" + curUser);
        }

        /*
         * Update an object
         */
        application.updateUser(userId1, "ARTS");

        /*
         * Deletes an object
         */
        application.deleteUser(userId1);

        /*
         * Retrieve all saved objects
         */
        List<User> remaingUsers = application.getAllUsers();
        System.out.println("List of all remained persisted Users >>>");
        for (User User : remaingUsers) {
            System.out.println("Persisted User :" + User);
        }

    }

    /**
     * This method saves a User object in database
     */
    public int saveUser(String p_login) {
        User user = new User();
        user.setLogin(p_login);
        user.setPass("fake_user");
        user.setEloranking(1500);
        user.setHorodatage(new Date());
        user.setIdIcone(1);
        user.setIsABot(0);
        user.setLoses(0);
        user.setWins(0);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        int id = (Integer) session.save(user);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    /**
     * This method returns list of all persisted User objects/tuples from
     * database
     */
    public List<User> getAllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) session.createQuery(
                "FROM User u ORDER BY u.horodatage DESC").list();

        session.getTransaction().commit();
        session.close();
        return users;
    }

    /**
     * This method updates a specific User object
     */
    public void updateUser(int id, String p_password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        User User = (User) session.get(User.class, id);
        User.setPass(p_password);
        //session.update(User);//No need to update manually as it will be updated automatically on transaction close.
        session.getTransaction().commit();
        session.close();
    }

    /**
     * This method deletes a specific User object
     */
    public void deleteUser(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        User User = (User) session.get(User.class, id);
        session.delete(User);
        session.getTransaction().commit();
        session.close();
    }

    public User getUserByLogin(String login) {
        User result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> users = session.createQuery(
                "FROM User u WHERE u.login = :login").setString("login", login).list();
        session.close();

        if(users!=null && users.size()==1) {
            result = users.get(0);
        }

        return result;
    }
}
