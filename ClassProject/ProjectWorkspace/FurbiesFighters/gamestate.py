from player import Player

class GameState:
  def __init__(self):
    GameState.players = []
    GameState.seed = 1
    GameState.num_players = 2
    GameState.num_fights = 1

    GameState.players.append(Player(name='P1', player_type=1, pet_name='PET1', pet_type=1, starting_hp=100))
    GameState.players.append(Player(name='P2', player_type=1, pet_name='PET2', pet_type=1, starting_hp=100))

gamestate = GameState()

# PER PLAYER
# health
# last attack
# cooldowns