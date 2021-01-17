package com.tobiasmaneschijn.core;

public class Game {

    private GameWindow gameWindow;

    public Game(){

    }
    public void startGame(){
        gameWindow = new GameWindow();

        gameWindow.setTitle("Cola Engine");
        gameWindow.setResolution(1280, 768);

    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }
}
