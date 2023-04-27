public class Character {
    private String name;
    private String domain;
    private String specialization;
    private String home;
    private int hp;
    private int energy;
    private int lf;

    public Character(String name, String domain, String specialization, String home, int hp, int energy, int lf) {
        this.name = name;
        this.domain = domain;
        this.specialization = specialization;
        this.home = home;
        this.hp = hp;
        this.energy = energy;
        this.lf = lf;
    }

    // getters and setters for all attributes
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

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getLf() {
        return lf;
    }

    public void setLf(int lf) {
        this.lf = lf;
    }
}
