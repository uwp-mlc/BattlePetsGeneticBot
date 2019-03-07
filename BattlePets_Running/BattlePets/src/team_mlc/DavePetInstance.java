package team_mlc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import event.AttackEvent;
import event.FightStartEvent;
import event.PlayerEventInfo;
import event.RoundStartEvent;
import player.PetInstance;
import player.PetTypes;
import python.PythonCommunicator;
import settings.PlayerInfo;
import skill.Skills;
import python.PythonCommunicator;

public class DavePetInstance extends PetInstance {
	protected Map<Skills, Integer> rechargingOpponentSkills, jarvisRechargingSkills;
	private int opponentIndex, jarvisIndex;
	private PetTypes opponentType;
	private Map<Integer, Double> randomDifference;
	private double opponentHealth;
	private Random random;
	private int roundsSinceUse;
	private Skills predictedSkill;//Override from the Player... We want to use this one. 
	
	
	private PythonCommunicator pyCom;
	
	public DavePetInstance(PlayerInfo playerInfo)
	{
		super(playerInfo);
		
		// Set all skills to re-charge time of 0
		this.randomDifference = new HashMap<Integer, Double>();
		this.rechargingOpponentSkills = new HashMap<Skills, Integer>();
		this.rechargingOpponentSkills.put(Skills.ROCK_THROW, 0);
		this.rechargingOpponentSkills.put(Skills.SCISSORS_POKE, 0);
		this.rechargingOpponentSkills.put(Skills.PAPER_CUT, 0);
		this.rechargingOpponentSkills.put(Skills.SHOOT_THE_MOON, 0);
		this.rechargingOpponentSkills.put(Skills.REVERSAL_OF_FORTUNE, 0);
		this.random  = new Random(10320l);
		roundsSinceUse = 0;
	}

	@Override
	public Skills chooseSkill() {
		// TODO Auto-generated method stub
		
		return null;
	}

	
	/*
	 * Update takes in the events and remembers all necessary information. 
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object event) 
	{
		if(event instanceof FightStartEvent)
		{
			FightStartEvent fse = (FightStartEvent) event;
			rememberFightStartEvent(fse);
		}
		if(event instanceof RoundStartEvent)
		{
			RoundStartEvent rse = (RoundStartEvent) event;
			rememberRoundStartEvent(rse);
		}         
		if(event instanceof AttackEvent)
		{
			AttackEvent ae = (AttackEvent) event;
			this.rememberAttackEvent(ae);
		}
	}
	
	/**
	 * Remember the fight start information 
	 * @param event
	 */
	private void rememberFightStartEvent(FightStartEvent event) {
		List<PlayerEventInfo> playerEventInfo = event.getPlayerEventInfo();
		this.rechargingOpponentSkills.forEach((key,value) -> value = 0);
		
		// This gets Opponent information (index and name)
		for(int i = 0; i < playerEventInfo.size(); i++)
		{
			PlayerEventInfo playerInfo = playerEventInfo.get(i);
			this.randomDifference.put(i, 0.0);
			if(playerInfo.getPetName().equals(super.getPetName()))
			{
				this.jarvisIndex = i;
				this.opponentIndex = (i + 1) % playerEventInfo.size();
				this.opponentHealth = playerEventInfo.get((i+1) % playerEventInfo.size()).getStartingHp();
				this.opponentType = playerEventInfo.get(this.opponentIndex).getPetType();
				this.randomDifference.put(i, 0.0);
			}
		}
	}

	/**
	 * Remember all information for a round start event. 
	 * @param event
	 */
	private void rememberRoundStartEvent(RoundStartEvent event){
		// DO NOTHING
	}
	
	/**
	 * Remember an attack event information. 
	 * @param event
	 */
	private void rememberAttackEvent(AttackEvent event)
	{
		if(event.getAttackingPlayableIndex() == opponentIndex)
		{
			int rechargeTime = (event.getAttackingSkillChoice() == Skills.REVERSAL_OF_FORTUNE || event.getAttackingSkillChoice() == Skills.SHOOT_THE_MOON) ? 6 : 1;
			this.decrementOpponentRechargeTimes();
			this.rechargingOpponentSkills.put(event.getAttackingSkillChoice(), rechargeTime);
			
			this.randomDifference.put(event.getAttackingPlayableIndex(), this.randomDifference.get(event.getAttackingPlayableIndex()) - event.getRandomDamage());
			this.randomDifference.put(event.getVictimPlayableIndex(), this.randomDifference.get(event.getVictimPlayableIndex()) + event.getRandomDamage());
			this.roundsSinceUse++;
		}
		else if(event.getAttackingPlayableIndex() == jarvisIndex)
		{
			this.opponentHealth -= (event.getConditionalDamage() + event.getRandomDamage());
			
			this.randomDifference.put(event.getAttackingPlayableIndex(), this.randomDifference.get(event.getAttackingPlayableIndex()) - event.getRandomDamage());
			this.randomDifference.put(event.getVictimPlayableIndex(), this.randomDifference.get(event.getVictimPlayableIndex()) + event.getRandomDamage());
		
			Skills s = event.getAttackingSkillChoice();
		}
	}
	
	/**
	 * Decrement the recharging times for all opponent skills. 
	 */
	private void decrementOpponentRechargeTimes()
	{
		for (Map.Entry<Skills, Integer> pair : rechargingOpponentSkills.entrySet())
		{
			int val = pair.getValue();
			Skills s = pair.getKey();
			
			if (val > 0)
			{
				rechargingOpponentSkills.put(s, --val);
			}
		}
	}
}
