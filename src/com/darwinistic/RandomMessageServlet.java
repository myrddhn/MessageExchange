package com.darwinistic;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

/**
 *
 * @author Andi Reinbrech
 */
public class RandomMessageServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4066096411780861928L;

	private static final Logger LOG = MessageExchange.LOG;

    MessageType randomMessageType = MessageType.BINARY;
    byte[] binaryData = new byte[32];

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Random rnd = new Random();
        Gson gson = new Gson();

        Message msg;

        boolean randomBool = rnd.nextBoolean();
        randomMessageType = (randomBool ? MessageType.BINARY : MessageType.TEXT);

        LOG.info("Creating random message");
        if (randomMessageType == MessageType.BINARY) {
            LOG.info("Random message is BINARY");
            // generate random bytes for binary messsage
            rnd.nextBytes(binaryData);
            msg = new Message(binaryData);
        } else {
            LOG.info("Random message is TEXT");
            // Message type will be determined automatically when instantiated
            msg = new Message(LoremIpsumParagraph.getRandomParagraph(32));
        }
        LOG.info("Serializing random messsage");

        String msgJSON = gson.toJson(msg);

        LOG.info("Sending random message to client");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(msgJSON);
    }
}
