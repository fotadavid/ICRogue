package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.window.Window;

public class ICRogueBehavior extends AreaBehavior {
    // enum for the different types of cells in the game
    public enum ICRogueCellType{
        //https://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
       // each type has a corresponding integer value and a boolean value indicating whether it is walkable
        NONE(0, false),
        GROUND(-16777216, true),
        WALL(-14112955, false),
        HOLE(-65536, true),;

        final int type;
        final boolean isWalkable;

        // constructor for a cell type
        ICRogueCellType(int type, boolean isWalkable){
            this.type = type;
            this.isWalkable = isWalkable;
        }

        // converts an integer value to a cell type
        public static ICRogueCellType toType(int type){
            for(ICRogueCellType ict : ICRogueCellType.values()){
                if(ict.type == type)
                    return ict;
            }
            // When you add a new color, you can print the int value here before assign it to a type
            System.out.println(type);
            return NONE;
        }
    }

    /**
     * Default Tuto2Behavior Constructor
     * @param window (Window), not null
     * @param name (String): Name of the Behavior, not null
     */
    public ICRogueBehavior(Window window, String name){
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width ; x++) {
                ICRogueCellType color = ICRogueCellType.toType(getRGB(height-1-y, x));
                setCell(x,y, new ICRogueCell(x,y,color));
            }
        }
    }

    public class ICRogueCell extends Cell {
        /// Type of the cell following the enum
        private final ICRogueCellType type;

        public  ICRogueCell(int x, int y, ICRogueCellType type){
            super(x, y);
            this.type = type;
        }

        // returns the type field of the ICRogueCell
        public ICRogueCellType getType(){
            return type;
        }

        // always allow entities to leave the cell
        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        // determines whether an entity is allowed to enter the cell
        // loops through the entities already in the cell and returns false
        // if any of them take up space and are not the player
        // returns true if the cell is walkable, or false otherwise
        @Override
        protected boolean canEnter(Interactable entity) {
            for(Interactable object : entities)
                if(object.takeCellSpace() && object.getClass() != ICRoguePlayer.class)
                    return false;
            return type.isWalkable;
        }


        // both following methods, return whether the cell or its contents can be interacted with
        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return false;
        }

        // allows a visitor to interact with the cell
        @Override
        public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
            ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
        }

    }
}