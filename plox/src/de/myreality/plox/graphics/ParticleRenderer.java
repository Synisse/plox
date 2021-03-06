/* Acid - Provides a Java cell API to display fancy cell boxes.
 * Copyright (C) 2013  Miguel Gonzalez
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

package de.myreality.plox.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.myreality.plox.GameObject;
import de.myreality.plox.GameObjectListener;
import de.myreality.plox.GameObjectType;
import de.myreality.plox.Resources;
import de.myreality.plox.screens.IngameScreen;

/**
 * Listens to a snake to spawn particles on collisions
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ParticleRenderer  implements GameObjectListener{

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private ParticleManager particleManager;
	
	private Map<GameObject, ParticleEffect> effects;
	
	private IngameScreen screen;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public ParticleRenderer(IngameScreen screen) {
		particleManager = new ParticleManager();
		effects = new HashMap<GameObject, ParticleEffect>();
		this.screen = screen;
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================
	
	// ===========================================================
	// World methods
	// ===========================================================

	
	public void render(SpriteBatch batch, float delta) {
		particleManager.render(batch, delta);
	}
	
	public void clear() {
		particleManager.clear();
	}

	@Override
	public void onRemove(GameObject object) {
		ParticleEffect effect = effects.remove(object);
		
		if (effect != null) {
			particleManager.setEndless(effect, false);
			effect.setDuration(0);
			
			if (!object.getType().equals(GameObjectType.SHOT)) {
				effect = particleManager.create(Resources.PARTICLES_EXPLOSION, false);
				effect.setPosition(object.getCenterX(), object.getCenterY());
				effect.start();
			} else {
				effect = particleManager.create(Resources.PARTICLES_EXPLOSION_SMALL, false);
				effect.setPosition(object.getCenterX(), object.getCenterY());
				particleManager.setEndless(effect, false);
				effect.start();
			}
		}
	}

	@Override
	public void onKill(GameObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMove(GameObject object) {
		ParticleEffect effect = effects.get(object);
		
		if (effect != null) {		
			if (object.getType().equals(GameObjectType.ALIEN)) {
				effect.setPosition(object.getCenterX(), object.getCenterY() + object.getHeight() / 3f);
			} else if (object.getType().equals(GameObjectType.SHOT)) {
				effect.setPosition(object.getCenterX(), object.getCenterY());
			}
		}
	}

	@Override
	public void onAdd(GameObject object) {
		if (object.getType().equals(GameObjectType.ALIEN)) {
			ParticleEffect effect = particleManager.create(Resources.PARTICLES_BLUE, true);			
			effects.put(object, effect);
		}
		
		if ( object.getType().equals(GameObjectType.SHOT)) {
			ParticleEffect effect = particleManager.create(Resources.PARTICLES_SHOT, true);			
			effects.put(object, effect);
		}
	}

	@Override
	public void onDamage(GameObject object) {
		// TODO Auto-generated method stub
		
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
