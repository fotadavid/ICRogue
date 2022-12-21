package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.DarkLord;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Coin;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0DarkLordRoom extends Level0EnemyRoom {

    private DarkLord boss;
    private Coin coin;

    public Level0DarkLordRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        boss = new DarkLord(this, Orientation.DOWN, new DiscreteCoordinates(3, 4));
        coin = new Coin(this, Orientation.UP, new DiscreteCoordinates(4, 5));
    }

    @Override
    protected void createArea() {
        super.createArea();
        registerActor(boss);
        registerActor(coin);
    }

    public boolean logic() {
        if (!boss.isAlive() && coin.isCollected()) {
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
