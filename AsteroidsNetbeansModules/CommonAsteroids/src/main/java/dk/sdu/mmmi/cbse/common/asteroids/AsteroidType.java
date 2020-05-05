package dk.sdu.mmmi.cbse.common.asteroids;

public enum AsteroidType {

    LARGE("LARGE"),
    MEDIUM("MEDIUM"),
    SMALL("SMALL");

    private String size;

    private AsteroidType(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
