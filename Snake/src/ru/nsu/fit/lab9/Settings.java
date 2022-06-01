package ru.nsu.fit.lab9;

public class Settings {
    private boolean mode;
    private double speed;
    private byte traps;
    private static Settings instance;

    private Settings() {
        mode = true; //default zen mode
        speed = 3 / 10.0;
        traps = 0;
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public boolean getMode() {
        return !mode;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public byte getTraps() {
        return traps;
    }

    public void setTraps(byte traps) {
        this.traps = traps;
    }
}
