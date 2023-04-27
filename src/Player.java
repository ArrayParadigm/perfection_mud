/*
ArrayParadigm/Kevin Quinn
04/27/2023 10:00AM CST
Updated: 04/27/2023 10:00AM CST
Player Class
*/

package perfection;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Player {
    private String name;
    private String password;
    private String domain;
    private String specialization;
    private int world;
    private int hp;
    private int energy;

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
        loadPlayerFile();
    }

    public void loadPlayerFile() {
        try {
            File file = new File(this.name + ".txt");
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    switch (parts[0]) {
                        case "Domain":
                            this.domain = parts[1];
                            break;
                        case "Specialization":
                            this.specialization = parts[1];
                            break;
                        case "World":
                            this.world = Integer.parseInt(parts[1]);
                            break;
                        case "HP":
                            this.hp = Integer.parseInt(parts[1]);
                            break;
                        case "Energy":
                            this.energy = Integer.parseInt(parts[1]);
                            break;
                    }
                }
                br.close();
            }
            else {
                System.out.println("Creating new player file...");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your domain: ");
                this.domain = scanner.nextLine();
                System.out.print("Enter your specialization: ");
                this.specialization = scanner.nextLine();
                System.out.print("Enter your starting world: ");
                this.world = scanner.nextInt();
                System.out.print("Enter your starting HP: ");
                this.hp = scanner.nextInt();
                System.out.print("Enter your starting energy: ");
                this.energy = scanner.nextInt();
                scanner.close();
                savePlayerFile();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerFile() {
        try {
            File file = new File(this.name + ".txt");
            FileWriter writer = new FileWriter(file);
            writer.write("Name:" + this.name + "\n");
            writer.write("Domain:" + this.domain + "\n");
            writer.write("Specialization:" + this.specialization + "\n");
            writer.write("World:" + this.world + "\n");
            writer.write("HP:" + this.hp + "\n");
            writer.write("Energy:" + this.energy + "\n");
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    // getter and setter methods for all attributes go here
}
