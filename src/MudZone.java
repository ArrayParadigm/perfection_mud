package perfection;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import perfection.*;

import org.apache.commons.io.FileUtils;

public class MudZone {
    private final String name;
    private final List<MudRoom> rooms;

    public MudZone(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<MudRoom> getRooms() {
        return rooms;
    }

    public void loadRooms(String directoryPath) {
        try {
            String zoneFilePath = directoryPath + name + ".zn";
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
                rooms.add(room);
            }
        } catch (IOException e) {
            System.err.println("Error loading rooms for zone " + name + ": " + e.getMessage());
        }
    }

    public MudRoom getRoomById(int id) {
        for (MudRoom room : rooms) {
            if (room.getNumber() == id) {
                return room;
            }
        }
        return null;
    }

    public MudRoom findRoom(int vnum) {
        for (MudRoom room : rooms) {
            if (room.getName().equals(vnum)) {
                return room;
            }
        }
        return null;
    }

}

