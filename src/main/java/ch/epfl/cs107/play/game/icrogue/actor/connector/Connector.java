package ch.epfl.cs107.play.game.icrogue.actor.connector;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Connector extends AreaEntity implements Interactable {

    private Sprite invisible, closed, locked;
    private int NO_KEY_ID;
    private String destination;

    public Connector(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName) {
        super(owner, orientation, coordinates);
    }

    public String getDestination() {
        return destination;
    }

 public List<DiscreteCoordinates> getCurrentCells() {
        DiscreteCoordinates coord = getCurrentMainCellCoordinates();
        return List.of(coord, coord.jump(new Vector((getOrientation().ordinal()+1)%2, getOrientation().ordinal()%2)));
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {

    }

    @Override
    public void draw(Canvas canvas) {

    }
}

