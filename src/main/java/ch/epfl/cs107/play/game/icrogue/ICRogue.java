package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.Playable;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.game.icrogue.area.Level0;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import java.util.Collections;
import java.util.List;

public class ICRogue extends AreaGame {
    public final static float CAMERA_SCALE_FACTOR = 13.f;
    public final static String ROOM = "icrogue/Level0Room";
    private ICRoguePlayer player;
    private boolean YOU_LOST_ONCE = true;
    private boolean PLAYER_LEAVES_ONCE = true;
    private final String[] areas = {ROOM};
    private float dt = 0;
    private final float GAME_OVER_TIME = 4.f;

    private int areaIndex;
    /**
     * Add all the areas
     */
    ICRogueRoom currentRoom;
    Level currentLevel;

    // initializes the game by creating a new instance of Level0 and generating a fixed map
    // then adds the areas in the level, sets the player's current area to the level's title,
    // and creates a new player character at a specific location
    private void initLevel(){
        PLAYER_LEAVES_ONCE = true;
        currentLevel = new Level0();
        currentLevel.generateMap();
        currentLevel.addAreas(this);
        Level0Room area = (Level0Room) setCurrentArea(currentLevel.getTitle(), true);
        player = new ICRoguePlayer(area, Orientation.DOWN, new DiscreteCoordinates(2, 2),"zelda/player");
        player.enterArea(area, new DiscreteCoordinates(2, 2));
    }

    // is called when the game starts, and it initializes the level by calling initLevel()
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            initLevel();
            return true;
        }
        return false;
    }

    // updates the game's state
    // if the player is not alive it prints "You Lost! Try Again"
    // and removes the player from the current area and re-initializes the level
    // if the player is transitioning between areas, it calls the switchRoom() method

@Override
    public void update(float deltaTime) {
    super.update(deltaTime);
    currentLevel.checkGameStatus();
    if(!player.isAlive() && YOU_LOST_ONCE) {
        System.out.println("You Lost!" + "\n" + "Try Again");
        YOU_LOST_ONCE = false;
    }
    if(player.isTransitioning())
        switchRoom();
    if(!player.isAlive())
        {
            if(PLAYER_LEAVES_ONCE)
            {
                player.gameOver();
                player.leaveArea();
                PLAYER_LEAVES_ONCE = false;
            }
        }
    resetMotion();
    }

    //  resets the player's motion if the "R" key on the keyboard is pressed
    //  if it is, the player is removed from the current area and the level is re-initialized
    public void resetMotion(){
        Keyboard keyboard = getWindow().getKeyboard();
        Button b = keyboard.get(Keyboard.R);
        if( b.isPressed() ) {
            player.leaveArea();
            initLevel();
        }
    }

    // is called when the game ends
    @Override
    public void end() {
    }

    // returns the title of the game as a string
    @Override
    public String getTitle() {

        return "ICRogue";
    }

    // switch the player to a different area when the player moves between rooms
    // removes the player from the current area, sets the player's transition state to "false",
    // and sets the player's current area to the destination specified in the player's destination field

    protected void switchRoom()
    {
        player.leaveArea();
        player.setTransitionStateToFalse();
        Level0Room area = (Level0Room) setCurrentArea(player.getDestination(), true);
        if(player.getOrientation() == Orientation.DOWN)
            player.enterArea(area, new DiscreteCoordinates(5, 8));
        else if(player.getOrientation() == Orientation.UP)
            player.enterArea(area, new DiscreteCoordinates(5, 1));
        else if(player.getOrientation() == Orientation.RIGHT)
            player.enterArea(area, new DiscreteCoordinates(1, 5));
        else player.enterArea(area, new DiscreteCoordinates(8, 5));
    }

    // returns a list of the player's current main cell coordinates
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(player.getCurrentMainCellCoordinates());
    }

}