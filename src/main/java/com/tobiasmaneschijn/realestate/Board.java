package com.tobiasmaneschijn.realestate;

import com.tobiasmaneschijn.realestate.entities.Field;

import java.util.ArrayList;

public class Board {

    private RealEstateGame game;

    private ArrayList<Field> fields = new ArrayList<>();

    private int boardSize = 46;
    Board(RealEstateGame game){
        this.game = game;
    }

    public void initializeBoard(){
        for (int i = 0; i < boardSize; i++) {
            Field field = new Field();
            field.setName("Field: " + i);
            game.addEntity(field);
            fields.add(field);

            if(i < boardSize / 4){
                field.setPosition( 64 * i, game.getGameWindow().getWidth() / 3.0f);
                System.out.println(1);
            }
            else if(i <= boardSize / 3){
                field.setPosition( game.getGameWindow().getWidth() / 3.0f, (game.getGameWindow().getWidth() / 3.0f) + 64 *i);
                System.out.println(2);

            }
            else if(i <= boardSize / 2){
                field.setPosition( game.getGameWindow().getWidth() / 3.0f, (game.getGameWindow().getWidth() / 3.0f) + 64 * (boardSize/4.0f));
                System.out.println(3);



            }



        }




    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void draw() {
        for (Field field : fields) {
            field.render();
        }

    }
}
