package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Coin;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0TurretRoom extends Level0EnemyRoom{

    private Turret turret1;
    private Turret turret2;

    private Coin coin;
    public Level0TurretRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        turret1 = new Turret(this, Orientation.UP, new DiscreteCoordinates(1, 8));
        turret2 = new Turret(this, Orientation.UP, new DiscreteCoordinates(8, 1));
        coin = new Coin(this, Orientation.UP, new DiscreteCoordinates(4, 5));
    }

    @Override
    protected void createArea() {
        super.createArea();
        registerActor(turret1);
        registerActor(turret2);
        registerActor(coin);
    }
    public boolean logic(){
        if(!turret1.IsAlive() && !turret2.IsAlive() && coin.isCollected()){
            return true;}
        return false;
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

}
