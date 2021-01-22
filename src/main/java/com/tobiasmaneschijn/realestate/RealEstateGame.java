package com.tobiasmaneschijn.realestate;

import com.tobiasmaneschijn.core.Entity;
import com.tobiasmaneschijn.core.Game;
import com.tobiasmaneschijn.core.GameWindow;
import com.tobiasmaneschijn.demoGame.DemoGame;
import com.tobiasmaneschijn.demoGame.entities.EnemyEntity;
import com.tobiasmaneschijn.demoGame.entities.RotatingEntity;
import com.tobiasmaneschijn.realestate.entities.FieldEntity;
import com.tobiasmaneschijn.realestate.entities.PlayerEntity;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class RealEstateGame extends Game {

    private Board board;
    private ArrayList<PlayerEntity> players;
    private static RealEstateGame instance;

    public RealEstateGame() {
        super();
        setGameTitle("Demo Game");

    }

    int currentFieldIndex = 0;
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for (FieldEntity field : board.getFields()) {
            field.update(deltaTime);
        }

      if( GameWindow.isClicked(GLFW.GLFW_KEY_SPACE)){
            currentFieldIndex += 1;
            FieldEntity field = board.getFieldAtIndex(currentFieldIndex);
            if(field == null){
                currentFieldIndex  = 0;
                field = board.getFieldAtIndex(currentFieldIndex);
            }

          players.get(0).moveTo(new Vector2f(field.getPosition().x + 16, field.getPosition().y + 16));
        }


    }

    @Override
    public void initialise() {
        super.initialise();

        board = new Board(this);
        board.initializeBoard();

        initPlayers();

    }

    private void initPlayers() {
      players = new ArrayList<PlayerEntity>();

      PlayerEntity player = new PlayerEntity();
      player.setName("Tobias");
      players.add(player);
      addEntity(player);
      Vector2f startPos = board.getFieldAtIndex(0).getPosition();
      player.moveTo(new Vector2f(startPos.x + 16, startPos.y + 16));

    }

    @Override
    public void draw() {
        super.draw();


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
