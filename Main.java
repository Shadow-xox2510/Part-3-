/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;

/**
 *
 *
 * 
 * 
*/
import java.util.Scanner;
import com.mycompany.main.Login;
import com.mycompany.main.Message;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();

        System.out.println("=== Registration ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell number: ");
        String cellnumber = scanner.nextLine();

        boolean registered = loginSystem.register(username, password, cellnumber);
        if (!registered) {
            System.out.println("Registration failed.");
            scanner.close();
            return;
        }

        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        if (loginSystem.login(loginUsername, loginPassword)) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed.");
            scanner.close();
            return;
        }

        // === Part 3: Message Handling ===
        System.out.println("\n=== Message Handling ===");
        System.out.print("Enter number of messages to send: ");
        int numMessages = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numMessages; i++) {
            System.out.println("\nMessage " + (i + 1));
            System.out.print("Enter recipient cell number: ");
            String recipientCell = scanner.nextLine();

            System.out.print("Enter message content: ");
            String content = scanner.nextLine();

            Message msg = new Message(recipientCell, content);

            if (msg.checkRecipientCell() && msg.checkMessageID()) {
                msg.storeMessage();
                System.out.println("Message stored successfully!");
            } else {
                System.out.println("Invalid message details. Message not stored.");
            }
        }

        // Display stored messages
        System.out.println("\n=== All Messages ===");
        for (Message m : Message.getAllMessages()) {
            System.out.println("Message ID: " + m.getMessageID());
            System.out.println("Recipient Cell: " + m.getRecipientCell());
            System.out.println("Content: " + m.getContent());
            System.out.println("Hash: " + m.getMessageHash());
            System.out.println("----------------------------");
        }

        System.out.println("Total messages stored: " + Message.returnTotalMessages());

        // Print JSON
        System.out.println("\n=== JSON Format of Messages ===");
        System.out.println(Message.toJSON());

        scanner.close();
    }
}
