package com.tobiasmaneschijn.demoGame;

import com.tobiasmaneschijn.core.*;
import com.tobiasmaneschijn.demoGame.entities.PlayerEntity;
import com.tobiasmaneschijn.demoGame.entities.RotatingEntity;

public class DemoGame extends Game {

    private static DemoGame instance;

    public static DemoGame get(){
        if( instance != null) return instance;
        instance = new DemoGame();
        return  instance;
    }

    private Entity player;

    public DemoGame() {
        super();
        setGameTitle("Demo Game");


    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void initialise() {
        super.initialise();
        player = new PlayerEntity();
        player.setPosition(20, getGameWindow().getHeight() - 100);
        addEntity(player);
        addEntity(new RotatingEntity());
    }
    
    @Override
    public void draw() {
        super.draw();
    }

}
