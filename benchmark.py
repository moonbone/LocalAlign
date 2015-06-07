#!/usr/bin/python

import os

inputToTest = [
[5,-4,-10],
[5,-3,-10],
[5,-6,-10],
[5,-5,-10],
[5,-4,-5],
[5,-3,-5],
[5,-6,-5],
[5,-5,-5],
[5,-4,-3],
[5,-3,-3],
[5,-6,-3],
[5,-5,-3],
]

cLine = r'java -cp "bin:lib/*" edu.tau.compbio.RNAMotifSandbox -s /home/ubuntu/master/AlignerSandbox/ELAVL1_Kishore.TTTTTTTTC.txt -p /home/ubuntu/master/AlignerSandbox/ELAVL1_Kishore.TTTTTTTTC.txt.probs.csv -c /home/ubuntu/master/AlignerSandbox/e_scores.txt -i RNCMPT00121_e_setA -m %d -r %d -g %d'

for conf in inputToTest:
	print conf
	os.system(cLine%tuple(conf))
