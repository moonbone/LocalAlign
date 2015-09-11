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
	totalFor *= ceil(float(e-b)/s)
	for i in xrange(b,e,s):
		yield i
	totalFor /= ceil(float(e-b)/s)

def yxrange(b,e,s=1):
	global totalFor
	global counter
	tempTotalFor = totalFor * ceil(float(e-b)/s)
	
	for i in xrange(b,e,s):
		counter += 1
		print >> sys.stderr, '\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b%4d/%4d (%3d%%)'%(counter,tempTotalFor,(counter/float(tempTotalFor))*100),
		yield i	

#cLine = r'java -cp "bin'+os.pathsep+r'lib/*" edu.tau.compbio.RNAMotifSandbox -s "E:\master\Data from yaron\newData\positives\ELAVL1_Kishore.bed.CAPS40.txt" -p "E:\master\Data from yaron\newData\positives\Probs\ELAVL1_Kishore.bed.CAPS40.txt.probs" -c "E:\master\RNACompete\e_scores.txt" -i RNCMPT00121_e_setA -m %d -r %d -g %d'
#cLine = 'Arguments = -s /specific/scratches/scratch/maordan/ELAVL1_Kishore.bed.CAPS40.txt -p /specific/scratches/scratch/maordan/ELAVL1_Kishore.bed.CAPS40.txt.probs -c /specific/scratches/scratch/maordan/e_scores.txt -i RNCMPT00121_e_setA -m %d -r %d -g %d -o 0\nOutput=output/%d_%d_%d.txt\nQueue'
#cLine = 'Arguments = -s /specific/scratches/scratch/maordan/PARCLIP_QKI_Hafner2010c_hg19.bed.txt -p /specific/scratches/scratch/maordan/PARCLIP_QKI_Hafner2010c_hg19.bed.txt.probs.csv -c /specific/scratches/scratch/maordan/e_scores.txt -i RNCMPT00047_e_setA -m %d -r %d -g %d -o 0\nOutput=output/%d_%d_%d.txt\nQueue'
#cLine = r'java -cp "bin'+os.pathsep+r'lib/*" edu.tau.compbio.BEARBenchmark -s "D:\master\BEAR data\BRAliBase\data-set2_p" -m %d -r %d -g %d -o 0 -u %.2f > foutput\%d_%d_%d_%.2f.txt'
#cLine = 'Arguments = -s /specific/scratches/scratch/maordan/data-set2_p -m %d -r %d -g %d -o 0\nOutput=output/%d_%d_%d.txt\nQueue'
#cLine = 'Arguments = -s /specific/scratches/scratch/maordan/data-set2_p -m %d -r %d -g %d -o 0 -u %.2f\nOutput=output/%d_%d_%d_%.2f.txt\nQueue'
cLine = 'Arguments = -s /a/home/cc/students/cs/maordan/data/maordan/data-set1_pairs/g2intron -m %d -r %d -g %d -o 0 -u %.2f -x .fap \nOutput=output/%d_%d_%d_%.2f.txt\nQueue'
cLine = r'java -cp "bin'+os.pathsep+r'lib/*" edu.tau.compbio.BEARBenchmark -s "D:\master\BEAR data\BRAliBase\data-set1_pairs" -m %d -r %d -g %d -o 0 -u %.2f -x .fap -w > foutput\%d_%d_%d_%.2f.txt'
fillMissing = True
fillMissing = False

'''
for conf in inputToTest:
	print conf
	os.system(cLine%tuple(conf))
'''

m = (100,101)
r = (-100,100,10)
g = (-200,0,10)
ratio = (1,21,1)

if not fillMissing:
	for i in xxrange(*m):
		for j in xxrange(*r):
			for k in xxrange(*g):
				#os.system(cLine%(i,j,k,i,j,k))
				#print (cLine%(i,j,k,i,j,k))
				for u in yxrange(*ratio):
					#print (cLine%(i,j,k,u/10.0,i,j,k,u/10.0))
					os.system(cLine%(i,j,k,u/10.0,i,j,k,u/10.0))
			

else:
	for i,j,k,u in [
(100,-90,-100,0.80),
(100,-60,-160,0.90),
(100,-100,-40,0.60),
(100,-100,-30,1.60),
(100,-100,-180,1.90),
(100,-100,-160,0.50),
(100,-100,-140,1.40),
(100,-100,-140,1.30),
(100,-100,-110,1.40),
(100,-100,-10,1.80),
(100,-100,-10,0.30),


	]:
		#print (cLine%(i,j,k,u,i,j,k,u))
		os.system(cLine%(i,j,k,u,i,j,k,u))


'''
import re
p = r'Working with params:\s*match:\s*(\d+)\s*mismatch:\s*(-?\d+)\s*gap:\s*(-?\d+)\s*(\d+\.\d+)\s*'
out = '\n'.join(map(lambda m:','.join(m),re.findall(p,s)))
open(r'E:\master\code\LocalAlign\something.csv','w').write(out)
'''


'''
pscp -r -pw 6322Ut -v D:\master\scratches_folder\maordan maordan@nova.cs.tau.ac.il:/specific/scratches/scratch
'''
