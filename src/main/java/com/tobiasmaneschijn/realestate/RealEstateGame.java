package com.tobiasmaneschijn.realestate;

import com.tobiasmaneschijn.core.Entity;
import com.tobiasmaneschijn.core.Game;
import com.tobiasmaneschijn.demoGame.DemoGame;
import com.tobiasmaneschijn.demoGame.entities.EnemyEntity;
import com.tobiasmaneschijn.demoGame.entities.PlayerEntity;
import com.tobiasmaneschijn.demoGame.entities.RotatingEntity;

import java.util.ArrayList;

public class RealEstateGame extends Game {

    private Board board;

    private static RealEstateGame instance;

    public RealEstateGame() {
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

        board = new Board(this);
        board.initializeBoard();
    }

    @Override
    public void draw() {
        super.draw();
        board.draw();
    }

    @Override
    public void destroyEntity(Entity e) {
        super.destroyEntity(e);
    }

    public static void main( String[] args )
    {
        RealEstateGame game = new RealEstateGame();
        game.startGame();

    }

}
