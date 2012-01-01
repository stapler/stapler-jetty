package org.kohsuke.stapler.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.WebApp;

/**
 * Runs Stapler app with Jetty.
 *
 * @author Kohsuke Kawaguchi
 */
public class JettyRunner {
    private Server server;

    /**
     * @param app
     *      The app object that handles the root of the URL.
     */
    public JettyRunner(Object app) {
        server = new Server();

        ServletContextHandler root = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        root.addServlet(new ServletHolder(new Stapler()), "/*");

        WebApp.get(root.getServletContext()).setApp(app);
    }
    
    public JettyRunner addHttpListener(int port) {
        Connector http=new SocketConnector();
        http.setPort(8080);
        server.addConnector(http);
        return this;
    }
    
    public void start() throws Exception {
        server.start();
    }
}
