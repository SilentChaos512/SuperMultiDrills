import sys
import re
import os

MOD_ID = 'SuperMultiDrills'

def createDirIfNeeded(name):
  if not os.path.exists(name):
    os.makedirs(name)

def createAllDirs():
  #createDirIfNeeded('output')
  createDirIfNeeded('output/blockstates')
  #createDirIfNeeded('output/models')
  createDirIfNeeded('output/models/block')
  createDirIfNeeded('output/models/item')

def writeBlockJSONs(name):
  #blockstate
  f = open('output/blockstates/' + name + '.json', 'w')
  f.write('{\n')
  f.write('  "variants": {\n')
  f.write('    "normal": { "model": "%s:%s" }\n' % (MOD_ID, name))
  f.write('  }\n')
  f.write('}\n')
  f.close()
  #block model
  f = open('output/models/block/%s.json' % name, 'w')
  f.write('{\n')
  f.write('  "parent": "block/cube_all",\n')
  f.write('  "textures": {\n')
  f.write('    "all": "%s:blocks/%s\n' % name)
  f.write('  }\n')
  f.write('}\n')
  f.close()
  #item model
  f = open('output/models/item/%s.json' % name, 'w')
  f.write('{\n')
  f.write('  "parent": "%s:block/%s",\n' % (MOD_ID, name))
  f.write('  "display": {\n')
  f.write('    "thirdperson": {\n')
  f.write('      "rotation": [ 10, -45, 170 ],\n')
  f.write('      "translation": [ 0, 1.5, -2.75 ],\n')
  f.write('      "scale": [ 0.375, 0.375, 0.375 ]\n')
  f.write('    }\n')
  f.write('  }\n')
  f.write('}\n')
  f.close()

def writeItemJSON(name, layer=0):
  f = open('output/models/item/' + name + '.json', 'w')
  f.write('{\n')
  f.write('  "parent": "builtin/generated",\n')
  f.write('  "textures": {\n')
  for i in range(0, layer):
    f.write('    "layer%d": "%s:items/Blank",\n' % (i, MOD_ID))
  f.write('    "layer%d": "%s:items/%s"\n' % (layer, MOD_ID, name))
  f.write('  },\n')
  f.write('  "display": {\n')
  f.write('    "thirdperson": {\n')
  f.write('      "rotation": [-90, 0, 0],\n')
  f.write('      "translation": [0, 1, -3],\n')
  f.write('      "scale": [0.55, 0.55, 0.55 ]\n')
  f.write('    },\n')
  f.write('    "firstperson": {\n')
  f.write('      "rotation": [0, -135, 25 ],\n')
  f.write('      "translation": [0, 4, 2 ],\n')
  f.write('      "scale": [1.7, 1.7, 1.7]\n')
  f.write('    }\n')
  f.write('  }\n')
  f.write('}\n')
  f.write('\n')
  f.close()



numRegex = re.compile("^\d+$")

isBlock = False
name = ''
count = 1
layer = 0

for arg in sys.argv:
  argl = str.lower(arg)
  matchNum = numRegex.match(arg)
  matchLayer = re.compile('layer=').match(argl)

  if argl == 'block':
    isBlock = True
  elif argl == 'item':
    isBlock = False

  if matchNum:
    count = int(matchNum.group(0))
  elif matchLayer:
    layer = int(re.search('\d+', argl).group(0))
  elif arg != 'makejson.py':
    name = arg

if name == '':
  print('No block/item name specified!')
  exit(1)
if count < 1:
  count = 1

createAllDirs()

for i in range(count):
  s = name
  if count > 1:
    s += str(i)
  if isBlock:
    writeBlockJSONs(s)
  else:
    writeItemJSON(s, layer)
#