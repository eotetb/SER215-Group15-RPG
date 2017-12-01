//package rpgBattle;

import java.util.Random;

public class PlayerCharacter
{
	//true if character is initialized
	private boolean initialized;
	
	//stats
	protected int maxHp;
	protected int currentHp;
	protected int maxStamina;
	protected int currentStamina;
	protected int strength;
	protected int defense;
	protected int magicDamage;
	protected int magicDefense;
	
	//critical rates and avoid rate
	protected double avoid;
	protected double criticalRate;
	private boolean isCritical = false;
	
	//player number
	private int playerNumber;
	
	//counters for status effects
	private int bleedCounter = 0;
	private int berserkCounter = 0;
	private int guardBreakCounter = 0;
	private int burnCounter = 0;
	private int magicShellCounter = 0;
	private int focusCounter = 0;
	private int boundCounter = 0;
	
	//stack numbers
	private int berserkStack = 0;
	private int guardBreakStack = 0;
	
	//used in defend() method
	private boolean defending = false;
	
	
	//added as client action flags
	private boolean isDefending = false;
	private boolean isAttacking = false;
	private boolean isUsingSkill1 = false;
	private boolean isUsingSkill2 = false;
	private boolean isUsingSkill3 = false;
	private boolean isUsingSkill4 = false;
	
	
	//damage types
	private boolean isPhysical = false;
	private boolean isMagic = false;
	private boolean noAvoid = false;
	private boolean noMiss = false;
	
	//whether or not status effect is active on player
	private boolean bleedFlag = false;
	private boolean guardBreakFlag = false;
	private boolean burnFlag = false;
	private boolean boundFlag = false;
	private boolean parryFlag = false;
	private boolean skillSuccess = false;
	
	//skill info
	private String consoleInfo = "";
	private String skill1Name = "";
	private String skill1Desc = "";
	private String skill2Name = "";
	private String skill2Desc = "";
	private String skill3Name = "";
	private String skill3Desc = "";
	private String skill4Name = "";
	private String skill4Desc = "";
	private int skill1Cost;
	private int skill2Cost;
	private int skill3Cost;
	private int skill4Cost;
	
	//enum for class type
	private ClassType type;
	
	//win conditions
	private boolean youAreDefeated = false;
	private boolean opponentDefeated = false;
	private boolean isDraw = false;
	
	
	
	PlayerCharacter() {
		this.type = ClassType.NoClass;
	}
	
