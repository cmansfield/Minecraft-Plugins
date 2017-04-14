
import sys
import os


if(len(sys.argv) != 2): exit()

with open(sys.argv[1]) as file:
	for line in file:
		line = line.rstrip()
		if(line == ''): continue
		print('SOUNDS_MAP.put("{}", Sound.{});'.format(line, line))
