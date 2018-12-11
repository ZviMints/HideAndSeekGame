package Testing;
import org.junit.jupiter.api.Test;

import Game.Game;

class GameTest {

	@Test
	void testFruitSet() {
		Game game = new Game("./data/game_1543684662657.csv");
		System.out.println(game);
	}
}
