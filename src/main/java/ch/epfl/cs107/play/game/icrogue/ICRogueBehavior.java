package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.DarkLord;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.SkullFire;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.window.Window;

public class ICRogueBehavior extends AreaBehavior {
    // enum that represents the different types of cells that can exist
    public enum ICRogueCellType{
        //https://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
        NONE(0, false),
        GROUND(-16777216, true),
        WALL(-14112955, false),
        HOLE(-65536, true),;

        final int type;
        final boolean isWalkable; // boolean indicating whether the cell is walkable or not

        // constructor for the enum members
        ICRogueCellType(int type, boolean isWalkable){
            this.type = type;
            this.isWalkable = isWalkable;
        }

        // converts an integer representation of a color to the corresponding enum member
        public static ICRogueCellType toType(int type){
            for(ICRogueCellType ict : ICRogueCellType.values()){
                // if the integer representation of the color matches
                // the type field of the enum member, return the enum member
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

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        } // by default, entity can leave any cell
        public ICRogueCellType getType(){
            return type;
        } //returns the type of the cell
        @Override
        protected boolean canEnter(Interactable entity) {
            // loops through all the entities in the cell
            for(Interactable object : entities) {
                // if the entity is a SkullFire or Fire and the object is a DarkLord,
                // returns whether the cell is walkable or not
                if((entity.getClass().equals(SkullFire.class) || entity.getClass().equals(Fire.class))
                        && object.getClass().equals(DarkLord.class))
                    return type.isWalkable;
                // if there are other entities in the cell that take up space,
                // but are not ICRoguePlayer entities, the entity cannot enter the cell
                if (object.takeCellSpace() && object.getClass() != ICRoguePlayer.class) {
                    return false;
                }
            }
            // if the entity is not a SkullFire or Fire,
            // or if there are no other entities in the cell that take up space,
            // return whether the cell is walkable or not
            return type.isWalkable;
        }

        // by default, cells are interactable
        @Override
        public boolean isCellInteractable() {
            return true;
        }

        // by default, cells are not view interactable
        @Override
        public boolean isViewInteractable() {
            return false;
        }

        // casts the AreaInteractionVisitor to an ICRogueInteractionHandler and calls the interactWith method
        @Override
        public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
            ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
        }

    }
}