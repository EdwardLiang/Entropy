package infrastructure;

import java.io.Serializable;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import entities.Door;
import entities.Entity;
import entities.Floor;
import entities.Wall;

public class GameMap implements Serializable {
	private static final long serialVersionUID = 42L;
	private ArrayList<Entity> gameElements;
	private BackGround back;
	private World world;
	private Time time;
	private float width;
	private float height;
	private String name;
	private float gravityMag;
	private String originalData;
	private Door door;

	public GameMap(BackGround back, String name, float gravityMag) {
		this.world = new World(new Vec2(0.0f, -gravityMag));
		this.gameElements = new ArrayList<Entity>();
		this.time = new Time(this);
		this.width = back.getWidth();
		this.height = back.getHeight();
		this.name = name;
		this.gravityMag = gravityMag;
		this.back = back;
		this.door = new Door(Util.toPosX(20),Util.toPosY(20));
	}

	public ArrayList<Entity> getElements() {
		return gameElements;
	}

	public BackGround getBack() {/* , you don't know what you're dealing with */
		return back;
	}
	
	public Door getDoor(){
		return door;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public double getPHeight() {
		return back.getPHeight();
	}

	public double getPWidth() {
		return back.getWidth();
	}

	public Boolean isPaused() {
		return time.isPaused();
	}

	public void startTime() {
		time.startTime();
	}

	public void killTime() {
		time.killTime();
	}

	public GameMap(BackGround back) {
		this.name = "Generic";
		this.gravityMag = 30.0f;
		this.world = new World(new Vec2(0.0f, -30.0f));
		this.gameElements = new ArrayList<Entity>();
		this.time = new Time(this);
		this.back = back;
		this.width = back.getWidth();
		this.height = back.getHeight();
		addCoreElements();
		this.door = new Door(Util.toPPosX(20),Util.toPPosY(13));
	}

	public World getPhysics() {
		return world;
	}

	public void reset() {
		stopAll();
		removeAll();
		if (originalData != null) {
			// TODO
			//
			//

		} else {
			addCoreElements();
		}
	}

	public void removeAll() {
		for (Entity a : gameElements) {
			a.removeFromMap();
		}
	}

	public void setVisible(Boolean bool) {
		if (bool == true) {
			back.setVisible(true);
			for (Entity a : gameElements) {
				a.setVisible(true);
			}
			door.setVisible(true);
			App.pS.setScene(App.scene);
			App.pS.show();
		} else {
			back.setVisible(false);
			for (Entity a : gameElements) {
				a.setVisible(false);
			}
			door.setVisible(false);
		}
	}

	private void stopAll() {
		time.timeline.stop();
	}

	// Use Entity's addToMap method. DO NOT DIRECTLY INVOKE THIS METHOD.
	public void addEntity(Entity entity) {
		gameElements.add(entity);
	}

	// Use Entity's removeFromMap method. DO NOT DIRECTLY CALL THIS METHOD.
	public void removeEntity(Entity entity) {
		gameElements.remove(entity);
	}

	public void addCoreElements() {
		Wall left = new Wall(0, height / 2, 1, height);
		Wall right = new Wall(width, height / 2, 1,
				height);
		Wall top = new Wall(width / 2, height, width, 1);
		Floor bottom = new Floor(width / 2, 0, width, 17);
		left.addToMap(this);
		right.addToMap(this);
		top.addToMap(this);
		bottom.addToMap(this);
	}

	public void toggleTime() {
		time.toggleTime();
	}

	@Override
	public String toString() {
		String result = "";
		result += back.toString() + "\n";
		result += name + "\n";
		result += gravityMag + "\n";
		for (Entity a : gameElements) {
			if (a != App.game.getPlayer())
				result += a.toString() + "\n";
		}
		return result;
	}
}
