/*
ArrayParadigm/Kevin Quinn
04/27/2023 10:00AM CST
Updated: 04/27/2023 10:00AM CST
MudWorld Class
*/

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
import perfection.*;



public class MudWorld {

    private Map<String, MudClientHandler> clients = new HashMap<>();
//    pChar character = mudWorld.createpChar("name");
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
/*
    public Map<String, pChar> getCharacters() {
        return characters;
    }
*/
    public MudRoom getStartingRoom() {
        // Just returns the first room of the first zone
        MudZone startingZone = zones.get(0);
        return startingZone.getRooms().get(0);
    }

    public MudRoom findRoom(int vnum) {
        for (MudZone zone : zones) {
            MudRoom room = zone.findRoom(vnum);
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
/*
    public MudRoom getRoomById(int id) {
        for (MudZone zone : zones) {
            MudRoom room = zone.getRoomById(id);
            if (room != null) {
                return room;
            }
        }
        return null;
    }
*/

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
                return null;
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

    private String processScoreCommand(MudClientHandler sender) {
        // Get the current player's character file
/*        pChar playerChar = sender;

        // Build a string with the character's information
        StringBuilder scoreBuilder = new StringBuilder();
        scoreBuilder.append("Name: ").append(playerChar.getName()).append("\n");
        scoreBuilder.append("Domain: ").append(playerChar.getDomain()).append("\n");
        scoreBuilder.append("Specialization: ").append(playerChar.getSpecialization()).append("\n");
        scoreBuilder.append("Home: ").append(playerChar.getHome()).append("\n");
        scoreBuilder.append("HP: ").append(playerChar.getHp());//.append("/");//.append(playerChar.getMaxHealth()).append("\n");
        scoreBuilder.append("Energy: ").append(playerChar.getEnergy());//.append("/").append(playerChar.getMaxMana()).append("\n");
//        scoreBuilder.append("Experience: ").append(playerChar.getCurrentExp()).append("/").append(playerChar.getNextLevelExp()).append("\n");
//        scoreBuilder.append("Gold: ").append(playerChar.getGold()).append("\n");

        // Return the character's information as a string
        return scoreBuilder.toString(); */
        return null;
    }

    // In MudWorld class

    public Map<String, pChar> getCharacters() {
        System.out.println("Characters stuff: " + characters);
        return characters;
    }



}