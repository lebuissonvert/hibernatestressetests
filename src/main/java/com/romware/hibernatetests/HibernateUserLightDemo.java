package com.romware.hibernatetests;

import com.romware.hibernatetests.model.UserLight;
import com.romware.hibernatetests.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class HibernateUserLightDemo {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        HibernateUserLightDemo application = new HibernateUserLightDemo();
        UserLight userLight = application.getUserLightByLogin("Romain");
        System.out.println(userLight.toString());
    }

    public UserLight getUserLightByLogin(String login) {
        UserLight result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<UserLight> usersLight = session.createQuery(
                "FROM UserLight u WHERE u.login = :login").setString("login", login).list();
        session.close();

        if(usersLight!=null && usersLight.size()==1) {
            result = usersLight.get(0);
        }

        return result;
    }
}
