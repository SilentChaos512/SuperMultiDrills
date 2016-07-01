import re
from subprocess import call

pattern = re.compile("[_A-Z]+")

f = open('matlist.txt', 'r')
line = f.readline()
while len(line) != 0:
    match = pattern.match(line)
    if match:
        name = match.group(0)
        name = 'DrillHead_' + name[:1].upper() + name[1:].lower()
        print(name)
        call('python makejson.py layer=1 item ' + name, shell=True)
    line = f.readline()
