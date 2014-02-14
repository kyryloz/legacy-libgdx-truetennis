package org.zapylaev.game.truetennis.core.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.zapylaev.game.truetennis.core.physics.PhysicalBox;

/**
 * @author k.zapylaev <zapylaev@gmail.com>
 */
public class Stick implements Renderable {
    public static final float WIDTH = 0.4f;
    public static final float HEIGHT = 2.8f;
    public static final float DENSITY = 1f;
    public static final float FRICTION = 1f;
    public static final float RESTITUTION = 1f;

    private PhysicalBox mPhysicalBox;

    public Stick(World world, float x, float y) {
        mPhysicalBox = (PhysicalBox) new PhysicalBox.BoxBuilder()
                .setX(x)
                .setY(y)
                .setWidth(WIDTH)
                .setHeight(HEIGHT)
                .setDensity(DENSITY)
                .setFriction(FRICTION)
                .setRestitution(RESTITUTION)
                .setType(BodyDef.BodyType.DynamicBody)
                .build(world);
    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
