package perfection;

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
}
