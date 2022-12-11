package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Level0KeyRoom extends Level0ItemRoom {
    private int keyId;
    private Key key;

    public Level0KeyRoom(DiscreteCoordinates roomCoordinates, int keyId) {
        super(roomCoordinates);
        this.keyId = keyId;
        key = new Key(this, Orientation.DOWN, new DiscreteCoordinates(5, 5), keyId);
        addItemToList(key);
    }

    public void createArea() {
        super.createArea();
        registerActor(key);
    }

    @Override
   public void update(float deltaTime) {
        super.update(deltaTime);
        if(isCollected()) {
            unregisterActor(key);
        }
    }

}
