package rpgBattle;

import java.net.*;

public class HandleSession 
{
	Socket Player1;
	Socket Player2;
	boolean rematch;
	
	private void run()
	{
		// TODO
	}
	
	private boolean isWon()
	{
		// TODO
		return false;
	}
	
	private boolean isFull()
	{
		// TODO
		return false;
	}
	
	private void handleTurn(PlayerCharacter player, PlayerCharacter foe)
	{
		// TODO
	}
	
	private void checkConditions(PlayerCharacter player, PlayerCharacter foe)
	{
		if (!player.getIsDefending())
		{
			if (foe.getBleedFlag())
				player.setBleedCounter(3);
			if (foe.getGuardBreakFlag())
				player.setGuardBreakCounter(3);
			if (foe.getBurnFlag())
				player.setBurnCounter(3);
			if (foe.getBoundFlag())
				player.setBoundCounter(2);
		}			
	}
	
	private int calcFinalDamage(PlayerCharacter player, PlayerCharacter foe, int damage)
	{
		int finalDamage = damage;
		
		if (player.chance(foe.getAvoid()))
		{
			if (!(player.getNoMiss() || foe.getNoAvoid()))
				return 0;
		}		
		if (player.getIsPhysical())
			finalDamage -= foe.getDefense();
		if (player.getIsMagic())
			finalDamage -= foe.getMagicDefense();
		if (foe.getIsDefending())
			finalDamage *= 0.5;
		
		return finalDamage;
	}
	
	private void sendMove(PlayerCharacter player, PlayerCharacter foe)
	{
		// TODO
	}
}