	PlayerCharacter(ClassType type)
	{
		this.playerNumber = playerNumber;
		switch(type)
		{
			case Knight:
				this.initialized = true;
				this.type = ClassType.Knight;
				this.maxHp = 100;
				this.maxStamina = 100;

				
				// Skills
				this.skill1Name = "Slash";
				this.skill1Desc = "Deals 40 damage costs 40 stamina"; 
				this.skill1Cost = 20;
				
				this.skill2Name = "Heal";
				this.skill2Desc = "Heals 40 hp costs 40 stamina";
				this.skill2Cost = 20;
				
				this.skill3Name = "Life Steal";
				this.skill3Desc = "Deals 30 damage and heals 20 hp costs 50 stamina";
				this.skill3Cost = 50;
				
				this.skill4Name = "Ultimate";
				this.skill4Desc = "Deals 200 damage costs 180 stamina";
				this.skill4Cost = 180;
				break;
			case Mage:
				this.initialized = true;
				this.type = ClassType.Mage;
				this.maxHp = 100;
				this.maxStamina = 100;

				
				// Skills
				this.skill1Name = "Heal";
				this.skill1Desc = "Heals user by 50% of maximum hp. Uses 2000 stamina."; 
				this.skill1Cost = 2000;
				
				this.skill2Name = "Blaze";
				this.skill2Desc = "Does 300% magic damage to target, has a 20% chance of causing the burn status for 3 turns. Uses 1500 stamina";
				this.skill2Cost = 1500;
				
				this.skill3Name = "Magic Shell";
				this.skill3Desc = "Gives user magic shell status for 3 turns. Uses 1000 stamina.";
				this.skill3Cost = 1000;
				
				this.skill4Name = "Inferno";
				this.skill4Desc = "Does 700% magic damage to target, has 30% chance of causing the burn status for 3 turns. User's avoid will drop to 0 and will become bound for one turn after skill use. Uses 6000 stamina.";
				this.skill4Cost = 6000;
				break;
			case Archer:
				this.initialized = true;
				this.type = ClassType.Archer;
				this.maxHp = 100;
				this.maxStamina = 100;
;
				
				// Skills
				this.skill1Name = "Focus";
				this.skill1Desc = "Gives user focus status for 5 turns. Uses 2000 stamina."; 
				this.skill1Cost = 2000;
				
				this.skill2Name = "Binding Net";
				this.skill2Desc = "Gives target the bound status for 2 turns at a 70% rate.  Uses 2000 stamina";
				this.skill2Cost = 2000;
				
				this.skill3Name = "Snipe";
				this.skill3Desc = "Does 300% physical damage to target, does not miss. Uses 2500 stamina.";
				this.skill3Cost = 2500;
				
				this.skill4Name = "Flame Arrow";
				this.skill4Desc = "Does 250% magic damage to target. 10% chance of causing burn status for 3 turns. Uses 3000 stamina.";
				this.skill4Cost = 3000;
				break;
			default:
				this.initialized = false;
				this.type = ClassType.NoClass;
				this.maxHp = 10000;
				this.maxStamina = 10000;
				this.strength = 100;
				this.defense = 100;
				this.magicDamage = 100;
				this.magicDefense = 100;
				this.avoid = 10.0;
				this.criticalRate = 10.0;
				
				// Skills
				this.skill1Name = "dummy";
				this.skill1Desc = "null"; 
				this.skill1Cost = 10;
				
				this.skill2Name = "dummy";
				this.skill2Desc = "null"; 
				this.skill2Cost = 10;
				
				this.skill3Name = "dummy";
				this.skill3Desc = "null"; 
				this.skill3Cost = 10;
				
				this.skill4Name = "dummy";
				this.skill4Desc = "null"; 
				this.skill4Cost = 10;
				break;
		}
	}
	
	/**
	 * Resets boolean flags at the start of turn
	 * and clears consoleInfo. 
	 *tells server whether or not to inflict status on other player
	 */
	public void initializeTurn()
	{
		// Reset flags
		isCritical = false;
		isDefending = false;
		isPhysical = false;
		isMagic = false;
		noAvoid = false;
		noMiss = false;
		bleedFlag = false;
		guardBreakFlag = false;
		burnFlag = false;
		boundFlag = false;
		parryFlag = false;
		skillSuccess = false;
		
		// Clear consoleInfo String
		this.consoleInfo = "";	
	}
	
	/**
	 * Does damage over time status effects and
	 * decrements status effects before ending turn.
	 */

	
	/**
	 * Uses strength to calculate damage
	 * or magicDamage for Mages,
	 * takes critical chance into account to multiply damage
	 * returns 0 if player is bound
	 * 
	 * @return 
	 */
	protected int attack()
	{
		double damage = 10;
				
		return (int)damage;
	}
	
	/**
	 * Sets isDefending to true for the next turn
	 * restores 20% of max stamina
	 */
	public void defend()
	{	
			
		setCurrentStamina(currentStamina + 20);		

	}
	
	/**
	 * Skill slot 1:<br/>
	 * Knight - Reflect<br/>
	 * Mage - Heal<br/>
	 * Archer - Focus<br/>
	 * NoClass - DummyAttack<br/>
	 * 
	 * Uses skill for specified class does calculations
	 * and returns the damage and flags.
	 * 
	 * @return 
	 */
	protected int useSkill1()
	{
		double damage = 0;
		
		if (this.boundCounter != 0)
		{
			this.consoleInfo += "Player " + getPlayerNumber() +
			"and can't move.\n";
			this.skillSuccess = true;
			return 0;
		}
		
		if (currentStamina < skill1Cost)
		{
			this.consoleInfo += "Not enough stamina to use this skill.";
			return 0;
		}
		else
		{
			setCurrentStamina(skill1Cost);
			this.skillSuccess = true;
		}
		
		switch(type)
		{
			case Knight:
				this.parryFlag = true;
				break;
			case Mage:
				subtractHp(-(getMaxHp() * (1/2)));
				break;
			case Archer:
				setFocusCounter(6);
				break;
			default:
				damage = getStrength();
				this.isPhysical = true;
				break;			
		}
		
		if (this.skillSuccess == true)
			this.consoleInfo = "Player " + getPlayerNumber() +
			"used " + this.skill1Name + ".\n";
		
		if (damage != 0)
			damage *= doCritical();
		
		return (int)damage;
	}
	
