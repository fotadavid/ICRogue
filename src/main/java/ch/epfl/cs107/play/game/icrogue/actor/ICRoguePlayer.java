package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.connector.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.DarkLord;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Coin;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.SkullFire;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ICRoguePlayer extends ICRogueActor implements Interactor {
    // boolean variables used to keep track of conditions
    private boolean StaffCollection = false;
    private boolean CoinCollection = false;
    private boolean UNREGISTER_ONCE = true;
    private boolean isTransitioning = false;
    private String destination;
    private final List<Integer> keyChain = new ArrayList<>();

    // variable to display a message on screen
    private TextGraphics message;
    private Sprite currentsprite;
    private float dt = 0;
    private float hp;

    private final boolean isAlive = true;

    /// Animation duration in frame number
   private int MOVE_DURATION = 7;
    /**
     * Demo actor
     *
     */
    private int arrayIndex = 0;
    private final Sprite[] movementArray1;
    private final Sprite[] movementArray2;
    private final Sprite[] movementArray3;
    private final Sprite[] movementArray4;
    public ICRoguePlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName) {
        super(owner, orientation, coordinates);
        this.hp = 10;
        message = new TextGraphics("HP :" + hp, 1f, Color.WHITE, null, 1, true, false, new Vector(7.1f, 9.4f));

        //bas
        Sprite sprite1 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));
        Sprite sprite11 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(16, 0, 16, 32), new Vector(.15f, -.15f));
        Sprite sprite12 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(32, 0, 16, 32), new Vector(.15f, -.15f));
        Sprite sprite13 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(48, 0, 16, 32), new Vector(.15f, -.15f));
        movementArray1 = new Sprite[]{sprite1, sprite11, sprite12, sprite13};
        // droite
        Sprite sprite2 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 32, 16, 32), new Vector(.15f, -.15f));
        Sprite sprite21 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(16, 32, 16, 32), new Vector(.15f, -.15f));
        Sprite sprite22 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(32, 32, 16, 32), new Vector(.15f, -.15f));
        Sprite sprite23 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(48, 32, 16, 32), new Vector(.15f, -.15f));
        movementArray2 = new Sprite[]{sprite2, sprite21, sprite22, sprite23};
        // haut
        Sprite sprite3 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 64, 16, 32), new Vector(.15f, -.15f));
        Sprite sprite31 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(16, 64, 16, 33), new Vector(.15f, -.15f));
        Sprite sprite32 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(32, 64, 16, 32), new Vector(.15f, -.15f));
        Sprite sprite33 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(48, 64, 16, 32), new Vector(.15f, -.15f));
        movementArray3 = new Sprite[]{sprite3, sprite31, sprite32, sprite33};
        // gauche
        Sprite sprite4 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 96, 16, 32), new Vector(.15f, -.15f));
        Sprite sprite41 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(16, 96, 16, 32), new Vector(.15f, -.15f));
        Sprite sprite42 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(32, 96, 16, 32), new Vector(.15f, -.15f));
        Sprite sprite43 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(48, 96, 16, 32), new Vector(.15f, -.15f));
        movementArray4 = new Sprite[]{sprite4, sprite41, sprite42, sprite43};
        currentsprite = sprite3;
    }

    // updates the status of the game
    // allows to move, turn, throw fireball, walk faster
    // increase the speed of the player if the coin has been collected during a certain time
    // remove the player of the screen game and throw the GameOver when the player lost
    // updates the HP of the player when he looses some hp
    @Override
    public void update(float deltaTime) {

        Keyboard keyboard = getOwnerArea().getKeyboard();
        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        turnIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        turnIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        turnIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        turnIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
        fireBall();
        energyBoost();
        if(CoinCollection)
            dt += deltaTime;
        float SPEED_TIME = 4.f;
        if(dt >= SPEED_TIME) {
            MOVE_DURATION = 7;
            CoinCollection = false;
            dt = 0;
        }
        if(hp <= 0 && UNREGISTER_ONCE) {
            UNREGISTER_ONCE = false;
            gameOver();
            getOwnerArea().unregisterActor(this);
        }
        super.update(deltaTime);
        if(hp < 6){
            message = new TextGraphics("HP :" + hp, 1f, Color.RED, null, 1, true, false, new Vector(7.1f, 9.4f));
        }
        message.setText("HP :" + hp);

    }
    // this method draws the player on the screen
    // and also the TextGraphics message (his HP)
    public boolean draw(Canvas canvas) {
        message.draw(canvas);
        currentsprite.draw(canvas);
        return false;
    }

    // allows the player to throw fireball when the button X is pressed
    // he can do so only if the Staff has been collected
    public void fireBall() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        Button b;
        b = keyboard.get(Keyboard.X);
        if (b.isPressed() && StaffCollection) {
            Fire fire = new Fire(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates());
            getOwnerArea().registerActor(fire);
        }
    }

    // methods which changes the speed to 3, of the player when the coin has been collected
    public void energyBoost() {
        if (CoinCollection) {
             MOVE_DURATION = 3;
        }
    }

    /**
     * Orientate and Move this player in the given orientation if the given button is down
     * @param orientation (Orientation): given orientation, not null
     * @param b (Button): button corresponding to the given orientation, not null
     */

    // allows the player to move on the map
    private void moveIfPressed(Orientation orientation, Button b){
        if(b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
                arrayIndex = (arrayIndex + 1) % 4;
            }
        }
        if(b.isReleased())
            arrayIndex = 0;
    }

    // allows the player to animate when he turns and walks, this means that the player changes sprites based on his
    // orientation
    private void turnIfPressed(Orientation orientation, Button b) {
        if( getOrientation() == Orientation.LEFT )
            currentsprite = movementArray4[arrayIndex];
        if( getOrientation() == Orientation.RIGHT )
            currentsprite = movementArray2[arrayIndex];
        if( getOrientation() == Orientation.DOWN ) {
            currentsprite = movementArray1[arrayIndex];
        }
        if( getOrientation() == Orientation.UP )
            currentsprite = movementArray3[arrayIndex];
        if (b.isDown())
            if (!isDisplacementOccurs())
                orientate(orientation);
    }

    /**
     * Leave an area by unregister this player
     */
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    /**
     *
     * @param area (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position){
        if(isAlive)
            area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
    }
    // setter and getter of the HP of the player
    public void setHp(float hp)
    {
        this.hp = hp;
    }
    public float getHp()
    {
        return hp;
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    public List<DiscreteCoordinates> getFieldOfViewCells(){
        return Collections.singletonList (getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    public String getDestination() {
        return destination;
    }
    public boolean isTransitioning(){
        return isTransitioning;
    }
    public void setTransitionStateToFalse(){ isTransitioning = false;}

    public boolean wantsCellInteraction() {
        return true;
    }

    // allows the player to have ViewInteraction when the button W is pressed
    @Override
    public boolean wantsViewInteraction() {
        Keyboard  keyboard = getOwnerArea().getKeyboard();
        Button b = keyboard.get(keyboard.W);
        return b.isDown();
    }
    public ICRoguePlayerInteractionHandler handler = new ICRoguePlayerInteractionHandler();
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }
    // has an interactWith method for each of these object types, which is called when the player interacts with the object
    // method for each object type depends on the value of isCellInteraction
    // the interactWith method for a Connector object checks whether isCellInteraction
    // is true and whether the player is not already transitioning between cells
    // the interactWith method for a DarkLord object simply reduces the player's
    // hit points by 1 and moves the player in a specific direction if isCellInteraction is true

    private class ICRoguePlayerInteractionHandler implements ICRogueInteractionHandler{
        public void interactWith(Cherry cherry, boolean isCellInteraction)
        {
            if(isCellInteraction)
                cherry.collect();
        }
        public void interactWith(Staff staff, boolean isCellInteraction)
        {
            if(!isCellInteraction) {
                staff.collect();
                StaffCollection = true;
            }
        }

        public void interactWith(Coin coin, boolean isCelInteraction)
        {
            if(isCelInteraction) {
                coin.collect();
                CoinCollection = true;
                getOwnerArea().unregisterActor(coin);
            }
        }
        public void interactWith(Key key, boolean isCellInteraction)
        {
            if(isCellInteraction){
                key.collect();
                keyChain.add(key.getKeyId());
            }
        }
        public void interactWith(Connector connector, boolean isCellInteraction)
        {
            if(isCellInteraction && !isDisplacementOccurs())
            {
                destination = connector.getDestination();
                isTransitioning = true;
            }
            if(!keyChain.isEmpty())
                if(!isCellInteraction && connector.getKeyId() == keyChain.get(0))
                {
                    connector.setCurrentState(Connector.ConnectorType.OPEN);
                }
        }

        @Override
        public void interactWith(Arrow arrow, boolean isCellInteraction) {
        }
        public void interactWith(SkullFire skullFire, boolean isCellInteraction) {
        }
        @Override
        public void interactWith(Turret turret, boolean isCellInteraction) {
            if(isCellInteraction)
                turret.die();
        }
        public void interactWith(DarkLord boss, boolean isCellInteraction){
            if(isCellInteraction) {
                setHp(getHp() - 1);
                if( getCurrentMainCellCoordinates().x > 1 && getCurrentMainCellCoordinates().x < 8 )
                    orientate(Orientation.LEFT);
                else if( getCurrentMainCellCoordinates().y > 1 && getCurrentMainCellCoordinates().y < 8 )
                    orientate(Orientation.UP);
                else if( (getCurrentMainCellCoordinates().x == 1 || getCurrentMainCellCoordinates().x == 8) && getCurrentMainCellCoordinates().y == 8 )
                    orientate(Orientation.DOWN);
                else if( (getCurrentMainCellCoordinates().x == 1 || getCurrentMainCellCoordinates().x == 8) && getCurrentMainCellCoordinates().y == 0 )
                    orientate(Orientation.UP);
                move(MOVE_DURATION);
            }
        }
    }
    // boolean returning if the player is Alive
    public boolean isAlive(){return isAlive;}
    // method to display the Game Over when the player is dead, has lost
    public void gameOver(){
        Foreground gameOver = new Foreground(getOwnerArea(), new RegionOfInterest(5, 5, 1200, 2000),
                "icrogue/GameOverRestart");
        getOwnerArea().registerActor(gameOver);
    }
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

}