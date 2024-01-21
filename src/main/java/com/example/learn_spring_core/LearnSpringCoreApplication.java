package com.example.learn_spring_core;

import com.example.learn_spring_core.component.RequestResponseLoggingFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class LearnSpringCoreApplication {
    public static void main(String[] args) throws Exception {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.example.learn_spring_core.configuration");

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setErrorHandler(null);

        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/*");
        contextHandler.addFilter(RequestResponseLoggingFilter.class, "/*", null);
        Server server = new Server(8080);
        server.setHandler(contextHandler);
        server.start();
        server.join();
    }
}

