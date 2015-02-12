package org.jorgechato.fightclub.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static Session session;

	public static void buildSessionFactory() {

        Root root = new Root();
        root.setVisible(true);

        Configuration configuration = new Configuration();
        configuration.configure().setProperty("hibernate.connection.username",root.NAME).setProperty("hibernate.connection.password", root.PASS);

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
						configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public static void openSession() {
		
		session = sessionFactory.openSession();
	}

	public static Session getCurrentSession() {
		
		if ((session == null) || (!session.isOpen()))
			openSession();
			
		return session;
	}

	public static void closeSessionFactory() {
		
		if (session != null)
			session.close();
		
		if (sessionFactory != null)
			sessionFactory.close();
	}
}
