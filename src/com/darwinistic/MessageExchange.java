package com.darwinistic;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Andi Reinbrech
 */
public class MessageExchange extends AbstractHandler {

    public static final Logger LOG;
    private static boolean running = true;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOG = Logger.getLogger(MessageExchange.class.getName());
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Hello World</h1>");
    }

    private static void printMessage(Message m) {
        LOG.info("====================================================================");
        LOG.log(Level.INFO, "Message ID       : {0}", m.getUUID());
        LOG.log(Level.INFO, "Message timestamp: {0}", m.getTimeStamp());
        LOG.log(Level.INFO, "Message type     : {0}", m.getType());
        try {
            LOG.log(Level.INFO, "Message payload  : {0}", Arrays.toString(Arrays.copyOf(m.getBinaryPayload(), 4)));
        } catch (InvalidMessageTypeException imte) {
            LOG.log(Level.WARNING, " *** Exception: {0} ***", imte.getLocalizedMessage());
            LOG.log(Level.INFO, "Message payload  : {0}", m.getPayload());
        }
        LOG.info("====================================================================");
    }

    public static void main(String[] args) throws Exception {
    	
        if (args.length != 1) {
            System.out.println("Usage: java -jar MessageExchage.jar [--server/--client]");
            System.exit(1);
        }

        new Thread(() -> {
            while (running) {
                int readkey;
                try {
                    readkey = System.in.read();
                    running = (readkey != 10);
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        }).start();

        if (args[0].equals("--server")) {
            Server server = new Server(9999);
            server.setHandler(new MessageExchange());
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/messages");
            server.setHandler(context);
            context.addServlet(new ServletHolder(new RandomMessageServlet()), "/getrandommessage");
            context.addServlet(new ServletHolder(new BinaryMessageServlet()), "/getbinarymessage");
            context.addServlet(new ServletHolder(new TextMessageServlet()), "/gettextmessage");
            server.start();

            while (running) {
                Thread.sleep(10);
            }

            LOG.info("Stopping web server");
            server.stop();
            server.join();
            LOG.info("Done...");
        }

        if (args[0].equals("--client")) {
            try {
                var htc = new HttpClient();
                htc.start();

                while (running) {
                    ContentResponse rs = htc.GET("http://localhost:9999/messages/getrandommessage");
                    Message rsMessage = new Gson().fromJson(rs.getContentAsString(), Message.class);
                    printMessage(rsMessage);

                    Thread.sleep(1500);
                }
                LOG.info("Exiting...");
                htc.stop();
            } catch (InterruptedException e) {
                // Thread.sleep
            }
        }
    }

}
