package com.tobiasmaneschijn.demoGame;

import com.tobiasmaneschijn.core.*;
import com.tobiasmaneschijn.demoGame.entities.EnemyEntity;
import com.tobiasmaneschijn.demoGame.entities.PlayerEntity;
import com.tobiasmaneschijn.demoGame.entities.RotatingEntity;

import java.util.ArrayList;

public class DemoGame extends Game {

    private static DemoGame instance;
    private int enemyCount = 10;

    private ArrayList<EnemyEntity> enemies = new ArrayList<>();

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
        addEnemies();
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void destroyEntity(Entity e) {
        super.destroyEntity(e);
        enemies.remove(e);
    }

    private void addEnemies(){
        for (int i = 0; i < enemyCount; i++) {
            EnemyEntity enemy = new EnemyEntity();
            enemies.add(enemy);
            enemy.setPosition(-100f * i, 200);
            addEntity(enemy);
        }
    }

}
