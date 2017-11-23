//package rpgBattle;

import java.io.*;
import java.net.*;



public class HandleSession implements Runnable
{
	Socket Player1;
	Socket Player2;
	boolean rematch;
	PlayerCharacter player1Char = new PlayerCharacter();
	PlayerCharacter player2Char = new PlayerCharacter();
	
	public HandleSession(Socket player1, Socket player2) {
		
		this.Player1 = player1;
		this.Player2 = player2;
		
		
		
	}
	
	public void run()
	{
		try {
			ObjectInputStream fromPlayer1 = new ObjectInputStream(Player1.getInputStream());
			ObjectOutputStream toPlayer1 = new ObjectOutputStream(Player1.getOutputStream());
			
			ObjectInputStream fromPlayer2 = new ObjectInputStream(Player2.getInputStream());
			ObjectOutputStream toPlayer2 = new ObjectOutputStream(Player2.getOutputStream());
			
			//waiting for each player character object
			while(true) {			
				player1Char = (PlayerCharacter) fromPlayer1.readObject();
				
				if (player1Char.isInitialized()) {
					break;
				}
			}
			
			
			
			while(true) {			
				player2Char = (PlayerCharacter) fromPlayer1.readObject();
				
				if (player2Char.isInitialized()) {
					break;
				}

			}
			
			
			
			
			
			
			
		} catch (IOException ex) {
			System.err.println(ex);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isWon()
	{
		// TODO
		return false;
	}
	
	
	//must check if both players are connected i.e. if we have two playercharacter objects filled
	
	private boolean isFull()
	{
		// TODO
		return false;
	}
	
	private void handleTurn(PlayerCharacter player, PlayerCharacter foe)
	{
		// TODO
		
	}
	
	//
	private void checkConditions(PlayerCharacter player, PlayerCharacter foe)
	{
		if (!foe.getIsDefending())
		{
			if (player.getBleedFlag())
				foe.setBleedCounter(3);
			if (player.getGuardBreakFlag())
				foe.setGuardBreakCounter(3);
			if (player.getBurnFlag())
				foe.setBurnCounter(3);
			if (player.getBoundFlag())
				foe.setBoundCounter(2);
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
	

}