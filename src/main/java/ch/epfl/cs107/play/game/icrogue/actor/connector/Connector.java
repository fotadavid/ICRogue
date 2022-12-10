package ch.epfl.cs107.play.game.icrogue.actor.connector;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.lang.reflect.Type;
import java.util.List;

public class Connector extends AreaEntity implements Interactable {

    private Sprite invisible, closed, locked, currentState;
    private int NO_KEY_ID = 67, keyId;
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
    public Connector(Area area, Orientation orientation, DiscreteCoordinates coordinates, String destination) {
        super(area, orientation, coordinates);
        this.destination = destination;
        this.coordinates = coordinates;
        invisible = new Sprite("icrogue/invisibleDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        closed = new Sprite("icrogue/door_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        locked = new Sprite("icrogue/lockedDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        currentState = invisible;
    }
    public Connector(Area area, Orientation orientation, DiscreteCoordinates coordinates, String destination, int keyId) {
        super(area, orientation, coordinates);
        this.keyId = keyId;
        this.destination = destination;
        this.coordinates = coordinates;
        invisible = new Sprite("icrogue/invisibleDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        closed = new Sprite("icrogue/door_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        locked = new Sprite("icrogue/lockedDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        currentState = locked;
    }
    public DiscreteCoordinates getCoordinates(){return coordinates;}

    public void setDestination(String destination) {this.destination = destination;}

    public String getDestination() {
        return destination;
    }
    public int getKeyId(){
        if(this.getType().equals(ConnectorType.LOCKED))
            return keyId;
        else return NO_KEY_ID;
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
    public void setKeyId(int keyId) {this.keyId = keyId;}

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        if(this.getType() == ConnectorType.LOCKED)
            return true;
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
            ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
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

