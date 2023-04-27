package perfection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.FileUtils;


public class MudWorld {
    
    private Map<String, MudClientHandler> clients = new HashMap<>();
    private Map<String, MudZone> zones = new HashMap<>();

    public MudWorld() {
        loadZones();
    }

    private void loadZones() {
        String directoryPath = "C:\\Users\\Array\\Projects\\perfection\\src\\";
        try {
            List<String> zoneNames = FileUtils.readLines(new File(directoryPath + "map.lst"), StandardCharsets.UTF_8);
            for (String zoneName : zoneNames) {
                MudZone zone = new MudZone(zoneName);
                String zoneFilePath = directoryPath + zoneName + ".zn";
                List<String> lines = FileUtils.readLines(new File(zoneFilePath), StandardCharsets.UTF_8);
                for (String line : lines) {
                    String[] parts = line.split("\\|");
                    if (parts.length != 3) {
                        System.err.println("Invalid line format: " + line);
                        continue;
                    }
                    int roomNum = Integer.parseInt(parts[0]);
                    String roomName = parts[1];
                    String roomDesc = parts[2];
                    MudRoom room = new MudRoom(roomNum, roomName, roomDesc);
                    zone.addRoom(room);
                }
                zones.put(zoneName, zone);
            }
        } catch (IOException e) {
            System.err.println("Error loading zones: " + e.getMessage());
        }
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
}
