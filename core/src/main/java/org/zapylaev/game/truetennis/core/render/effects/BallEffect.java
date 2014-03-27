/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Zapylaiev Kyrylo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.zapylaev.game.truetennis.core.render.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import org.zapylaev.game.truetennis.core.domain.Ball;

public class BallEffect implements IEffect<Ball> {

    private final ParticleEffect mEffect;
    private OrthographicCamera mCamera;
    private Vector3 mTempVec;
    private boolean mPlayed;

    public BallEffect(OrthographicCamera camera) {
        mCamera = camera;
        mEffect = new ParticleEffect();
        mEffect.load(Gdx.files.internal("effects/ball.eff"), Gdx.files.internal("effects/"));
        mEffect.start();
        mTempVec = new Vector3();
    }

    @Override
    public void draw(SpriteBatch batch, Ball domainObj) {
        if (mPlayed) {
            batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.begin();
            float x = domainObj.getPosition().x;
            float y = domainObj.getPosition().y;
            mTempVec.set(x, y, 0);
            mCamera.project(mTempVec);
            mEffect.setPosition(mTempVec.x, mTempVec.y);
            mEffect.draw(batch, Gdx.graphics.getDeltaTime());
            batch.end();
        }
    }

    @Override
    public void play() {
        mPlayed = true;
    }

    @Override
    public void stop() {
        mPlayed = false;
        mEffect.dispose();
    }
}