	/**
	 * Skill slot 2:<br/>
	 * Knight - Slash<br/>
	 * Mage - Blaze<br/>
	 * Archer - Binding Net<br/>
	 * NoClass - DummyAttack<br/>
	 * 
	 * Uses skill for specified class does calculations
	 * and returns the damage and flags.
	 * 
	 * @return 
	 */
	protected int useSkill2()
	{
		double damage = 0;
		
		if (this.boundCounter != 0)
		{
			this.consoleInfo += "Player " + getPlayerNumber() +
			"and can't move.\n";
			this.skillSuccess = true;
			return 0;
		}
		
		if (currentStamina < skill2Cost)
		{
			this.consoleInfo += "Not enough stamina to use this skill.";
			return 0;
		}
		else
		{
			setCurrentStamina(skill2Cost);
			this.skillSuccess = true;
		}
		
		switch(type)
		{
			case Knight:
				damage = getStrength() * 3.5;
				this.isPhysical = true;
				if (chance(10))
					this.bleedFlag = true;
				break;
			case Mage:
				damage = getMagicDamage() * 3;
				this.isMagic = true;
				if (chance(20))
					this.burnFlag = true;
				break;
			case Archer:
				damage = getStrength() * 3;
				this.isPhysical = true;
				this.noMiss = true;
				break;
			default:
				damage = getStrength();
				this.isPhysical = true;
				break;			
		}
		
		if (this.skillSuccess == true)
			this.consoleInfo = "Player " + getPlayerNumber() +
			"used " + this.skill2Name + ".\n";
		
		if (damage != 0)
			damage *= doCritical();
		
		return (int)damage;
	}
	
	/**
	 * Skill slot 3:<br/>
	 * Knight - Battle Cry<br/>
	 * Mage - Magic Shell<br/>
	 * Archer - Snipe<br/>
	 * NoClass - DummyAttack<br/>
	 * 
	 * Uses skill for specified class does calculations
	 * and returns the damage and flags.
	 * 
	 * @return 
	 */
	protected int useSkill3()
	{
		double damage = 0;
		
		if (this.boundCounter != 0)
		{
			this.consoleInfo += "Player " + getPlayerNumber() +
			"and can't move.\n";
			this.skillSuccess = true;
			return 0;
		}
		
		if (currentStamina < skill3Cost)
		{
			this.consoleInfo += "Not enough stamina to use this skill.";
			return 0;
		}
		else
		{
			setCurrentStamina(skill3Cost);
			this.skillSuccess = true;
		}
		
		switch(type)
		{
			case Knight:
				setBerserkCounter(4);
				break;
			case Mage:
				setMagicShellCounter(4);
				break;
			case Archer:
				if (chance(70))
					this.boundFlag = true;
				break;
			default:
				damage = getStrength();
				this.isPhysical = true;
				break;			
		}
		
		if (this.skillSuccess == true)
			this.consoleInfo = "Player " + getPlayerNumber() +
			"used " + this.skill3Name + ".\n";
		
		if (damage != 0)
			damage *= doCritical();
		
		return (int)damage;
	}
	
	/**
	 * Skill slot 4:<br/>
	 * Knight - Guard Splitter<br/>
	 * Mage - Inferno<br/>
	 * Archer - Flame Arrow<br/>
	 * NoClass - DummyAttack<br/>
	 * 
	 * Uses skill for specified class does calculations
	 * and returns the damage and flags.
	 * 
	 * @return 
	 */
	protected int useSkill4()
	{
		double damage = 0;
		
		if (this.boundCounter != 0)
		{
			this.consoleInfo += "Player " + getPlayerNumber() +
			"and can't move.\n";
			this.skillSuccess = true;
			return 0;
		}
		
		if (currentStamina < skill4Cost)
		{
			this.consoleInfo += "Not enough stamina to use this skill.";
			return 0;
		}
		else
		{
			setCurrentStamina(skill4Cost);
			this.skillSuccess = true;
		}
		
		switch(type)
		{
			case Knight:
				damage = getStrength() * 4.5;
				this.isPhysical = true;
				if (chance(70))
					this.guardBreakFlag = true;
				break;
			case Mage:
				damage = getMagicDamage() * 7;
				this.isMagic = true;
				if (chance(30))
					this.burnFlag = true;
				setBoundCounter(2);
				this.noAvoid = true;
				break;
			case Archer:
				damage = getMagicDamage() * 2.5;
				this.isMagic = true;
				if (chance(10))
					this.burnFlag = true;
				break;
			default:
				damage = getStrength();
				this.isPhysical = true;
				break;			
		}
		
		if (this.skillSuccess == true)
			this.consoleInfo = "Player " + getPlayerNumber() +
			"used " + this.skill4Name + ".\n";
		
		if (damage != 0)
			damage *= doCritical();
		
		return (int)damage;
	}

