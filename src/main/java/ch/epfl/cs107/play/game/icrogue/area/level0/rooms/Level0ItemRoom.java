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

    public Level0ItemRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }
    public void addItemToList(Item item){items.add(item);}
    public boolean isCollected() {
        return isCollected;
    }


}
