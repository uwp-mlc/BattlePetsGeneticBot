from pexpect import popen_spawn
from pexpect import exceptions
import os

player1_name = 'P1'
player2_name = 'P2'


pet1_name = 'PET1'
pet2_name = 'PET2'

# responses based on stdout value
responses = {'Enter a random seed':'1', 
                'Enter a number of players':'2', 
                'Enter a number of fights':'1',
                'Enter the player type for player 1':'2',
                'Enter the pet type for player 1':'1',
                'Enter a name for player 1':player1_name,
                '{}: Enter a name for your pet'.format(player1_name):pet1_name, # if name changes change this line
                'Enter the player type for player 2':'2',
                'Enter the pet type for player 2':'1',
                'Enter a name for player 2':player2_name,
                '{}: Enter a name for your pet'.format(player2_name):pet2_name, # if name changes change this line
                'Enter a starting hp':100,
                'Round 1 Started':None,
                'Battle Started':None,
                'Number of Fights = ':None,
                'Starting HP: ':None,
                'Pets':None,
                'Pet 1':None,
                'Pet 2':None,
                'Pet Name: {}'.format(pet1_name):None,
                'Pet Name: {}'.format(pet2_name):None,
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
                        
                        # clear stdout line
                        child.readline()
                
                # if expect cannot find match
                # print line
                except exceptions.TIMEOUT:
                        line = child.readline()
                        if not line == b'\r\n':
                                print(line)

game_init()

def send_att():
        child.write("{}\n".format('1').encode())
        print(child.readline())

send_att()
