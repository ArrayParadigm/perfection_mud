/*
ArrayParadigm/Kevin Quinn
04/27/2023 10:00AM CST
Updated: 04/27/2023 10:00AM CST
MudServer Class
*/

package perfection;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class MudServer {
    private ServerSocket serverSocket;
    private MudWorld mudWorld;
    private Map<String, pChar> characters;

    public MudServer(int port, MudWorld mudWorld) {
        try {
            serverSocket = new ServerSocket(port);
            this.mudWorld = mudWorld;
            characters = mudWorld.getCharacters();
            System.out.println("MUD server started on port " + port);
        } catch (IOException e) {
            System.err.println("Could not start MUD server on port " + port);
            System.exit(1);
        }
    }

    public pChar loadpChar(String name, String password, Socket clientSocket) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            pChar character = characters.get(name);
            if (character == null) {
                // If character doesn't exist, create a new one
                String newPassword = askNewPassword(out);
                pChar newCharacter = new pChar(name, "defaultDomain", "defaultSpecialization", 10001, 100, 100, 100, 100, 100, newPassword);
                characters.put(name, newCharacter);
                savePlayer(newCharacter);
                return newCharacter;
            } else {
                // Check if password is correct by reading from the character file
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

    private String askNewPassword(PrintWriter writer) {
        writer.println("Welcome! Since this is your first time here, please choose a password for your character:");
        writer.flush();

        // Prompt for password and read from input
        // You can implement this based on your existing code structure
        // Example: Use BufferedReader to read password from clientSocket.getInputStream()

        // Return the entered password
        // Modify this according to your implementation
        return "newPassword";
    }

    private void savePlayer(pChar player) {
        // Save the player data to a player file
        // You can implement the logic for saving player data here
        // Example: Use a file writer to save the player data to a file
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
        MudServer server = new MudServer(port, mudWorld);
        server.run();
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