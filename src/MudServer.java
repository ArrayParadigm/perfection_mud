/*
ArrayParadigm/Kevin Quinn
04/27/2023 10:00AM CST
Updated: 04/27/2023 10:00AM CST
MudServer Class
*/

package perfection;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MudServer {
    private ServerSocket serverSocket;
    private MudWorld mudWorld;
    private Map<String, pChar> characters;

    public MudServer(int port, MudWorld mudWorld, Map<String, pChar> characters) {
        try {
            serverSocket = new ServerSocket(port);
            this.mudWorld = mudWorld;
            this.characters = characters; // Initialize with provided map
            System.out.println("MUD server started on port " + port);
        } catch (IOException e) {
            System.err.println("Could not start MUD server on port " + port);
            System.exit(1);
        }
    }

    public pChar loadpChar(String name, String password, Socket clientSocket) {
        try {
            System.out.println("Loading character: " + name);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//            pChar character = characters.get(name);
            pChar character = mudWorld.getCharacters().get(name);
//            pChar character = mudWorld.characters.get(name);
//            character = new pChar(name, "1", "1", 1, 1, 1, 1, 1, 1, password);
            System.out.println("Character name is " + character);
            if (character == null) {
                System.out.println("Character not found. Creating a new one.");
                // If character doesn't exist, create a new one
                pChar pchar = new pChar(name, "defaultDomain", "defaultSpecialization", 10001, 100, 100, 100, 100, 100, password);
                character = pchar.createpChar(name, clientSocket);
                characters.put(name, character);
            } else {
                System.out.println("Character found. Checking password");
                // Check if password is correct by reading from character file
                File file = new File(name + ".character");
                if (file.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String storedPassword = reader.readLine();
                        if (!character.checkPassword(password)) {
                            out.println("Incorrect password! Please try again.");
                            clientSocket.close();
                            return null;
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading character file: " + e.getMessage());
                        clientSocket.close();
                        return null;
                    }
                } else {
                    out.println("Character file not found!");
                    clientSocket.close();
                    return null;
                }
            }
            return character;
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
            return null; // Return null to indicate that an error occurred
        }
    }

    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress().getHostName());
                MudClientHandler clientHandler = new MudClientHandler(clientSocket, mudWorld, this);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                System.err.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        MudWorld mudWorld = new MudWorld();
        Map<String, pChar> characters = mudWorld.getCharacters(); // Retrieve characters map
        MudServer server = new MudServer(port, mudWorld, characters); // Pass the characters map
        server.run();
    }
    public void setCharacters(Map<String, pChar> characters) {
        this.characters = characters;
    }

}

class MudClientHandler implements Runnable {
    private Socket clientSocket;
    private MudWorld mudWorld;
    private String name;
    private String location = "";
    private BufferedReader in;
    private PrintWriter out;
    private MudServer mudServer;

    public MudClientHandler(Socket clientSocket, MudWorld mudWorld, MudServer mudServer) {

        this.clientSocket = clientSocket;
        this.mudWorld = mudWorld;
        this.mudServer = mudServer;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error creating client handler: " + e.getMessage());
        }
    }

    public void run() {
        try {
            String password = "";
            out.println("Welcome to the MUD! What is your name?");
            String input = in.readLine();
            if (input == null) {
                return;
            }
            name = input;
            pChar player = mudServer.loadpChar(name, password, clientSocket); // Load or create a character
            if (player == null) {
                return; // If player is null, the connection has been closed
            }
            mudWorld.addClient(name, this);
            out.println("Hello, " + name + "! You are in " + location);
            while (true) {
                input = in.readLine();
                if (input == null) {
                    break;
                }
                String output = mudWorld.processCommand(input, this);
                out.println(output);
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                mudWorld.removeClient(name);
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}