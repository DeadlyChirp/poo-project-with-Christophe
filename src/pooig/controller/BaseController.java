package pooig.controller;

import pooig.util.Settings;

public abstract class BaseController {

    protected Settings settings;

    public BaseController() {
        settings = new Settings();
    }

    public abstract void start();

    public abstract void placeTile(int x, int y);

    public abstract void skipTile();

    public Settings getSettings() {
        return settings;
    }
}
