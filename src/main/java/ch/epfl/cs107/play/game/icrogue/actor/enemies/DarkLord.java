package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.nio.channels.spi.SelectorProvider;
import java.util.Random;

public class DarkLord extends ICRogueActor {
    private final Sprite[] sprites = new Sprite[4];
    private boolean isAttacking = false;
    private final Sprite[][] spellSprites = new Sprite[4][3];
    private Animation spellAnimation;
    private Sprite[] currentSpriteLine = new Sprite[3];
    private Sprite currentSprite;
    public DarkLord(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        for( int i = 0; i < 4; i++ )
            sprites[i] = new Sprite("zelda/darkLord", 1f, 2f, this,
                    new RegionOfInterest(0, i*32, 32, 32));
        for( int i = 0; i < 3; i++ )
            for( int j = 0; j < 4; j++ )
                spellSprites[j][i] = new Sprite("zelda/darkLord.spell", 1f, 2f, this,
                        new RegionOfInterest(i * 32, j*32, 32, 32));
        currentSprite = sprites[0];
        currentSpriteLine = spellSprites[0];
        spellAnimation = new Animation(24, currentSpriteLine);
        spellAnimation.setSpeedFactor(12);
        spellAnimation.setAnchor(new Vector(0, 0));
        spellAnimation.setHeight(1f);
        spellAnimation.setHeight(2f);
    }
    public void update()
    private void moveToRandomPosition()
    {
        int x = RandomHelper.roomGenerator.nextInt(1, 9);
        int y = RandomHelper.roomGenerator.nextInt(1, 9);
        DiscreteCoordinates randomPosition = new DiscreteCoordinates(x, y);
        changePosition(randomPosition);
    }
    public void attack(){
        getOwnerArea().registerActor(new Fire(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates()));
        isAttacking = false;
    }
    @Override
    public boolean draw(Canvas canvas) {
        if(isAttacking)
            spellAnimation.draw(canvas);
        else
            currentSprite.draw(canvas);
        return false;
    }
}
