from pexpect import popen_spawn
from pexpect import exceptions
import os
from gamestate import *


# responses based on stdout value
responses = {'Enter a random seed':gamestate.seed, 
                'Enter a number of players':gamestate.num_players, 
                'Enter a number of fights':gamestate.num_fights,
                'Enter the player type for player 1':gamestate.players[0].player_type,
                'Enter the pet type for player 1':gamestate.players[0].pet_type,
                'Enter a name for player 1':gamestate.players[0].name,
                '{}: Enter a name for your pet'.format(gamestate.players[0].name):gamestate.players[0].pet_name, # if name changes change this line
                'Enter the player type for player 2':'2',
                'Enter the pet type for player 2':'1',
                'Enter a name for player 2':gamestate.players[1].name,
                '{}: Enter a name for your pet'.format(gamestate.players[1].name):gamestate.players[1].pet_name, # if name changes change this line
                'Enter a starting hp':gamestate.players[0].starting_hp,
                'Round 1 Started':None,
                'Battle Started':None,
                'Number of Fights = ':None,
                'Starting HP: {}'.format(gamestate.players[0].starting_hp):None,
                'Pets':None,
                'Pet 1':None,
                'Pet 2':None,
                'Pet Name: {}'.format(gamestate.players[0].pet_name):None,
                'Pet Name: {}'.format(gamestate.players[1].pet_name):None,
                'Pet Type: ':None,
                'Current HP: ':None,
                r'^(\n\r\n)':None,
                }

# pexpect list, this is a list of keys from the reponses dictionary
expected = list(responses.keys())

# create a piped thread for java process 
dirname = os.path.dirname(__file__)
child = popen_spawn.PopenSpawn(['java', '-cp', dirname + '\\bp.jar', "battlepets.GameMain"])

def game_init():
        init_not_done = True
        while init_not_done:
                try:
                        # index of expected string
                        expected_index = child.expect(expected,timeout=1)
                        output = expected[expected_index]
                        #if not output == r'^(\n\r\n)':
                        print(output)
                                
                        # get response from dictionary through list index
                        response = responses[expected[expected_index]]
                        
                        # write response
                        if response:
                                print('Entering: {}'.format(response))
                                child.write("{}\n".format(response).encode())

                                
                        if 'Pet Name: PET2' == output:
                                child.write("{}\n")
                        
                        if 'Round 1 Started' == output:
                                return
                        # clear stdout line
                        child.read_nonblocking(-1,1)
                
                # if expect cannot find match
                # print line
                except exceptions.TIMEOUT:
                        line = child.read_nonblocking(-1,1)
                        if not line == b'\r\n' and not line ==  b'':
                                print(line)

game_init()

def send_att():
        child.write("{}\n".format('1').encode())
        print(child.readline())

send_att()
