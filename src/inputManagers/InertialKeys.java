package inputManagers;

import infrastructure.App;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class InertialKeys extends DefaultKeys {
	public EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			buffer.add(t.getCode());
			t.consume();
			if (t.getCode() == KeyCode.P) {
				App.game.getCurrentMap().toggleTime();
			}
			if(t.getCode() == KeyCode.N){
				if(App.game.getIsAtDoor()){
					int index = App.game.getMaps().indexOf(App.game.getCurrentMap());
					if(index + 1 < App.game.getMaps().size())
						App.game.changeMap(App.game.getMaps().get(index+1));
				}
			}
		}
	};
	public EventHandler<KeyEvent> keyRelease = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			buffer.remove(t.getCode());
			t.consume();
		};
	};
	public Runnable keyThread = new Runnable() {
		@Override
		public void run() {
			while (true) {
				Body body = App.game.getPlayer().getBody();
				if (buffer.contains(KeyCode.W)) {
					Vec2 velocity = new Vec2(0, 50.0f);
					body.setLinearVelocity(velocity);
				}
				if (buffer.contains(KeyCode.S)) {
					Vec2 velocity = new Vec2(0, -50.0f);
					body.setLinearVelocity(velocity);
				}
				if (buffer.contains(KeyCode.A) && buffer.contains(KeyCode.D)) {
					Vec2 velocity = new Vec2(0, 50.0f);
					body.setLinearVelocity(velocity);
				} else if (buffer.contains(KeyCode.A)) {
					Vec2 velocity = new Vec2(-50.0f, 0);
					body.setLinearVelocity(velocity);
				} else if (buffer.contains(KeyCode.D)) {
					Vec2 velocity = new Vec2(50.0f, 0);
					body.setLinearVelocity(velocity);
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					System.out.println("KeyManager stopped");
				}
			}
		}

	};

}
