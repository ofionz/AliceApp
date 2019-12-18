package myServer.alice.business.entities.DB;


import myServer.alice.business.entities.Balance;
import myServer.alice.business.entities.Product;
import myServer.alice.business.entities.Purchase;
import myServer.alice.business.entities.Task;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
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
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("??????????!" + e);
            }
        }
        return sessionFactory;
    }
}