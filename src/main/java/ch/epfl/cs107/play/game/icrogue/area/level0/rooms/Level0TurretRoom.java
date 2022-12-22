package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Coin;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0TurretRoom extends Level0EnemyRoom{

    private Turret turret1;
    private Turret turret2;
    private Coin coin;
    public Level0TurretRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        // add the turret1 object to the list of Item
        turret1 = new Turret(this, Orientation.UP, new DiscreteCoordinates(1, 8),
                new Orientation[]{Orientation.DOWN, Orientation.RIGHT});
        // add the turret2 object to the list of Item
        turret2 = new Turret(this, Orientation.UP, new DiscreteCoordinates(8, 1),
                new Orientation[]{Orientation.UP, Orientation.LEFT});
        // add the coin object to the list of Item
        coin = new Coin(this, Orientation.UP, new DiscreteCoordinates(4, 5));
    }

    // registers both turrets and the coin as actors in the game
    @Override
    protected void createArea() {
        super.createArea();
        registerActor(turret1);
        registerActor(turret2);
        registerActor(coin);
    }

    // returns true if the turrets are alive, and false otherwise
    // returns true if the coin has been collected, and false otherwise
    public boolean logic(){
        if(!turret1.IsAlive() && !turret2.IsAlive() && coin.isCollected()){
            return true;}
        return false;
    }

    // updates the state of the objects in the room
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

}
