package perfection;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MudServer {
    private ServerSocket serverSocket;
    private perfection.MudWorld mudWorld;
    Map<String, pChar> characters = mudWorld.getCharacters();


    public MudServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            mudWorld = new perfection.MudWorld();
            System.out.println("MUD server started on port " + port);
        } catch (IOException e) {
            System.err.println("Could not start MUD server on port " + port);
            System.exit(1);
        }
    }
/*
    public pChar createpChar(String name) {
        perfection.pChar CharPlayer = new perfection.pChar();
        CharPlayer.setName(name);
        CharPlayer.setHp(1000);
        CharPlayer.setEnergy(1000);
        CharPlayer.setLf(1000);
        // Set default values for other fields
        return CharPlayer;
    }
*/
/*
    public pChar loadpChar(String name) {
        pChar character = new pChar();
        try {
            File file = new File(name + ".character");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(":");
                if (parts[0].equals("NAME")) {
                    character.setName(parts[1]);
                } else if (parts[0].equals("DOMAIN")) {
                    character.setDomain(parts[1]);
                } else if (parts[0].equals("SPECIALIZATION")) {
                    character.setSpecialization(parts[1]);
                } else if (parts[0].equals("HOME")) {
                    character.setHome(Integer.parseInt(parts[1]));
                } else if (parts[0].equals("HP")) {
                    character.setHp(Integer.parseInt(parts[1]));
                } else if (parts[0].equals("ENERGY")) {
                    character.setEnergy(Integer.parseInt(parts[1]));
                } else if (parts[0].equals("LF")) {
                    character.setLf(Integer.parseInt(parts[1]));
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading character file: " + e.getMessage());
        }
        System.out.println(character);
        return character;
    }
*/
/*
    public perfection.pChar loadpChar(String name, String password) {
        pChar pchar = new pChar(name, "defaultDomain", "defaultSpecialization", 10001, 100, 100, 100, 100, 100, password);
        perfection.pChar character = pchar.createpChar(name);
        if (character == null) {
            // If character doesn't exist, create a new one
            pChar character = pchar.createpChar(name);
//            character = pChar.createpChar(name);//, "defaultDomain", "defaultSpecialization", "defaultHome", 100, 100, 100, 100, 100, password);
        } else if (!character.checkPassword(password)) {
            // If password is incorrect, prompt for password again
            out.println("Incorrect password! Please try again.");
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
            return null; // Return null to indicate that the connection has been closed
        }

        return character;
    }
*/
public perfection.pChar loadpChar(String name, String password) {
    perfection.pChar character = characters.get(name);
    if (character == null) {
        // If character doesn't exist, create a new one
        pChar pchar = new pChar(name, "defaultDomain", "defaultSpecialization", 10001, 100, 100, 100, 100, 100, password);
        character = pchar.createpChar(name);
        characters.put(name, character);
    } else if (!character.checkPassword(password)) {
        // If password is incorrect, prompt for password again
        out.println("Incorrect password! Please try again.");
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
        return null; // Return null to indicate that the connection has been closed
    }

    return character;
}




    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress().getHostName());
                MudClientHandler clientHandler = new MudClientHandler(clientSocket, mudWorld);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                System.err.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        MudServer server = new MudServer(port);
        MudWorld mudWorld = new MudWorld();
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

    public MudClientHandler(Socket clientSocket, MudWorld mudWorld) {

        this.clientSocket = clientSocket;
        this.mudWorld = mudWorld;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error creating client handler: " + e.getMessage());
        }
    }

    public void run() {
        try {
            out.println("Welcome to the MUD! What is your name?");
            String input = in.readLine();
            if (input == null) {
                return;
            }
            name = input;
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