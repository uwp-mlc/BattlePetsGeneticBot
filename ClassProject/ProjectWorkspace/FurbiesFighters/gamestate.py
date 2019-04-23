from player import Player
import json

class GameState:
  def __init__(self):
    self.p1_cooldowns = {"rock throw":0,
                          "paper cut":0,
                          "scissor poke":0,
                          "shoot the moon":0,
                          "reversal of fortune":0}
    self.p1_health = 100
    self.p2_cooldowns = {"rock throw":0,
                          "paper cut":0,
                          "scissor poke":0,
                          "shoot the moon":0,
                          "reversal of fortune":0}
    self.p2_health = 100

  def remember_turn(self, state):
    '''
    Decrement recharge times, then remember the health & cooldowns
    for each player. 
    Returns a tuple with the array for p1 and p2 cooldowns
    '''
    self.decrement_recharge_times()
    
    p1_last_skill = state["P1_NAME"]["last_skill"]
    self.p1_cooldowns[state["P1_NAME"]["last_skill"]] = self.get_generic_skill_recharge(p1_last_skill)
    self.p1_health = state["P1_NAME"]["current_health"]

    p2_last_skill = state["Jarvis 1"]["last_skill"]
    self.p1_cooldowns[state["P1_NAME"]["last_skill"]] = self.get_generic_skill_recharge(p1_last_skill)
    self.p1_health = state["P1_NAME"]["current_health"]

    return ([self.p1_cooldowns[x] for x in self.p1_cooldowns], [self.p2_cooldowns[x] for x in self.p2_cooldowns])

  def decrement_recharge_times(self):
    '''
    Decrement each recharge time in the player skill dictionaries by 1. If
    recharge time is negative, set it to 0.
    '''
    for key in self.p1_cooldowns:
      value = self.p1_cooldowns[key]
      self.p1_cooldowns[key] = 0 if value <= 0 else value - 1

    for key in self.p2_cooldowns:
      value = self.p2_cooldowns[key]
      self.p2_cooldowns[key] = 0 if value <= 0 else value - 1
      

  def get_generic_skill_recharge(self, skill_string):
    '''
    Gets the recharge time associated with the skill.
    EX: ROF = 6 and RockThrow=1
    '''
    if skill_string == "reversal of fortune":
      return 6
    if skill_string == "shoot the moon":
      return 6

    return 1

'''
gamestate = GameState()

obj = json.loads("{ \"Jarvis 1\" : { \"last_skill\" : \"rock throw\",\"current_health\" : 96.423190 ,\"random_damage\" : 0.9512186050415039,\"conditional_damage\" : 0.0 }, \"P1_NAME\" : {\"last_skill\" : \"reversal of fortune\",\"current_health\" : 99.048781 ,\"random_damage\" : 2.625591278076172,\"conditional_damage\" : 0.9512186050415039 }}")

print(gamestate.remember_turn(obj)[0])
'''

# PER PLAYER
# health
# last attack
# cooldowns