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

import java.lang.reflect.Type;
import java.util.List;

public class Connector extends AreaEntity implements Interactable {

    private Sprite invisible, closed, locked, currentState;
    private int NO_KEY_ID;
    private DiscreteCoordinates coordinates;
    private String destination;
    public enum ConnectorType{
        OPEN,
        CLOSED,
        LOCKED,
        INVISIBLE;
    }
    public ConnectorType getType(){return type;}
    private ConnectorType type = ConnectorType.INVISIBLE;
    public Connector(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);
        this.coordinates = coordinates;
        invisible = new Sprite("icrogue/invisibleDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        closed = new Sprite("icrogue/door_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        locked = new Sprite("icrogue/lockedDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        currentState = invisible;
    }

    public DiscreteCoordinates getCoordinates(){return coordinates;}

    public String getDestination() {
        return destination;
    }

 public List<DiscreteCoordinates> getCurrentCells() {
        DiscreteCoordinates coord = getCurrentMainCellCoordinates();
        return List.of(coord, coord.jump(new Vector((getOrientation().ordinal()+1)%2, getOrientation().ordinal()%2)));
    }

    @Override
    public boolean takeCellSpace() {
        if(!this.getType().equals(ConnectorType.OPEN))
            return true;
        else  return false;
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
        if( type.equals(ConnectorType.INVISIBLE) )
            invisible.draw(canvas);
        else if( type.equals(ConnectorType.LOCKED) )
            locked.draw(canvas);
        else if( type.equals(ConnectorType.CLOSED) )
            closed.draw(canvas);
    }

    public void setCurrentState(ConnectorType type){
        this.type = type;
    }
}

