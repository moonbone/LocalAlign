#!/usr/bin/python

import os
'''
inputToTest = [
[5,-4,-2],
[5,-3,-2],
[5,-6,-2],
[5,-5,-2],
[5,-2,-3],
[5,-1,-3],
[5,-4,-3],
[6,-4,-2],
[6,-3,-2],
[6,-6,-2],
[6,-5,-2],
[6,-2,-3],
[6,-1,-3],
[6,-4,-3],
[4,-4,-2],
[4,-3,-2],
[4,-6,-2],
[4,-5,-2],
[4,-2,-3],
[4,-1,-3],
[4,-4,-3],
]
'''
#cLine = r'java -cp "bin'+os.pathsep+r'lib/*" edu.tau.compbio.RNAMotifSandbox -s "E:\master\Data from yaron\newData\positives\ELAVL1_Kishore.bed.CAPS40.txt" -p "E:\master\Data from yaron\newData\positives\Probs\ELAVL1_Kishore.bed.CAPS40.txt.probs" -c "E:\master\RNACompete\e_scores.txt" -i RNCMPT00121_e_setA -m %d -r %d -g %d'
cLine = 'Arguments = -s /specific/scratches/scratch/maordan/ELAVL1_Kishore.bed.CAPS40.txt -p /specific/scratches/scratch/maordan/ELAVL1_Kishore.bed.CAPS40.txt.probs -c /specific/scratches/scratch/maordan/e_scores.txt -i RNCMPT00121_e_setA -m %d -r %d -g %d -o 0\nOutput=output/%d_%d_%d.txt\nQueue'

'''
for conf in inputToTest:
	print conf
	os.system(cLine%tuple(conf))
'''
for i in xrange(40,41):
	for j in xrange(-60,-30,5):
		for k in xrange(-1600,0,20):
			#os.system(cLine%(i,j,k))
			print (cLine%(i,j,k,i,-j,-k))
			#pass

'''
for i,j,k in [
(40,-10,-360),
(40,-5,-520),
(40,-100,-200),
(40,-10,-100),
(40,-100,-20),
(40,-10,-380),
(40,-10,-200),
(40,-35,-800),
(40,-35,-220),
(40,-70,-440),
(40,-60,-500),
(40,-60,-260),
(40,-60,-160),
(40,-55,-780),
(40,-50,-20),
(40,-45,-460),
(40,-45,-440),
(40,-45,-40),
(40,-45,-360),
(40,-45,-320),
(40,-45,-260),
(40,-45,-180),
(40,-40,-780),
(40,-40,-160),
(40,-40,-120),
(40,-35,-600),
(40,-35,-480),
(40,-35,-360),
(40,-35,-320),
(40,-35,-300),
(40,-35,-260),
(40,-35,-180),
(40,-45,-800),
(40,-45,-760),
(40,-45,-600),
(40,-45,-580),
(40,-45,-560),
(40,-45,-520),
(40,-45,-480),
(40,-45,-220),


]:
	print (cLine%(i,j,k,i,-j,-k))

'''
'''
import re
p = r'Working with params:\s*match:\s*(\d+)\s*mismatch:\s*(-?\d+)\s*gap:\s*(-?\d+)\s*(\d+\.\d+)\s*'
out = '\n'.join(map(lambda m:','.join(m),re.findall(p,s)))
open(r'E:\master\code\LocalAlign\something.csv','w').write(out)
'''


'''
pscp -r -pw 6322Ut -v E:\master\scratches_folder\maordan maordan@nova.cs.tau.ac.il:/specific/scratches/scratch
'''
