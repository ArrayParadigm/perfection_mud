package perfection;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
//import org.apache.commons.io.FileUtiles;


public class MudZone {
    private String name;
    private List<MudRoom> rooms;

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

    public void addRoom(MudRoom room) {
        rooms.add(room);
    }

    public MudRoom getStartingRoom() {
        return rooms.get(0);
    }
    public MudRoom getRoomById(int id) {
        for (MudRoom room : rooms) {
            if (room.getNumber() == id) {
                return room;
            }
        }
        return null;
    }

    public MudRoom findRoom(String name) {
        for (MudRoom room : rooms) {
            if (room.getName().equalsIgnoreCase(name)) {
                return room;
            }
        }
        return null;
    }

}