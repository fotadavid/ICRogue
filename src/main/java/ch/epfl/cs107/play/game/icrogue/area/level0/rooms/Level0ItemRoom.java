package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.List;

abstract class Level0ItemRoom extends Level0Room{

    private boolean isCollected;

    List<Item> items = new ArrayList<>();

    // takes in a single argument of type DiscreteCoordinates (represents the coordinates of the room)
    public Level0ItemRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }

    // adds an Item object to the list of items for this room
    public void addItemToList(Item item){items.add(item);}

    // returns a boolean value indicating whether the object isCollected or not
    public boolean isCollected() {
        return isCollected;
    }


}
