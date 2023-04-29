package perfection;


import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;




public class pChar {
    private String name;
    private String domain;
    private String specialization;
    private String home;
    private int hp;
    private int maxHp; // added field for maximum HP
    private int energy;
    private int maxEnergy; // added field for maximum energy
    private int lf;

    // added constructor that initializes all fields
    public pChar(String name, String domain, String specialization, String home, int hp, int maxHp, int energy, int maxEnergy, int lf) {
        this.name = name;
        this.domain = domain;
        this.specialization = specialization;
        this.home = home;
        this.hp = hp;
        this.maxHp = maxHp;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.lf = lf;
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

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
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
        return this.password;
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
        perfection.pChar CharPlayer = new perfection.pChar();
        CharPlayer.setName(name);
        CharPlayer.setHp(1000);
        CharPlayer.setEnergy(1000);
        CharPlayer.setLf(1000);

        // Prompt user to set a password
        System.out.println("Enter a password for your character:");
        String password = scanner.nextLine();
        CharPlayer.setPassword(password);

        // Prompt user to set a domain
        System.out.println("Choose a domain for your character:");
        System.out.println("1. Air\n2. Earth\n3. Fire\n4. Water");
        int domainChoice = scanner.nextInt();
        switch (domainChoice) {
            case 1:
                CharPlayer.setDomain("Air");
                break;
            case 2:
                CharPlayer.setDomain("Earth");
                break;
            case 3:
                CharPlayer.setDomain("Fire");
                break;
            case 4:
                CharPlayer.setDomain("Water");
                break;
            default:
                System.out.println("Invalid choice. Setting default domain to Air.");
                CharPlayer.setDomain("Air");
                break;
        }

        // Prompt user to set a specialization
        System.out.println("Choose a specialization for your character:");
        System.out.println("1. Warrior\n2. Mage\n3. Rogue\n4. Priest");
        int specializationChoice = scanner.nextInt();
        switch (specializationChoice) {
            case 1:
                CharPlayer.setSpecialization("Warrior");
                break;
            case 2:
                CharPlayer.setSpecialization("Mage");
                break;
            case 3:
                CharPlayer.setSpecialization("Rogue");
                break;
            case 4:
                CharPlayer.setSpecialization("Priest");
                break;
            default:
                System.out.println("Invalid choice. Setting default specialization to Warrior.");
                CharPlayer.setSpecialization("Warrior");
                break;
        }

        // Save the new character to a file
        try {
            PrintWriter writer = new PrintWriter(name + ".character", "UTF-8");
            writer.println("Name: " + CharPlayer.getName());
            writer.println("Password: " + CharPlayer.getPassword());
            writer.println("Domain: " + CharPlayer.getDomain());
            writer.println("Specialization: " + CharPlayer.getSpecialization());
            writer.println("Hp: " + CharPlayer.getHp());
            writer.println("Energy: " + CharPlayer.getEnergy());
            writer.println("Lf: " + CharPlayer.getLf());
            // Add other fields as needed
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving character file.");
        }

        return CharPlayer;
    }

}
