/*
ArrayParadigm/Kevin Quinn
04/27/2023 10:00AM CST
Updated: 04/27/2023 10:00AM CST
pChar Class
*/

package perfection;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class pChar {
    private String name;
    private String domain;
    private String specialization;
    private int home;
    private int hp;
    private int maxHp; // added field for maximum HP
    private int energy;
    private int maxEnergy; // added field for maximum energy
    private int lf;
    private String password;

    // added constructor that initializes all fields
    public pChar(String name, String domain, String specialization, int home, int hp, int maxHp, int energy, int maxEnergy, int lf, String pw) {
        this.name = name;
        this.domain = domain;
        this.specialization = specialization;
        this.home = home;
        this.hp = hp;
        this.maxHp = maxHp;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.lf = lf;
        password = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxHp() { // added getter for maximum HP
        return maxHp;
    }

    public void setMaxHp(int maxHp) { // added setter for maximum HP
        this.maxHp = maxHp;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getMaxEnergy() { // added getter for maximum energy
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) { // added setter for maximum energy
        this.maxEnergy = maxEnergy;
    }

    public int getLf() {
        return lf;
    }

    public void setLf(int lf) {
        this.lf = lf;
    }
    public boolean checkPassword(String password) {

        return this.password.equals(password);
    }
    private static String readInput(BufferedReader in, PrintWriter out, String prompt) {
        out.println(prompt);
        String input = null;
        try {
            input = in.readLine();
        } catch (IOException e) {
            System.err.println("Error reading client input: " + e.getMessage());
        }
        return input;
    }


    public pChar createpChar(String name, Socket clientSocket) {
//        Scanner scanner = new Scanner(System.in); // Unused.
        Scanner scanner = null;
        BufferedReader in = null;
        PrintWriter out = null;
        pChar charPlayer = new pChar(name, "", "", 10001, 1000, 1000, 1000, 1000, 1000, ""); // create a new pChar with default values
        charPlayer.setName(name);
        charPlayer.setHp(1000);
        charPlayer.setEnergy(1000);
        charPlayer.setLf(1000);

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            scanner = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("Error getting client input/output stream: " + e.getMessage());
            return null;
        }

        // Prompt user to set a password
//        out.println("Enter a password for your character:");
        String password = readInput(in, out, "Enter a password for your character:");

//        String password = scanner.nextLine();
        charPlayer.setPassword(password);
        out.println("Your current password is: " + charPlayer.password);
        // Prompt user to set a domain

        out.println("Choose a domain for your character:");
        out.println("1. Air\n2. Earth\n3. Fire\n4. Water");
        int domainChoice = scanner.nextInt();
        switch (domainChoice) {
            case 1:
                charPlayer = new pChar(name, "Air", null, 10001, 1000, 1000, 1000, 1000, 1000, password);
                break;
            case 2:
                charPlayer = new pChar(name, "Earth", null, 10001, 1000, 1000, 1000, 1000, 1000, password);
                break;
            case 3:
                charPlayer = new pChar(name, "Fire", null, 10001, 1000, 1000, 1000, 1000, 1000, password);
                break;
            case 4:
                charPlayer = new pChar(name, "Water", null, 10001, 1000, 1000, 1000, 1000, 1000, password);
                break;
            default:
                System.out.println("Invalid choice. Setting default domain to Air.");
                charPlayer = new pChar(name, "Air", null, 10001, 1000, 1000, 1000, 1000, 1000, password);
                break;
        }

        // Prompt user to set a specialization
       out.println("Choose a specialization for your character:");
        out.println("1. Warrior\n2. Mage\n3. Rogue\n4. Priest");
        int specializationChoice = scanner.nextInt();
        switch (specializationChoice) {
            case 1:
                charPlayer.setSpecialization("Warrior");
                break;
            case 2:
                charPlayer.setSpecialization("Mage");
                break;
            case 3:
                charPlayer.setSpecialization("Rogue");
                break;
            case 4:
                charPlayer.setSpecialization("Priest");
                break;
            default:
                out.println("Invalid choice. Setting default specialization to Warrior.");
                charPlayer.setSpecialization("Warrior");
                break;
        }

        // Prompt user to set a home zone
        out.println("Choose a home zone for your character:");
        out.println("1. Forest\n2. Mountains\n3. Plains\n4. Coast");
        int homeChoice = scanner.nextInt();
        scanner.nextLine(); // consume the newline character left by nextInt()
        switch (homeChoice) {
            case 1:
                charPlayer.setHome(10001);
                break;
            case 2:
                charPlayer.setHome(10001);
                break;
            case 3:
                charPlayer.setHome(10001);
                break;
            case 4:
                charPlayer.setHome(10001);
                break;
            default:
                System.out.println("Invalid choice. Setting default home zone to Forest.");
                charPlayer.setHome(10001);
                break;
        }

        // Save the new character to a file
        try {
            PrintWriter writer = new PrintWriter(name + ".character", "UTF-8");
            writer.println("Name: " + charPlayer.getName());
            writer.println("Password: " + charPlayer.getPassword());
            writer.println("Domain: " + charPlayer.getDomain());
            writer.println("Specialization: " + charPlayer.getSpecialization());
            writer.println("Max Hp: " + charPlayer.getMaxHp());
            writer.println("Max Energy: " + charPlayer.getMaxEnergy());
            writer.println("Hp: " + charPlayer.getHp());
            writer.println("Energy: " + charPlayer.getEnergy());
            writer.println("Lf: " + charPlayer.getLf());
            // Add other fields as needed
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving character file.");
        }

        return charPlayer;
    }
    public void loadFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // Skip the first line (password)
        reader.readLine();
        setName(reader.readLine());
        setDomain(reader.readLine());
        setSpecialization(reader.readLine());
        setHp(Integer.parseInt(reader.readLine()));
        setEnergy(Integer.parseInt(reader.readLine()));
        setLf(Integer.parseInt(reader.readLine()));
        setMaxEnergy(Integer.parseInt(reader.readLine()));
        setMaxHp(Integer.parseInt(reader.readLine()));
        reader.close();
    }


}
