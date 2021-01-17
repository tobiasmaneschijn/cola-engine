package com.tobiasmaneschijn.core;

public class Game {

    private GameWindow gameWindow;

    public Game(){

    }
    public void startGame(){
        gameWindow = new GameWindow("Cola Engine", 1920, 1080);

        gameWindow.setTitle("Cola Engine v0.1.0");
        gameWindow.setResolution(1280, 768);

    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }
}
