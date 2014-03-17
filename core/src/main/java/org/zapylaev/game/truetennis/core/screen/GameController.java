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

package org.zapylaev.game.truetennis.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Json;
import org.zapylaev.game.truetennis.core.Constants;
import org.zapylaev.game.truetennis.core.IRenderer;
import org.zapylaev.game.truetennis.core.domain.Field;
import org.zapylaev.game.truetennis.core.domain.Team;
import org.zapylaev.game.truetennis.core.model.IModel;
import org.zapylaev.game.truetennis.core.model.IModelListener;
import org.zapylaev.game.truetennis.core.model.PhysicalModel;
import org.zapylaev.game.truetennis.core.render.GameRenderer;

/**
 * @author k.zapylaev <zapylaev@gmail.com>
 */
public class GameController extends InputAdapter implements Screen, IModelListener {
    enum State {
        IDLE, END_GAME, GAME
    }
    private IModel mModel;
    private IRenderer mGameRenderer;
    private OrthographicCamera mMainCamera;
    private OrthographicCamera mHUDCamera;
    private State mState;
    private Json mJson;

    @Override
    public void show() {
        mModel = new PhysicalModel();
        mMainCamera = new OrthographicCamera(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        mMainCamera.update();
        mHUDCamera = new OrthographicCamera(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        mHUDCamera.update();
        mGameRenderer = new GameRenderer(mMainCamera, mHUDCamera);
        mModel.addModelListener(this);
        Gdx.input.setInputProcessor(this);
        mState = State.IDLE;
        mJson = new Json();
    }

    @Override
    public void render(float delta) {
        mModel.update();
        mGameRenderer.render();
        mModel.debugRender(mMainCamera);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            if (mState == State.IDLE) {
                mModel.startRound();
                mState = State.GAME;
                return true;
            } else if (mState == State.END_GAME) {
                mGameRenderer.stopWinEffect();
                mModel.resetRound();
                mModel.startRound();
                mState = State.GAME;
                return true;
            }
        }
        return false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / height;
        mMainCamera.viewportWidth = Constants.SCREEN_HEIGHT * aspectRatio;
        mMainCamera.update();
        mHUDCamera.viewportWidth = Constants.HUD_SCREEN_HEIGHT * aspectRatio;
        mHUDCamera.update();
    }

    @Override
    public void dispose() {
        mGameRenderer.dispose();
        mModel.dispose();
    }

    @Override
    public void onModelUpdate(String modelState) {
        mGameRenderer.updateModel(mJson.fromJson(Field.class, modelState));
    }

    @Override
    public void goalEvent() {
        mState = State.IDLE;
    }

    @Override
    public void winEvent() {
        mState = State.END_GAME;
        mGameRenderer.playWinEffect();
    }
}
