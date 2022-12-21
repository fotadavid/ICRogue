package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.DarkLord;
import ch.epfl.cs107.play.game.icrogue.actor.items.Coin;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0DarkLordRoom extends Level0EnemyRoom {

    private DarkLord boss;
    private Coin coin;


    public Level0DarkLordRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        // adds the coin and the boss object to the room using their respective areas, orientations, and coordinates
        boss = new DarkLord(this, Orientation.DOWN, new DiscreteCoordinates(3, 4));
        coin = new Coin(this, Orientation.UP, new DiscreteCoordinates(4, 5));
    }

    // registers the  boss and the coin as actors in the game
    @Override
    protected void createArea() {
        super.createArea();
        registerActor(boss);
        registerActor(coin);
    }

    // boolean returning true if the coin has been collected
    // and if the boss is alive, false otherwise
    public boolean logic() {
        if (!boss.isAlive() && coin.isCollected()) {
            return true;
        }
        return false;
    }

    // updates the state of the objects in the room
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
