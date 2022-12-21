package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;


public class Coin extends Item{

    private Sprite coin;

    public Coin(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        coin = new Sprite("zelda/coin", .5f, .5f, this);
    }


    public String getTitle() {
        return "zelda/coin";
    }

    @Override
    public boolean draw(Canvas canvas) {
        if(!this.isCollected())
            coin.draw(canvas);
        return false;
    }

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
        return true;
    }


    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

}



