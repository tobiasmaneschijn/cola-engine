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

        for (int i = 0; i < boardSize/4 ; i++) {
            Field field = new Field();
            field.setName("Field: " + i);
            game.addEntity(field);
            fields.add(field);
            field.setPosition(game.getGameWindow().getWidth() / 3f + 64*i, 256);
        }
        for (int i = 0; i < boardSize/4 ; i++) {
            Field field = new Field();
            field.setName("Field: " + i);
            game.addEntity(field);
            fields.add(field);
            field.setPosition(game.getGameWindow().getWidth() / 3f ,
                    200 + 64*i);
        }
        for (int i = 0; i < boardSize/4 ; i++) {
            Field field = new Field();
            field.setName("Field: " + i);
            game.addEntity(field);
            fields.add(field);
            field.setPosition(game.getGameWindow().getWidth() / 3f + 64*i,
                    256 + (64 * ((boardSize/4f)-1)));
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
