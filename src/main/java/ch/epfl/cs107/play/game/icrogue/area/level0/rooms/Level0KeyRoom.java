package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Level0KeyRoom extends Level0ItemRoom {
    private int keyId; // integer representing the ID of the key
    private Key key;

    // constructor creates a new Key actor and adds it to the room
    // Key actor has the specified orientation, position, and key ID
    public Level0KeyRoom(DiscreteCoordinates roomCoordinates, int keyId) {
        super(roomCoordinates);
        this.keyId = keyId;
        key = new Key(this, Orientation.DOWN, new DiscreteCoordinates(5, 5), keyId);
        addItemToList(key);
    }

    // called to create the area of the room
    // registers the Key actor
    public void createArea() {
        super.createArea();
        registerActor(key);
    }

    // updates the state of the room and its actors
    // if the Key has been collected, it unregisters the Key actor
    @Override
   public void update(float deltaTime) {
        super.update(deltaTime);
        if(isCollected()) {
            unregisterActor(key);
        }
    }

    // boolean indicating if the Key has been collected or not
    public boolean logic(){
        if(key.isCollected()){
            return true;}
        return false;
    }
}