	public int getMaxHp()
	{
		return this.maxHp;
	}
	
	public int getCurrentHp()
	{
		return this.currentHp;
	}
	
	public int getMaxStamina()
	{
		return this.maxStamina;
	}
	
	public int getCurrentStamina()
	{
		return this.currentStamina;
	}
	
	public int getStrength()
	{
		int currentStrength = this.strength;
		if (this.burnCounter != 0 | this.bleedCounter != 0)
			currentStrength -= currentStrength * 0.1;
		if (this.berserkCounter != 0)
			currentStrength += currentStrength * (0.5 * this.berserkStack);
		
		return currentStrength;
	}
	
	public int getDefense()
	{
		int currentDefense = this.defense;
		if (this.guardBreakCounter != 0)
			currentDefense -= currentDefense * (0.25 * this.guardBreakStack);
		
		return currentDefense;
	}
	
	public int getMagicDamage()
	{
		return this.magicDamage;
	}
	
	public int getMagicDefense()
	{
		return this.magicDefense;
	}
	
	public double getAvoid()
	{
		return this.avoid;
	}
	
	public double getCriticalRate()
	{	
		return this.criticalRate;
	}
	
	public int getBleedCounter()
	{
		return this.bleedCounter;
	}
	
	public int getBerserkCounter()
	{
		return this.berserkCounter;
	}
	
	public int getGuardBreakCounter()
	{
		return this.guardBreakCounter;
	}
	
	public int getBurnCounter()
	{
		return this.burnCounter;
	}
	
	public int getMagicShellCounter()
	{
		return this.magicShellCounter;
	}
	
	public int getFocusCounter()
	{
		return this.focusCounter;
	}
	
	public int getBoundCounter()
	{
		return this.boundCounter;
	}
	
	public boolean getIsCritical()
	{
		return this.isCritical;
	}
	
	//getter methods for client flags
	public boolean getIsDefending()
	{
		return this.isDefending;
	}
	
	public boolean getIsAttacking()
	{
		return this.isAttacking;
	}
	
	public boolean getUsingSkill1()
	{
		return this.isUsingSkill1;
	}
	
	public boolean getUsingSkill2()
	{
		return this.isUsingSkill2;
	}
	
	public boolean getUsingSkill3()
	{
		return this.isUsingSkill3;
	}
	
	public boolean getUsingSkill4()
	{
		return this.isUsingSkill4;
	}
	
	public boolean getYouAreDefeated() {
		return this.youAreDefeated;
	}
	
	public boolean getOpponentDefeated() {
		return this.opponentDefeated;
	}
	
	public boolean getIsDraw() {
		return this.isDraw;
	}
	
	//getter methods for skill strings
		public String getSkill1Name()
		{
			return this.skill1Name;
		}
		
		public String getSkill2Name()
		{
			return this.skill2Name;
		}
		
		public String getSkill3Name()
		{
			return this.skill3Name;
		}
		
		public String getSkill4Name()
		{
			return this.skill4Name;
		}
		
		
		public String getSkill1Desc()
		{
			return this.skill1Desc;
		}
		
		public String getSkill2Desc()
		{
			return this.skill2Desc;
		}
		
		public String getSkill3Desc()
		{
			return this.skill3Desc;
		}
		
		public String getSkill4Desc()
		{
			return this.skill4Desc;
		}
	
	//setter methods for client flags
	public void setIsAttacking(boolean x) {
		isAttacking = x;
	}
	public void setIsDefending(boolean x) {
		isDefending= x;
	}
	public void setIsUsingSkill1(boolean x) {
		isUsingSkill1 = x;
	}
	
