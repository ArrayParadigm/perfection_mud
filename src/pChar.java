package perfection;


import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

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

    public pChar createpChar(String name) {
        Scanner scanner = new Scanner(System.in);
        pChar charPlayer = new pChar(name, "", "", 10001, 1000, 1000, 1000, 1000, 1000, ""); // create a new pChar with default values
        charPlayer.setName(name);
        charPlayer.setHp(1000);
        charPlayer.setEnergy(1000);
        charPlayer.setLf(1000);

        // Prompt user to set a password
        System.out.println("Enter a password for your character:");
        String password = scanner.nextLine();
        charPlayer.setPassword(password);

        // Prompt user to set a domain
        System.out.println("Choose a domain for your character:");
        System.out.println("1. Air\n2. Earth\n3. Fire\n4. Water");
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
        System.out.println("Choose a specialization for your character:");
        System.out.println("1. Warrior\n2. Mage\n3. Rogue\n4. Priest");
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
                System.out.println("Invalid choice. Setting default specialization to Warrior.");
                charPlayer.setSpecialization("Warrior");
                break;
        }

        // Prompt user to set a home zone
        System.out.println("Choose a home zone for your character:");
        System.out.println("1. Forest\n2. Mountains\n3. Plains\n4. Coast");
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

}
