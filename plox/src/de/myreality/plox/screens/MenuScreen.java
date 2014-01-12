package de.myreality.plox.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.myreality.plox.PloxGame;
import de.myreality.plox.Resources;
import de.myreality.plox.tweens.SpriteTween;

public class MenuScreen implements Screen {
	
	private PloxGame game;

	private SpriteBatch batch;
	private Sprite background;
	private Sprite logo;
	private float logoWidth, logoHeight;
	private TweenManager tweenManager;
	private boolean lastTouched = true;
	
	public MenuScreen(PloxGame game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		batch.begin();
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background.draw(batch);
		
		logo.draw(batch);
		batch.end();
		
		if (!lastTouched && Gdx.input.isTouched()) {
			game.setScreen(new IngameScreen(game));
		}
		
		lastTouched = Gdx.input.isTouched();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		tweenManager = new TweenManager();
		background = new Sprite(Resources.BACKGROUND);
		logo = new Sprite(Resources.LOGO);
		logoWidth = logo.getWidth();
		logoHeight = logo.getHeight();
		batch = new SpriteBatch();
		
		float scaleFactor = Gdx.graphics.getWidth() / 800f;
		
		logo.setBounds(0, 0, scaleFactor * logoWidth, scaleFactor * logoHeight);
		
		logo.setX(Gdx.graphics.getWidth() / 2f - logo.getWidth() / 2f);
		logo.setY(Gdx.graphics.getHeight() - logo.getHeight() - 70);
		
		animateLogo();
	}
	
	private void animateLogo() {
		Tween.to(logo, SpriteTween.BOUNCE, 1)
        .target(Gdx.graphics.getHeight() - logo.getHeight() - 30)
        .ease(TweenEquations.easeInOutQuad)
        .setCallback(new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				animateLogo();
			}
        	
        })
        .setCallbackTriggers(TweenCallback.COMPLETE)
        .repeatYoyo(1, 0).start(tweenManager);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
