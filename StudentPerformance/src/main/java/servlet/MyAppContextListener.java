package servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

@WebListener
public class MyAppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // No action needed on context initialization
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
        	 AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
