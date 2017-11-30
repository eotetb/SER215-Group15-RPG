//package rpgBattle;

import java.io.*;
import java.net.*;

public class HandleSession implements Runnable
{
	Socket Player1;
	Socket Player2;
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
				player2Char = (PlayerCharacter) fromPlayer2.readObject();
				
				if (player2Char.isInitialized()) {
					break;
				}

			}
			
			player2Char = checkPlayers(player1Char, player2Char);
			
			if (player2Char.getCurrentHp() != 0) {
				player1Char = checkPlayers(player2Char,player1Char);
			}
			
			player1Char = determineDefeat(player1Char);
			player2Char = determineDefeat(player2Char);
			
			if (player1Char.getYouAreDefeated() && player2Char.getYouAreDefeated()) {
				player1Char.setIsDraw(true);
				player2Char.setIsDraw(true);
			}
			
			if (player1Char.getYouAreDefeated()) {
				player2Char.setOpponentDefeated(true);
			}
			if (player2Char.getYouAreDefeated()) {
				player1Char.setOpponentDefeated(true);
			}
			
			toPlayer1.writeObject(player1Char);
			toPlayer2.writeObject(player2Char);
			
			
			
			
		} catch (IOException ex) {
			System.err.println(ex);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PlayerCharacter checkPlayers(PlayerCharacter player, PlayerCharacter foe) {
		
		if (player.getIsAttacking()) {
			int damageToFoe = player.attack();
			
			if (foe.getMagicShellCounter() != 0) {
				int reducedDamage = (int) ((.5) * damageToFoe);
				foe.subtractHp(reducedDamage);
			} else {
				foe.subtractHp(damageToFoe);
			}
			
			
		}
		
		if (player.getIsDefending() ) {
			player.defend(); //increases stamina level
		}
		
		if (player.getUsingSkill1() ){
			//knight - parry flag set to true
			//mage - recovers 1/2 of max hp
			//archer - sets focus counter to 6
			//
			player.useSkill1(); 
		}
		
		if (player.getUsingSkill2()) {
			//knight - deal 3.5 times damage to enemy and 10% chance to cause bleeding
			//mage - deal 3 times damage to enemy and 20% chance to cause bleeding
			//archer - deal 3 times damage to enemy
			//
			int damageToFoe = player.useSkill2();
			
			if (foe.getMagicShellCounter() != 0) {
				int reducedDamage = (int) ((.5) * damageToFoe);
				foe.subtractHp(reducedDamage);
			} else {
				foe.subtractHp(damageToFoe);
			}
		}
		
		if (player.getUsingSkill3()) {
			//knight - cause berserk status on self for 7 turns
			//mage - cause magic shell status on self (1/2 damage) for next 4 turns
			//archer - 70% chance to cause bound on enemy
			
			player.useSkill3();
		}
		
		if (player.getUsingSkill4()) {
			//knight - deal 4.5 times damage to enemy and 70% chance to cause guard break on foe
			//mage - deal 7 times damage to enemy and 30% chance to cause burn status on foe also causes 2 bound status effect on player (cannot be avoided)
			//archer - deal 2.5 times damage with 10% chance to cause burn
			//
			int damageToFoe = player.useSkill4();
			
			if (foe.getMagicShellCounter() != 0) {
				int reducedDamage = (int) ((.5) * damageToFoe);
				foe.subtractHp(reducedDamage);
			} else {
				foe.subtractHp(damageToFoe);
			}
		}
		
		if (player.getBleedFlag()) {
			foe.setBleedCounter(3);
		}
		
		if (player.getBurnFlag()) {
			foe.setBurnCounter(3);
		}
		
		if (player.getBoundFlag()) {
			foe.setBoundCounter(2);
		}
		
		if (player.getGuardBreakFlag()) {
			foe.setGuardBreakCounter(3);
		}
		
		return foe;
		
	}
	
	public PlayerCharacter determineDefeat(PlayerCharacter p1) {
		
		if (p1.getCurrentHp() == 0) {
			p1.setYouAreDefeated(true);
		}
	
		return p1;
	}
	

}
