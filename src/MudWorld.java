package perfection;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MudWorld {

    private Map<String, MudClientHandler> clients = new HashMap<>();
    private Map<String, pChar> characters;
//    private Map<String, MudZone> zones = new HashMap<>();
    private List<MudZone> zones;
    private Map<String, Player> players = new HashMap<>();

    public MudWorld() {
        this.zones = new ArrayList<>();
        this.characters = new HashMap<>();
        loadZones();
    }

    public void removeZone(MudZone zone) {
        zones.remove(zone);
    }

    public void addZone(MudZone zone) {
        zones.add(zone);
    }

    public MudRoom getRoomById(int id) {
        for (MudZone zone : zones) {
            MudRoom room = zone.getRoomById(id);
            if (room != null) {
                return room;
            }
        }
        return null;
    }

    public MudRoom getStartingRoom() {
        // Just returns the first room of the first zone
        MudZone startingZone = zones.get(0);
        return startingZone.getRooms().get(0);
    }

    public MudRoom findRoom(String name) {
        for (MudZone zone : zones) {
            MudRoom room = zone.findRoom(name);
            if (room != null) {
                return room;
            }
        }
        return null;
    }

    private void loadZones() {
        String directoryPath = "C:\\Users\\Array\\Projects\\perfection\\src\\";
        try {
            List<String> zoneNames = FileUtils.readLines(new File(directoryPath + "map.lst"), StandardCharsets.UTF_8);
            for (String zoneName : zoneNames) {
                MudZone zone = new MudZone(zoneName);
                zone.loadRooms(directoryPath);
                zones.add(zone);
            }
        } catch (IOException e) {
            System.err.println("Error loading zones: " + e.getMessage());
        }
    }


    public pChar loadpChar(String name, String password) {
        if (!checkPassword(name, password)) {
            throw new RuntimeException("Incorrect password");
        }

        pChar pChar = characters.get(name);
        if (pChar == null) {
            throw new RuntimeException("Character not found: " + name);
        }

        return pChar;
    }

    public void createpChar(String name, String domain, String specialization, String home) {
        pChar pChar = new pChar();
        pChar.setName(name);
        pChar.setDomain(domain);
        pChar.setSpecialization(specialization);
        pChar.setHome(home);
        pChar.setHp(1000);
        pChar.setEnergy(1000);
        pChar.setLf(1000);
        characters.put(name, pChar);
    }

    private boolean checkPassword(String name, String password) {
        // logic to check the password goes here
        return true;
    }

    public synchronized void addClient(String name, MudClientHandler handler) {
        clients.put(name, handler);
    }

    public synchronized void removeClient(String name) {
        clients.remove(name);
    }

    public synchronized void broadcast(String message, MudClientHandler sender) {
        String fullMessage = sender.getName() + " says: " + message;
        for (MudClientHandler client : clients.values()) {
            if (client.getName().equals(sender.getName())) {
                continue;
            }
            if (client.getLocation().equals(sender.getLocation())) {
                client.sendMessage(fullMessage);
            }
        }
    }



    public String processCommand(String command, MudClientHandler sender) {
        String[] tokens = command.split(" ");
        String commandName = tokens[0].toLowerCase();
        switch (commandName) {
            case "say":
                return processSayCommand(tokens, sender);
            case "look":
                return processLookCommand(sender);
            case "move":
                return processMoveCommand(tokens, sender);
            case "score":
                return processScoreCommand();
            // add more cases for other commands
            default:
                return "Unknown command: " + commandName;
        }
    }

    private String processSayCommand(String[] tokens, MudClientHandler sender) {
        if (tokens.length < 2) {
            return "Say what?";
        }
        String message = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
        broadcast(message, sender);
        return "You say: " + message;
    }

    private String processLookCommand(MudClientHandler sender) {
        // TODO: implement logic for processing "look" command
        return "You look around.";
    }

    private String processMoveCommand(String[] tokens, MudClientHandler sender) {
        // TODO: implement logic for processing "move" command
        return "You move.";
    }

    public Player getPlayer(String name) {
        return players.get(name);
    }

    private String processScoreCommand() {
        // Get the current player's character file
        perfection.pChar playerChar = currentPlayer.getCharacter();

        // Build a string with the character's information
        StringBuilder scoreBuilder = new StringBuilder();
        scoreBuilder.append("Name: ").append(playerChar.getName()).append("\n");
        scoreBuilder.append("Domain: ").append(playerChar.getDomain()).append("\n");
        scoreBuilder.append("Specialization: ").append(playerChar.getSpecialization()).append("\n");
        scoreBuilder.append("Home: ").append(playerChar.getHome()).append("\n");
        scoreBuilder.append("HP: ").append(playerChar.getHp()).append("/");//.append(playerChar.getMaxHealth()).append("\n");
        scoreBuilder.append("Energy: ").append(playerChar.getEnergy()).append("/").append(playerChar.getMaxMana()).append("\n");
//        scoreBuilder.append("Experience: ").append(playerChar.getCurrentExp()).append("/").append(playerChar.getNextLevelExp()).append("\n");
//        scoreBuilder.append("Gold: ").append(playerChar.getGold()).append("\n");

        // Return the character's information as a string
        return scoreBuilder.toString();
    }


}