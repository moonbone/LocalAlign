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
cLine = r'java -cp "bin'+os.pathsep+r'lib/*" edu.tau.compbio.RNAMotifSandbox -s "E:\master\Data from yaron\newData\positives\ELAVL1_Kishore.bed.CAPS40.txt" -p "E:\master\Data from yaron\newData\positives\Probs\ELAVL1_Kishore.bed.CAPS40.txt.probs" -c "E:\master\RNACompete\e_scores.txt" -i RNCMPT00121_e_setA -m %d -r %d -g %d'
'''
for conf in inputToTest:
	print conf
	os.system(cLine%tuple(conf))
'''
for i in xrange(7,10):
	for j in xrange(-4,0):
		for k in xrange(-4,0):
			os.system(cLine%(i,j,k))