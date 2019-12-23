package myServer.alice.business.entities.DB;


import myServer.alice.business.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.apache.log4j.Logger;

/**
 * Create and return sessions factory
 */

public class HibernateSessionFactoryUtil {
    private static final Logger log = Logger.getLogger(HibernateSessionFactoryUtil.class);
    private static SessionFactory sessionFactory;
    private HibernateSessionFactoryUtil() {
    }
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Balance.class);
                configuration.addAnnotatedClass(Task.class);
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(Purchase.class);
                configuration.addAnnotatedClass(Book.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                log.error("Error in configuration SessionFactory "+e.getMessage());
            }
        }
        return sessionFactory;
    }
}