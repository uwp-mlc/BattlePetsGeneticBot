"""
2-input XOR example -- this is most likely the simplest possible example.
"""

from __future__ import print_function
from game import Game
import os
import neat
import gzip, pickle
from neat.six_util import iteritems, itervalues
from pexpect import popen_spawn

# 2-input XOR inputs and expected outputs.
# 3 opponent type
# 3 its type
# 5 cooldowns
# 5 opp cooldown
# 5 last attack
# 5 opp last attack
# 1 health
# 1 opp health
xor_inputs = [(0.0, 0.0), (0.0, 1.0), (1.0, 0.0), (1.0, 1.0)]
xor_outputs = [   (0.0,),     (1.0,),     (1.0,),     (0.0,)]


def eval_genomes(genomes, config):
    for genome_id, genome in genomes:
        # Play the game
        net = neat.nn.FeedForwardNetwork.create(genome, config)
        gameInstance = Game()
        while not gameInstance.game_finished:
            # Output game inputs
            attack = net.activate(gameInstance.get_net_data())
            gameInstance.sendAttack(attack)
        genome.fitness = gameInstance.get_fitness()
        print('Fitness of ' + str(genome_id) + ' is: ' + str(genome.fitness))
        # Evaluate fitness
        # genome.fitness = 4.0
        # net = neat.nn.FeedForwardNetwork.create(genome, config)
        # for xi, xo in zip(xor_inputs, xor_outputs):
        #     output = net.activate(xi)
        #     genome.fitness -= (output[0] - xo[0]) ** 2

class eval:
    best = None
    def eval_genome(self, genome, config):
        # Play the game
        net_random = neat.nn.FeedForwardNetwork.create(genome, config)
        net_best = neat.nn.FeedForwardNetwork.create(eval.best, config)
        gameInstance = Game()
        while not gameInstance.game_finished:
            # Output game inputs
            attack_random = net_random.activate(gameInstance.get_net_data())
            net_best = net_random.activate(gameInstance.get_net_data())
            gameInstance.sendAttack(attack_random)
            gameInstance.sendAttack(net_best)
        Game.remember(gameInstance.gamestate)
        genome.fitness = gameInstance.get_fitness()
        #print('Fitness is: ' + str(genome.fitness))
        if genome.fitness > 0:
            eval.best = genome
        return genome.fitness


def run(config_file):
    # Load configuration.
    config = neat.Config(neat.DefaultGenome, neat.DefaultReproduction,
                         neat.DefaultSpeciesSet, neat.DefaultStagnation,
                         config_file)

    # Create the population, which is the top-level object for a NEAT run.
    p = neat.Population(config)

    # Add a stdout reporter to show progress in the terminal.
    p.add_reporter(neat.StdOutReporter(True))
    stats = neat.StatisticsReporter()
    p.add_reporter(stats)
    p.add_reporter(neat.Checkpointer(5))

    # Run for up to 300 generations.
    
    generations = 10
    evaler = eval()
    Game.child = popen_spawn.PopenSpawn(['java', '-cp', 'working_bp.jar', 'edu.furbiesfighters.gameplay.Main'])
    evaler.best = load_best_genome(config_file)
    pe = neat.ThreadedEvaluator(8, evaler.eval_genome)
    winner = p.run(pe.evaluate, generations)
    pe.stop()

    # Display the winning genome.
    print('\nBest genome:\n{!s}'.format(winner))

    # Show output of the most fit genome against training data.
    print('\nOutput:')
    # winner_net = neat.nn.FeedForwardNetwork.create(winner, config)
    # for xi, xo in zip(xor_inputs, xor_outputs):
    #     output = winner_net.activate(xi)
    #     print("input {!r}, expected output {!r}, got {!r}".format(xi, xo, output))

    # p = neat.Checkpointer.restore_checkpoint('neat-checkpoint-4')
    # p.run(eval_genomes, 10)

def load_best_genome(config_file):
    greatest_genome = None
    p = neat.Checkpointer.restore_checkpoint('neat-checkpoint-9')
    
    for g in itervalues(p.population):
        if greatest_genome == None:
            greatest_genome = g
        if g.fitness > greatest_genome.fitness:
            greatest_genome = g
    return greatest_genome
    
    
    
if __name__ == '__main__':
    # Determine path to configuration file. This path manipulation is
    # here so that the script will run successfully regardless of the
    # current working directory.
    local_dir = os.path.dirname(__file__)
    config_path = os.path.join(local_dir, 'config-feedforward')
    run(config_path)
    config = neat.Config(neat.DefaultGenome, neat.DefaultReproduction,
                         neat.DefaultSpeciesSet, neat.DefaultStagnation,
                         config_path)
    #eval_genome(load_best_genome(config_path), config)