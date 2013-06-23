package keymanagers;

import java.io.IOException;

import entities.Player;

import infrastructure.App;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DCreationKeys extends DefaultKeys {
	public EventHandler<KeyEvent> keyPress = new EventHandler<KeyEvent>() {
		@Override
		public synchronized void handle(KeyEvent key) {
			final KeyEvent t = key;
			if (t.getCode() == KeyCode.R)
				try {
					App.game.getCurrentMap().reset();
					buffer.clear();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (((Player) App.game.getPlayer()).getStatus() == Player.Status.DEAD) {
				return;
			}

			buffer.add(t.getCode());
			t.consume();
			try {
				if (t.getCode() == KeyCode.D) {
					if (App.game.getPlayer() != null) {
						if (((Player) App.game.getPlayer()).getSide() == Player.Side.LEFT) {
							App.game.getPlayer().setVisible(false);
							((Player) App.game.getPlayer())
									.setSide(Player.Side.RIGHT);
							((Player) App.game.getPlayer()).changeNode();
							App.game.getPlayer().setVisible(true);
						}
					}
				}
				if (t.getCode() == KeyCode.A) {
					if (App.game.getPlayer() != null) {
						if (((Player) App.game.getPlayer()).getSide() == Player.Side.RIGHT) {
							App.game.getPlayer().setVisible(false);
							((Player) App.game.getPlayer())
									.setSide(Player.Side.LEFT);
							((Player) App.game.getPlayer()).changeNode();
							App.game.getPlayer().setVisible(true);
						}
					}
				}

				if (t.getCode() == KeyCode.N) {
					if (App.game.getIsAtDoor()) {
						int index = App.game.getMaps().indexOf(
								App.game.getCurrentMap());
						if (index + 1 < App.game.getMaps().size())
							App.game.changeMap(App.game.getMaps()
									.get(index + 1));
					}
				}
				if (t.getCode() == KeyCode.P) {
					App.game.getCurrentMap().toggleTime();
				} else if (t.getCode() == KeyCode.DIGIT1) {
					App.shaker.generateDynamicEntity();
				}
			} catch (Exception e) {
				buffer.clear();
			}

		}
	};
}