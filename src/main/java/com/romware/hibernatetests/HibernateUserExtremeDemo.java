package com.romware.hibernatetests;

import com.romware.hibernatetests.model.User;
import com.romware.hibernatetests.utils.HibernateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

public class HibernateUserExtremeDemo {

    private static final int SIMULTANEOUS_INSERTS_BY_REQUEST = 200;

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        HibernateUserExtremeDemo application = new HibernateUserExtremeDemo();

        int nbCreations = 50000;
        Session session = application.beginTransaction();

        // Purge précédent jeu de test
        application.deleteUserWithLoginStartingWith("test_", session);

        // Insertion en masse
        //List<Integer> createdIds = application.extremeSaveUser(nbCreations, session);
        application.extremeSaveUserOptim(nbCreations, session);

        application.commitTransaction(session);

        long startTime = System.currentTimeMillis();
        session = application.beginTransaction();
        application.deleteUserWithLoginStartingWith("test_", session);
        application.commitTransaction(session);
        long endTime = System.currentTimeMillis();
        System.out.println(nbCreations + " suppressions en " + (endTime-startTime) + "ms, soit " +
                1000*nbCreations/(endTime-startTime) + " suppressions/sec");

        System.exit(0);
    }

    public List<Integer> extremeSaveUser(int nbCreations, Session session){
        List<Integer> createdIds = new ArrayList<Integer>();

        List<String> loginsToCreate = new ArrayList<String>();
        for(int i=0; i<nbCreations; i++) {
            loginsToCreate.add(DigestUtils.md5Hex(i+"").toUpperCase());
        }

        long startTime = System.currentTimeMillis();
        for(String curLogin : loginsToCreate) {
            createdIds.add(saveUser("test_" + curLogin, session));
        }
        long endTime = System.currentTimeMillis();
        System.out.println(nbCreations + " insertions en " + (endTime-startTime) + "ms, soit " +
                1000*nbCreations/(endTime-startTime) + " insertions/sec");

        return createdIds;
    }

    public void extremeSaveUserOptim(int nbCreations, Session session){
        List<String> loginsToCreate = new ArrayList<String>();
        for(int i=0; i<nbCreations; i++) {
            loginsToCreate.add("test_" + i);
        }

        long startTime = System.currentTimeMillis();
        saveUserOptim(loginsToCreate, session);
        long endTime = System.currentTimeMillis();
        System.out.println(nbCreations + " insertions en " + (endTime-startTime) + "ms, soit " +
                1000*nbCreations/(endTime-startTime) + " insertions/sec");
    }

    /**
     * This method saves a User object in database
     */
    public int saveUser(String p_login, Session session) {
        User user = getUser(p_login);
        int id = (Integer) session.save(user);
        return id;
    }

    public void saveUserOptim(List<String> p_logins, Session session) {
        List<Integer> resultat = new ArrayList<Integer>();
        if(p_logins!=null && !p_logins.isEmpty()) {
            if(p_logins.size() > SIMULTANEOUS_INSERTS_BY_REQUEST) {
                saveUserOptim(p_logins.subList(SIMULTANEOUS_INSERTS_BY_REQUEST, p_logins.size()), session);
                p_logins = p_logins.subList(0, SIMULTANEOUS_INSERTS_BY_REQUEST);
            }
            Query q = session.createSQLQuery("insert into users (id, login, pass, eloranking, " +
                    "wins, loses, isABot, idIcone, horodatage) " + getUsersInfos(p_logins));
            q.executeUpdate();
        }
    }

    public void deleteUser(int id, Session session) {
        User User = (User) session.get(User.class, id);
        session.delete(User);
    }

    public void deleteUserOptim(List<Integer> ids, Session session) {
        if(ids!=null && !ids.isEmpty()) {
            Query q = session.createQuery("delete from User where id in (:idList) ");
            q.setString("idList", StringUtils.join(ids, ","));
            q.executeUpdate();
        }
    }

    public void deleteUserWithLoginStartingWith(String startingLogin, Session session) {
        Query q = session.createSQLQuery("delete from users where login like :startingLogin");
        q.setParameter("startingLogin", startingLogin+"%");
        q.executeUpdate();
    }

    public Session beginTransaction() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        return session;
    }

    public void commitTransaction(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    private String getUsersInfos(List<String> p_logins) {
        String result = "";
        List<User> users = new ArrayList<User>();
        for(String curLogin : p_logins) {
            users.add(getUser(curLogin));
        }
        for(User curUser : users) {
            if(users.indexOf(curUser) == 0) {
                result += " select " + curUser.getConcatenatedFields();
            } else {
                result += " union all select " + curUser.getConcatenatedFields();
            }
        }
        return result;
    }

    private User getUser(String p_login) {
        User user = new User();
        user.setLogin(p_login);
        user.setPass("fake_user");
        user.setEloranking(1500);
        user.setIdIcone(1);
        user.setIsABot(0);
        user.setLoses(0);
        user.setWins(0);
        return user;
    }
}
