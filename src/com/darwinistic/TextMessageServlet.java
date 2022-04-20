package com.darwinistic;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Andi Reinbrech
 */
public class TextMessageServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2417881445339524700L;

	private static final Logger LOG = MessageExchange.LOG;
    
    MessageType randomMessageType = MessageType.TEXT;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();

        Message msg;
        LOG.info("Creating TEXT message");
        msg = new Message(LoremIpsumParagraph.getRandomParagraph(32));
        LOG.info("Serializing TEXT message");
        String msgJSON = gson.toJson(msg);

        LOG.info("Sending TEXT message to client");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(msgJSON);
    }
}
