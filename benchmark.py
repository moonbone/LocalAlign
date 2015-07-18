#!/usr/bin/python

import os
import sys
from math import ceil

global totalFor
global counter
totalFor = 1
counter = 0

def xxrange(b,e,s=1):
	global totalFor
	totalFor *= ceil((e-b)/s)
	for i in xrange(b,e,s):
		yield i

def yxrange(b,e,s=1):
	global totalFor
	global counter
	tempTotalFor = totalFor * ceil((e-b)/s)
	
	for i in xrange(b,e,s):
		counter += 1
		print >> sys.stderr, '\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b%4d/%4d (%3d%%)'%(counter,tempTotalFor,(counter/float(tempTotalFor))*100),
		yield i	

#cLine = r'java -cp "bin'+os.pathsep+r'lib/*" edu.tau.compbio.RNAMotifSandbox -s "E:\master\Data from yaron\newData\positives\ELAVL1_Kishore.bed.CAPS40.txt" -p "E:\master\Data from yaron\newData\positives\Probs\ELAVL1_Kishore.bed.CAPS40.txt.probs" -c "E:\master\RNACompete\e_scores.txt" -i RNCMPT00121_e_setA -m %d -r %d -g %d'
#cLine = 'Arguments = -s /specific/scratches/scratch/maordan/ELAVL1_Kishore.bed.CAPS40.txt -p /specific/scratches/scratch/maordan/ELAVL1_Kishore.bed.CAPS40.txt.probs -c /specific/scratches/scratch/maordan/e_scores.txt -i RNCMPT00121_e_setA -m %d -r %d -g %d -o 0\nOutput=output/%d_%d_%d.txt\nQueue'
#cLine = 'Arguments = -s /specific/scratches/scratch/maordan/PARCLIP_QKI_Hafner2010c_hg19.bed.txt -p /specific/scratches/scratch/maordan/PARCLIP_QKI_Hafner2010c_hg19.bed.txt.probs.csv -c /specific/scratches/scratch/maordan/e_scores.txt -i RNCMPT00047_e_setA -m %d -r %d -g %d -o 0\nOutput=output/%d_%d_%d.txt\nQueue'
#cLine = r'java -cp "bin'+os.pathsep+r'lib/*" edu.tau.compbio.BEARBenchmark -s "E:\master\BEAR data\BRAliBase\data-set1_p" -m %d -r %d -g %d -o 0 > foutput\%d_%d_%d.txt'
cLine = 'Arguments = -s /specific/scratches/scratch/maordan/data-set2_p -m %d -r %d -g %d -o 0\nOutput=output/%d_%d_%d.txt\nQueue'

fillMissing = True
fillMissing = False

'''
for conf in inputToTest:
	print conf
	os.system(cLine%tuple(conf))
'''

if not fillMissing:
	for i in xxrange(50,51):
		for j in xxrange(-50,50,10):
			for k in yxrange(-50,50,10):
				#os.system(cLine%(i,j,k,i,j,k))
				print (cLine%(i,j,k,i,j,k))
			

else:
	for i,j,k in [
(50,-49,-47),
(50,-49,-45),
(50,-49,-33),


	]:
		print (cLine%(i,j,k,i,j,k))


'''
import re
p = r'Working with params:\s*match:\s*(\d+)\s*mismatch:\s*(-?\d+)\s*gap:\s*(-?\d+)\s*(\d+\.\d+)\s*'
out = '\n'.join(map(lambda m:','.join(m),re.findall(p,s)))
open(r'E:\master\code\LocalAlign\something.csv','w').write(out)
'''


'''
pscp -r -pw 6322Ut -v E:\master\scratches_folder\maordan maordan@nova.cs.tau.ac.il:/specific/scratches/scratch
'''
