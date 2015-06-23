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
#cLine = 'Arguments = -s /specific/scratches/scratch/maordan/ELAVL1_Kishore.bed.CAPS40.txt -p /specific/scratches/scratch/maordan/ELAVL1_Kishore.bed.CAPS40.txt.probs -c /specific/scratches/scratch/maordan/e_scores.txt -i RNCMPT00121_e_setA -m %d -r %d -g %d -o 0\nOutput=output/%d_%d_%d.txt\nQueue'
cLine = 'Arguments = -s /specific/scratches/scratch/maordan/PARCLIP_QKI_Hafner2010c_hg19.bed.txt -p /specific/scratches/scratch/maordan/PARCLIP_QKI_Hafner2010c_hg19.bed.txt.probs.csv -c /specific/scratches/scratch/maordan/e_scores.txt -i RNCMPT00047_e_setA -m %d -r %d -g %d -o -45\nOutput=output/%d_%d_%d.txt\nQueue'

'''
for conf in inputToTest:
	print conf
	os.system(cLine%tuple(conf))
'''
for i in xrange(40,41):
	for j in xrange(-200,0,10):
		for k in xrange(-200,0,10):
			#os.system(cLine%(i,j,k))
			#print (cLine%(i,j,k,i,-j,-k))
			pass


for i,j,k in [
(40,-50,-100),
(40,-40,-50),
(40,-40,-200),
(40,-40,-20),
(40,-20,-40),
(40,-20,-160),
(40,-180,-10),
(40,-170,-180),
(40,-170,-120),
(40,-150,-50),
(40,-150,-100),
(40,-100,-70),
(40,-100,-200),
(40,-100,-130),
(40,-100,-10),






]:
	print (cLine%(i,j,k,i,-j,-k))


'''
import re
p = r'Working with params:\s*match:\s*(\d+)\s*mismatch:\s*(-?\d+)\s*gap:\s*(-?\d+)\s*(\d+\.\d+)\s*'
out = '\n'.join(map(lambda m:','.join(m),re.findall(p,s)))
open(r'E:\master\code\LocalAlign\something.csv','w').write(out)
'''


'''
pscp -r -pw 6322Ut -v E:\master\scratches_folder\maordan maordan@nova.cs.tau.ac.il:/specific/scratches/scratch
'''