	public void setIsUsingSkill2(boolean x) {
		isUsingSkill2 = x;
	}
	public void setIsUsingSkill3(boolean x) {
		isUsingSkill3 = x;
	}
	public void setIsUsingSkill4(boolean x) {
		isUsingSkill4 = x;
	}
	public void setYouAreDefeated(boolean x) {
		youAreDefeated = x;
	}
	public void setOpponentDefeated(boolean x) {
		opponentDefeated = x;
	}
	public void setIsDraw(boolean x) {
		isDraw = x;
	}
	
	public boolean getIsPhysical()
	{
		return this.isPhysical;
	}
	
	public boolean getIsMagic()
	{
		return this.isMagic;
	}
	
	public boolean getNoAvoid()
	{
		return this.noAvoid;
	}
	
	public boolean getNoMiss()
	{
		return this.noMiss;
	}
	
	public boolean getBleedFlag()
	{
		return this.bleedFlag;
	}
	
	public boolean getGuardBreakFlag()
	{
		return this.guardBreakFlag;
	}
	
	public boolean getBurnFlag()
	{
		return this.burnFlag;
	}
	
	public boolean getBoundFlag()
	{
		return this.boundFlag;
	}
	
	public boolean getParryFlag()
	{
		return this.parryFlag;
	}
	
	public int getPlayerNumber()
	{
		return this.playerNumber;
	}
	
	public ClassType getClassType() {
		return this.type;
	}
	
	public boolean isInitialized() {
		return this.initialized;
	}
	
	/**
	 * Negative values in hpChange represent recovering hp
	 * currentHp cannot be greater than maxHp
	 * 
	 * @param hpChange
	 */
	public void subtractHp(int hpChange)
	{
		this.currentHp -= hpChange;
		if (this.currentHp > this.maxHp)
			this.currentHp = this.maxHp;
		else if (this.currentHp < 0)
			this.currentHp = 0;
	}

	/**
	 * Negative values in staminaChange represent recovering stamina
	 * currentStamina cannot be greater than maxStamina
	 * 
	 * @param staminaChange
	 */
	public void setCurrentStamina(int staminaChange)
	{
		this.currentStamina -= staminaChange;
		if (this.currentStamina > this.maxStamina)
			this.currentStamina = this.maxStamina;
	}
	
	/**
	 * Maximum number of turns for bleed is 3
	 * 
	 * @param turns
	 */
	public void setBleedCounter(int turns)
	{
		if (turns == -1 && this.bleedCounter == 0)
			return;
		else if (turns == -1 && this.bleedCounter == 1)
			this.consoleInfo += "Player " + getPlayerNumber() +
			"'s bleeding has subsided.\n";
		this.bleedCounter += turns;
		if (this.bleedCounter > 3)
			this.bleedCounter = 3;
	}
	
	/**
	 * Maximum number of turns for berserk is 7
	 * The bonus damage effect of berserk can be
	 * stacked a maximum of 3 times.
	 * 
	 * @param turns
	 */
	public void setBerserkCounter(int turns)
	{
		if (turns == -1 && this.berserkCounter == 0)
			return;
		else if (turns == -1 && this.berserkCounter == 1)
			this.consoleInfo += "Player " + getPlayerNumber() +
			"'s berkserk state has ended.\n";
		this.berserkCounter += turns;
		if (turns > 0)
		{
			this.berserkStack++;
			if (this.berserkStack > 3)
				this.berserkStack = 3;
		}
		if (this.berserkCounter > 7)
			this.berserkCounter = 7;
		
		if (this.berserkCounter == 0)
			this.berserkStack = 0;
	}
	
	/**
	/**
	 * Maximum number of turns for Guard Break is 3
	 * The defense reduction effect of Guard Break can be
	 * stacked a maximum of 4 times.
	 * 
	 * @param turns
	 */
	public void setGuardBreakCounter(int turns)
	{
		if (turns == -1 && this.guardBreakCounter == 0)
			return;
		else if (turns == -1 && this.guardBreakCounter == 1)
			this.consoleInfo += "Player " + getPlayerNumber() +
			"'s guard has returned to normal.\n";
		this.guardBreakCounter += turns;
		if (turns > 0)
		{
			this.guardBreakStack++;
			if (this.guardBreakStack > 4)
				this.guardBreakStack = 4;
		}
		if (this.guardBreakCounter > 3)
			this.guardBreakCounter = 3;
		
		if (this.guardBreakCounter == 0)
			this.guardBreakStack = 0;
	}
	
