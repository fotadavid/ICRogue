package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class ICRoguePlayer extends ICRogueActor {

    private TextGraphics message;
    private Sprite sprite1;
    private Sprite sprite2;
    private Sprite sprite3;
    private Sprite sprite4;
    private Sprite currentsprite;

    private Fire fire;

    /// Animation duration in frame number
    final static int MOVE_DURATION = 8;
    /**
     * Demo actor
     *
     */


    public ICRoguePlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName) {
        super(owner, orientation, coordinates);
        //bas
        sprite1 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));
        // droite
        sprite2 = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 32, 16, 32), new Vector(.15f,-.15f));
        // haut
        sprite3 = new Sprite("zelda/player", .75f, 1.5f, this,new RegionOfInterest(0, 64, 16, 32), new Vector(.15f,-.15f));
        // gauche
        sprite4 = new Sprite("zelda/player", .75f, 1.5f, this,new RegionOfInterest(0, 96, 16, 32), new Vector(.15f,-.15f));
        currentsprite = sprite3;
    }


   /* public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }*/

    /**
     * Center the camera on the player
     */

    @Override
    public void update(float deltaTime) {

        Keyboard keyboard= getOwnerArea().getKeyboard();
        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        turnIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        turnIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        turnIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        turnIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
        fireBall();
        super.update(deltaTime);

    }
    public void draw(Canvas canvas) {
        currentsprite.draw(canvas);
    }
    public void fireBall() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        Button b;
        b = keyboard.get(Keyboard.X);
        if (b.isDown()) {
            fire = new Fire(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates());
            getOwnerArea().registerActor(fire);
        }
    }



  

    /**
     * Orientate and Move this player in the given orientation if the given button is down
     * @param orientation (Orientation): given orientation, not null
     * @param b (Button): button corresponding to the given orientation, not null
     */

    private void moveIfPressed(Orientation orientation, Button b){
        if(b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    private void turnIfPressed(Orientation orientation, Button b) {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        if( getOrientation() == Orientation.LEFT )
            currentsprite = sprite4;
        else if( getOrientation() == Orientation.RIGHT )
            currentsprite = sprite2;
        else if( getOrientation() == Orientation.DOWN )
            currentsprite = sprite1;
        else if( getOrientation() == Orientation.UP )
            currentsprite = sprite3;
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
            }
        }
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
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());

        resetMotion();
    }



    ///Ghost implements Interactable

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

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
    }

}