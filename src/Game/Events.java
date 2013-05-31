package Game;

import java.util.EnumSet;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

//Multi-Key Event Set use inspiration from http://www.brainoverload.nl/java/167/keypressedeventhandler-with-java-fx-2-0-and-multiple-keys

//KNOWN GLITCH: Multiple Jumps Possible.

public class Events {
	public static final Set<KeyCode> buffer = EnumSet.noneOf(KeyCode.class);
	public static final EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			buffer.add(t.getCode());
			t.consume();
			if (t.getCode() == KeyCode.P) {
				App.game.currentLevel.time.toggleTime();
			}
		}
	};
	public static final EventHandler<KeyEvent> keyRelease = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			Body body = (Body) App.game.player.node.getUserData();
			final KeyEvent t = key;
			buffer.remove(t.getCode());
			if (t.getCode() == KeyCode.A && body.getLinearVelocity().x != 0) {
				Vec2 velocity = new Vec2(0, body.getLinearVelocity().y);
				body.setLinearVelocity(velocity);
			}
			if (t.getCode() == KeyCode.D && body.getLinearVelocity().x != 0) {
				Vec2 velocity = new Vec2(0, body.getLinearVelocity().y);
				body.setLinearVelocity(velocity);
			}
			t.consume();
		}

	};

	public static final Runnable keyThread = new Runnable() {
		@Override
		public void run() {
			Body body = (Body) App.game.player.node.getUserData();
			while (true) {
				if (buffer.contains(KeyCode.W)
						&& body.getLinearVelocity().y == 0
						&& body.getContactList() != null) {
					Vec2 impulse = new Vec2(0, 200.0f);
					Vec2 point = body.getWorldPoint(body.getWorldCenter());
					body.applyLinearImpulse(impulse, point);
				}
				if (buffer.contains(KeyCode.A) && buffer.contains(KeyCode.D)) {
					Vec2 velocity = new Vec2(0, body.getLinearVelocity().y);
					body.setLinearVelocity(velocity);
				} else if (buffer.contains(KeyCode.A)) {
					Vec2 velocity = new Vec2(-20.0f, body.getLinearVelocity().y);
					body.setLinearVelocity(velocity);
				} else if (buffer.contains(KeyCode.D)) {
					Vec2 velocity = new Vec2(20.0f, body.getLinearVelocity().y);
					body.setLinearVelocity(velocity);
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					;
				}
			}
		}

	};
	public static final Runnable r = new Runnable() {
		@Override
		public void run() {
			while (true) {
				Body playerData = (Body) App.game.player.node.getUserData();
				if (!App.game.currentLevel.time.isPaused()) {
					if (Utility.toPixelPosX(playerData.getPosition().x)
							+ App.game.getOffsetX() > Utility.WIDTH / 2 + 20
							&& !(-App.game.getOffsetX() + Utility.WIDTH + 1 > Utility
									.toPixelWidth(App.game.currentLevel.width))) {
						App.game.setOffsetX(App.game.getOffsetX() - 1);
					} else if (Utility.toPixelPosX(playerData.getPosition().x)
							+ App.game.getOffsetX() < Utility.WIDTH / 2 - 20
							&& !(App.game.getOffsetX() - 1 > 0))
						App.game.setOffsetX(App.game.getOffsetX() + 1);

					if (Utility.toPixelPosY(playerData.getPosition().y)
							+ App.game.getOffsetY() > Utility.HEIGHT / 2 + 20
							&& !(App.game.getOffsetY() - 1 < 0))
						App.game.setOffsetY(App.game.getOffsetY() - 1);
					else if (Utility.toPixelPosY(playerData.getPosition().y)
							+ App.game.getOffsetY() < Utility.HEIGHT / 2 - 20
							&& !(App.game.getOffsetY() + Utility.HEIGHT + 1 > Utility
									.toPixelHeight(App.game.currentLevel.height)))
						App.game.setOffsetY(App.game.getOffsetY() + 1);
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}

			}
		}

	};

}
