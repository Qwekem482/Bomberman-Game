package uet.oop.bomberman.entities.character.enemy.ai;

import java.util.Random;

public class AIEasy{
	Random random = new Random();
	public int calculateDirection() {
		return random.nextInt(4);
	}
}
