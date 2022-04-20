package com.darwinistic;

import java.io.IOException;
import java.util.Random;
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
public class BinaryMessageServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6665535266205134217L;
	private static final Logger LOG = MessageExchange.LOG;
    MessageType randomMessageType = MessageType.BINARY;
    byte[] binaryData = new byte[32];

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Random rnd = new Random();
        Gson gson = new Gson();

        Message msg;

        // generate random bytes for binary messsage
        LOG.info("Generating random bytes");
        rnd.nextBytes(binaryData);
        LOG.info("Creating BINARY message");
        msg = new Message(binaryData);
        LOG.info("Serializing BINARY message");
        String msgJSON = gson.toJson(msg);

        LOG.info("Sending BINARY message to client");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(msgJSON);
    }
}
