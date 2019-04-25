from player import Player
import json



class GameState:
  TYPES = {'speed':1,'power':2,'intelligence':3}  # might be caps?
  SKILLS = ['rock throw','scissor poke','paper cut','shoot the moon','reversal of fortune']
  
  def __init__(self, me_type, op_type):
    self.FLATTEN = lambda l: [item for sublist in l for item in sublist]
    self.me_cooldowns = {"rock throw":0,
                          "scissor poke":0,
                          "paper cut":0,
                          "shoot the moon":0,
                          "reversal of fortune":0}
    self.me_health = 100
    self.me_last_skill = None
    self.me_player_type = me_type
    self.me_last_conditional = None
    self.me_last_random = None

    self.op_cooldowns = {"rock throw":0,
                          "paper cut":0,
                          "scissor poke":0,
                          "shoot the moon":0,
                          "reversal of fortune":0}
    self.op_health = 100
    self.op_player_type = op_type
    self.op_last_skill = None
    self.op_last_conditional = None
    self.op_last_random = None

  def remember_turn(self, state):
    '''
    Decrement recharge times, then remember the health & cooldowns
    for each player. 
    Returns a tuple with the array for p1 and p2 cooldowns
    '''
    self.decrement_recharge_times()
    
    self.me_last_skill = state["P1_NAME"]["last_skill"]
    self.me_cooldowns[state["P1_NAME"]["last_skill"]] = self.get_generic_skill_recharge(self.me_last_skill)
    self.me_health = state["P1_NAME"]["current_health"]
    self.me_last_random = state["P1_NAME"]["random_damage"]
    self.me_last_conditional = state["P1_NAME"]["conditional_damage"]

    self.op_last_skill = state["Jarvis 1"]["last_skill"]
    self.op_cooldowns[state["Jarvis 1"]["last_skill"]] = self.get_generic_skill_recharge(self.op_last_skill)
    self.op_health = state["Jarvis 1"]["current_health"]
    self.op_last_random = state["Jarvis 1"]["random_damage"]
    self.op_last_conditional = state["Jarvis 1"]["conditional_damage"]
    #return ([self.me_cooldowns[x] for x in self.me_cooldowns], [self.op_cooldowns[x] for x in self.op_cooldowns])


  def get_net_data(self):
    '''
    Generate an array of the game state for all input values
    '''
    net_data = []
    self._to_catagorical
    net_data.append(self._to_catagorical(list(self.TYPES.values()),self.op_player_type))
    net_data.append(self._to_catagorical(list(self.TYPES.values()),self.me_player_type))
    net_data.append(list(self.me_cooldowns.values()))
    net_data.append(list(self.op_cooldowns.values()))
    net_data.append(self._to_catagorical(self.SKILLS,self.me_last_skill))
    net_data.append(self._to_catagorical(self.SKILLS,self.op_last_skill))
    net_data.append([self.me_health])
    net_data.append([self.op_health])
    return self.FLATTEN(net_data)


  def decrement_recharge_times(self):
    '''
    Decrement each recharge time in the player skill dictionaries by 1. If
    recharge time is negative, set it to 0.
    '''
    for key in self.me_cooldowns:
      value = self.me_cooldowns[key]
      self.me_cooldowns[key] = 0 if value <= 0 else value - 1

    for key in self.op_cooldowns:
      value = self.op_cooldowns[key]
      self.op_cooldowns[key] = 0 if value <= 0 else value - 1
      

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

  def _to_catagorical(self, arr, val):

    return_arr = [0] * len(arr)
    if val == None:
      return return_arr
    for i in range(len(arr)):

      if arr[i] == val:

        return_arr[i] = 1

        return return_arr

    raise ValueError('Value was not found in given array!' + '\nArray: ' + str(arr) + '\nValue: '+ val)


'''
gamestate = GameState()

obj = json.loads("{ \"Jarvis 1\" : { \"last_skill\" : \"rock throw\",\"current_health\" : 96.423190 ,\"random_damage\" : 0.9512186050415039,\"conditional_damage\" : 0.0 }, \"P1_NAME\" : {\"last_skill\" : \"reversal of fortune\",\"current_health\" : 99.048781 ,\"random_damage\" : 2.625591278076172,\"conditional_damage\" : 0.9512186050415039 }}")

print(gamestate.remember_turn(obj)[1])
'''

# PER PLAYER
# health
# last attack
# cooldowns