package com.tobiasmaneschijn.realestate;

import com.tobiasmaneschijn.core.ResourceFactory;
import com.tobiasmaneschijn.core.Sprite;
import com.tobiasmaneschijn.realestate.entities.FieldEntity;
import com.tobiasmaneschijn.realestate.entities.PlayerEntity;
import org.joml.Vector2f;

import java.util.ArrayList;

public class Board {

    private RealEstateGame game;

    private ArrayList<FieldEntity> fieldEntities = new ArrayList<>();

    private int boardSize = 30;
    Board(RealEstateGame game){
        this.game = game;
    }

    private void generateSquareBoard(int sideLength) {
        int offSet = (64-sideLength)/2;
        int i = 0;
        for(int x=sideLength; x > 0; x--){
            FieldEntity field = new FieldEntity();
            field.setPosition(x * 64 + offSet, sideLength * 64+offSet);
            fieldEntities.add(field);
        }
        for(int y=sideLength; y > 0; y--){
            FieldEntity field = new FieldEntity();
            field.setPosition(offSet, y * 64 + offSet);
            fieldEntities.add(field);
        }
        for(int x=0; x < sideLength; x++){
            FieldEntity field = new FieldEntity();
            field.setPosition(x * 64 +offSet, offSet);
            fieldEntities.add(field);
        }
        for(int y=0; y < sideLength; y++){
            FieldEntity field = new FieldEntity();
            field.setPosition(sideLength * 64 +offSet, y * 64+offSet );
            fieldEntities.add(field);
        }

        for (FieldEntity fieldEntity : fieldEntities) {
            game.addEntity(fieldEntity);
        }

    }

    public void initializeBoard(){

        float xOrigin = 0;
        float yOrigin = 0;
        float spriteSize = 64;
        float offset = 32;

        generateSquareBoard(10);
        final FieldEntity start = fieldEntities.get(0);
        Sprite spr = ResourceFactory.get().getSprite("sprites/squares/start.gif");
        start.setSprite(spr);
    }

    public FieldEntity getFieldAtIndex(int index){
       try{
           return getFields().get(index);
       }catch (IndexOutOfBoundsException ex){
           return  null;
       }
    }
    public ArrayList<FieldEntity> getFields() {
        return fieldEntities;
    }

    public void draw() {
    }
}
