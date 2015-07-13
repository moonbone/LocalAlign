#!/usr/bin/python

import os

#cLine = r'java -cp "bin'+os.pathsep+r'lib/*" edu.tau.compbio.RNAMotifSandbox -s "E:\master\Data from yaron\newData\positives\ELAVL1_Kishore.bed.CAPS40.txt" -p "E:\master\Data from yaron\newData\positives\Probs\ELAVL1_Kishore.bed.CAPS40.txt.probs" -c "E:\master\RNACompete\e_scores.txt" -i RNCMPT00121_e_setA -m %d -r %d -g %d'
#cLine = 'Arguments = -s /specific/scratches/scratch/maordan/ELAVL1_Kishore.bed.CAPS40.txt -p /specific/scratches/scratch/maordan/ELAVL1_Kishore.bed.CAPS40.txt.probs -c /specific/scratches/scratch/maordan/e_scores.txt -i RNCMPT00121_e_setA -m %d -r %d -g %d -o 0\nOutput=output/%d_%d_%d.txt\nQueue'
cLine = 'Arguments = -s /specific/scratches/scratch/maordan/PARCLIP_QKI_Hafner2010c_hg19.bed.txt -p /specific/scratches/scratch/maordan/PARCLIP_QKI_Hafner2010c_hg19.bed.txt.probs.csv -c /specific/scratches/scratch/maordan/e_scores.txt -i RNCMPT00047_e_setA -m %d -r %d -g %d -o 0\nOutput=output/%d_%d_%d.txt\nQueue'

fillMissing = False
fillMissing = True

'''
for conf in inputToTest:
	print conf
	os.system(cLine%tuple(conf))
'''

if not fillMissing:
	for i in xrange(40,41):
		for j in xrange(-200,-50,10):
			for k in xrange(-80,-40,5):
				#os.system(cLine%(i,j,k))
				print (cLine%(i,j,k,i,-j,-k))
			

else:
	for i,j,k in [
(40,-90,-70),
(40,-90,-65),



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
