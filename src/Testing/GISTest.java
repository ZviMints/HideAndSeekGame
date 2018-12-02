package Testing;
import org.junit.jupiter.api.Test;

import Game.Game;

class GISTest {

	@Test
	void testFruitSet() {
		Game game = new Game("./data/game_1543684662657.csv");
		System.out.println(game);
	}
}