	/**
	 * Maximum number of turns for burn is 3
	 * 
	 * @param turns
	 */
	public void setBurnCounter(int turns)
	{
		if (turns == -1 && this.burnCounter == 0)
			return;
		else if (turns == -1 && this.burnCounter == 1)
			this.consoleInfo += "Player " + getPlayerNumber() +
			"'s burn has recovered.\n";		
		this.burnCounter += turns;
		if (this.burnCounter > 3)
			this.burnCounter = 3;
	}
	
	/**
	 * Maximum number of turns for magic shell is 4
	 * 
	 * @param turns
	 */
	public void setMagicShellCounter(int turns)
	{
		if (turns == -1 && this.magicShellCounter == 0)
			return;
		else if (turns == -1 && this.magicShellCounter == 1)
			this.consoleInfo += "Player " + getPlayerNumber() +
			"'s magic shell wears off.\n";
		this.magicShellCounter += turns;
		if (this.magicShellCounter > 4)
			this.magicShellCounter = 4;
	}
	
	/**
	 * Maximum number of turns for focus is 6
	 * 
	 * @param turns
	 */
	public void setFocusCounter(int turns)
	{
		if (turns == -1 && this.focusCounter == 0)
			return;
		else if (turns == -1 && this.focusCounter == 1)
			this.consoleInfo += "Player " + getPlayerNumber() +
			"'s focus returns to normal.\n";
		this.focusCounter += turns;
		if (this.focusCounter > 6)
			this.focusCounter = 6;
	}
	
	/**
	 * Maximum number of turns for bound is 2
	 * 
	 * @param turns
	 */
	public void setBoundCounter(int turns)
	{
		if (turns == -1 && this.boundCounter == 0)
			return;
		else if (turns == -1 && this.boundCounter == 1)
			this.consoleInfo += "Player " + getPlayerNumber() +
			" is no longer bound.\n";
		this.boundCounter += turns;
		if (this.focusCounter > 2)
			this.focusCounter = 2;
	}
	
	/**
	 * Rolls a random number between 0 and 100
	 * returns true if the value is less than or
	 * equal to the given value
	 * 
	 * @param number
	 * @return
	 */
	public boolean chance(double number)
	{
		Random random = new Random();
		int randValue = random.nextInt(100);
		if (number <= randValue)
			return true;
		else
			return false;
	}
	
	/**
	 * Sets bleedFlag to true if player
	 * successfully uses bleed. Server 
	 * handles the rest of the action on 
	 * the other player.
	 */
	public void doBleed()
	{
		this.bleedFlag = true;
	}
	
	/**
	 * Sets guardBreakFlag to true if player
	 * successfully uses guard break. Server 
	 * handles the rest of the action on 
	 * the other player.
	 */
	public void doGuardBreak()
	{
		this.guardBreakFlag = true;
	}
	
	/**
	 * Sets burnFlag to true if player
	 * successfully uses burn. Server 
	 * handles the rest of the action on 
	 * the other player.
	 */
	public void doBurn()
	{
		this.burnFlag = true;
	}
	
	/**
	 * Sets boundFlag to true if player
	 * successfully uses bound. Server 
	 * handles the rest of the action on 
	 * the other player.
	 */
	public void doBound()
	{
		this.boundFlag = true;
	}
	
	/**
	 * Sets parryFlag to true if player
	 * successfully uses parry. Server 
	 * handles the rest of the action on 
	 * the other player.
	 */
	public void doParry()
	{
		this.parryFlag = true;
	}
	
	/**
	 * Checks if the user should do a critical hit
	 * Returns the critical multiplier.
	 * 
	 * @return
	 */
	public double doCritical()
	{
		if (chance(getCriticalRate()))
		{
			this.consoleInfo = "Critical Hit!\n";
			if (this.focusCounter != 0)
				return 3;
			else
				return 1.5;
		}
		else
			return 1;
	}
	
	public String writeConsoleInfo(String consoleInfo)
	{
		return consoleInfo + "\n";
	}
	
	public enum ClassType
	{
		NoClass, Knight, Mage, Archer
	}
}
