package com.mycompany.main;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Zaida
 */
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Message {
    private String messageID;
    private String recipientCell;
    private String content;
    private String messageHash;

    private static List<Message> messages = new ArrayList<>();

    public Message(String recipientCell, String content) {
        this.messageID = generateMessageID();
        this.recipientCell = recipientCell;
        this.content = content;
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public boolean checkRecipientCell() {
        if (recipientCell.startsWith("+27") && recipientCell.length() == 12) {
            return recipientCell.substring(3).matches("[0-9]{9}");
        } else if (recipientCell.startsWith("0") && recipientCell.length() == 10) {
            return recipientCell.substring(1).matches("[0-9]{9}");
        }
        return false;
    }

    private String createMessageHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(content.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating message hash", e);
        }
    }

    public void storeMessage() {
        messages.add(this);
    }

    public static List<Message> getAllMessages() {
        return messages;
    }

    public static int returnTotalMessages() {
        return messages.size();
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipientCell() {
        return recipientCell;
    }

    public String getContent() {
        return content;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public static String toJSON() {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < messages.size(); i++) {
            Message msg = messages.get(i);
            json.append("  {\n");
            json.append("    \"MessageID\": \"").append(msg.messageID).append("\",\n");
            json.append("    \"RecipientCell\": \"").append(msg.recipientCell).append("\",\n");
            json.append("    \"Content\": \"").append(msg.content).append("\",\n");
            json.append("    \"MessageHash\": \"").append(msg.messageHash).append("\"\n");
            json.append("  }");
            if (i < messages.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("]");
        return json.toString();
    }
}

    

