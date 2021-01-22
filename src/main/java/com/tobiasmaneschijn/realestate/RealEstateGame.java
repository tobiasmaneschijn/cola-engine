package com.tobiasmaneschijn.realestate;

import com.tobiasmaneschijn.colaengine.core.Entity;
import com.tobiasmaneschijn.colaengine.core.Game;
import com.tobiasmaneschijn.colaengine.core.GameWindow;
import com.tobiasmaneschijn.realestate.entities.DieEntity;
import com.tobiasmaneschijn.realestate.entities.FieldEntity;
import com.tobiasmaneschijn.realestate.entities.PlayerEntity;
import org.joml.Random;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class RealEstateGame extends Game {

    private Board board;
    private ArrayList<PlayerEntity> players;
    private DieEntity[] dice;
    private static RealEstateGame instance;



    private static final Random random = new Random();


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

          int[] rolls = throwDice();
          int moveVal = 0;

          for (int roll : rolls) {
              moveVal += roll;
          }


          currentFieldIndex += moveVal;
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

        dice = new DieEntity[2];

        dice[0] = new DieEntity();
        dice[1] = new DieEntity();

        for (DieEntity die : dice) {
            addEntity(die);
        }

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


    public int[] throwDice(){
        int[] rolls = new int[dice.length];
        for (int i = 0; i < dice.length; i++) {
            DieEntity die = dice[i];
            rolls[i] = random.nextInt(6) + 1;
            die.setDie(rolls[i]);
        }
        return rolls;
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
