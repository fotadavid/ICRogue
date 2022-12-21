package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;


public class Coin extends Item{

    private Sprite[] sprites = new Sprite[4];
    private Animation currentAnimation;

    public Coin(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        for(int i = 0; i < 4; i++)
            sprites[i] = new Sprite("zelda/coin", .8f, .8f, this, new RegionOfInterest(i*16, 0, 16, 16));
        currentAnimation = new Animation(10, sprites);
        currentAnimation.setSpeedFactor(5);
        currentAnimation.setAnchor(new Vector(0, 0));
        currentAnimation.setHeight(.8f);
        currentAnimation.setHeight(.8f);
        //coin = new Sprite("zelda/coin", .5f, .5f, this);
    }
    public String getTitle() {
        return "zelda/coin";
    }

    // draws the coin on the game screen
    // if it hasn't been collected
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        currentAnimation.update(deltaTime);
    }

    @Override
    public boolean draw(Canvas canvas) {
        if(!this.isCollected())
            currentAnimation.draw(canvas);
        return false;
    }

    // returns a list of DiscreteCoordinates representing the cells that the coin occupies
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }


    @Override
    public boolean isViewInteractable() {
        return false;
    }

    // a boolean indicating whether the interaction
    // is a cell interaction or a view interaction
    // calls the appropriate interaction method on the visitor
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public boolean isCellInteractable() {
        if(isCollected())
            return false;
        return true;
    }

}



