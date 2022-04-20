package com.darwinistic;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

/**
 *
 * @author Andi Reinbrech
 */
public class Message {
    public Message() {
        this.timeStamp = Instant.ofEpochSecond(0L).until(Instant.now(), ChronoUnit.MILLIS);
        this.uuid = UUID.randomUUID();
    }
    
    public Message(String payload) {
        this();
        this.type = MessageType.TEXT;
        this.payload = payload;
        this.length = payload.length();
    }
    
    public Message(byte[] payload) {
        this();
        this.type = MessageType.BINARY;
        this.payload = Base64.getEncoder().encodeToString(payload);
        this.length = payload.length;
    }
    
    private UUID uuid;
    private MessageType type;
    private long timeStamp;
    private int length;
    private String payload;
    
    public String getUUID() {
        return uuid.toString();
    }
    
    public int getLength() {
        return length;
    }
    
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getTimeStamp() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(this.timeStamp), ZoneId.systemDefault()).toString();
    }

    public String getPayload() {
        return payload;
    }
    
    public byte[] getBinaryPayload() throws InvalidMessageTypeException {
        if (this.type == MessageType.BINARY) {
            return Base64.getDecoder().decode(payload);
        } else {
            throw new InvalidMessageTypeException("Not a binary message, use getPayload()");
        }
    }
    
}
