package edu.tau.compbio;

import edu.tau.compbio.util.RNACompeteScorer;
import edu.tau.compbio.util.RNAWithPairingProbabilities;
import gnu.getopt.Getopt;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import com.ibm.compbio.seqalign.CustomSmithWaterman;
import com.ibm.compbio.seqalign.SmithWaterman;

public class RNAMotifSandbox {

	public static void main(String[] args) throws IOException{
		
		Getopt go = new Getopt("RNAMotifSandbox",args, "s:p:c:i:m:r:g:o:");
		int match = 5;
		int mismatch = -4;
		int gap = -10;
		double offset = -3;
		String sequenceInputFile = null;
		String probabilitiesInputFile = null;
		String rnaCompeteInputFile = null;
		String rnaCompeteExperimentID = null;
		int c;
		while ((c = go.getopt()) != -1)
		{
			switch (c)
			{
			case 's':
				sequenceInputFile = go.getOptarg();
				break;
			case 'p':
				probabilitiesInputFile = go.getOptarg();
				break;
			case 'c':
				rnaCompeteInputFile = go.getOptarg();
				break;
			case 'i':
				rnaCompeteExperimentID = go.getOptarg();
				break;
			case 'm':
				match = Integer.parseInt(go.getOptarg());
				break;
			case 'r':
				mismatch = Integer.parseInt(go.getOptarg());
				break;
			case 'g':
				gap = Integer.parseInt(go.getOptarg());
				break;
			case 'o':
				offset = Double.parseDouble(go.getOptarg());
				break;
			}
		}
		if(null == sequenceInputFile || null == probabilitiesInputFile || null == rnaCompeteInputFile || null == rnaCompeteExperimentID)
		{
			System.err.println("A mandatory option is missing. check input files and protein ID and run again.");
			System.err.printf("%b %b %b %b\n",null == sequenceInputFile , null == probabilitiesInputFile , null == rnaCompeteInputFile , null == rnaCompeteExperimentID);
			System.exit(2);
		}
		System.out.printf("Working with params:\n\tmatch:%12d\n\tmismatch:%9d\n\tgap:%14d\n",match,mismatch,gap);
		
		
		Vector<RNAWithPairingProbabilities> data = RNAWithPairingProbabilities.readFromFiles(sequenceInputFile, probabilitiesInputFile,true);
		RNACompeteScorer rnaCompete = new RNACompeteScorer(new File(rnaCompeteInputFile), rnaCompeteExperimentID);
				
		int i = 0;
		double totalSequences = data.size();
		double totalScore = 0;
		for (RNAWithPairingProbabilities rna1 : data)
		{
			System.err.printf("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b%4d%% (%4d/%4d)",(int)(100*i/totalSequences),i+1,(int)totalSequences);

			for (RNAWithPairingProbabilities rna2 : data.subList(data.indexOf(rna1)+1, data.size()))
			{
				SmithWaterman a  = new CustomSmithWaterman(rna1.getSequenceArray() ,rna2.getSequenceArray(), match, mismatch, gap, offset, rna1.getProbabilitiesArray(), rna2.getProbabilitiesArray());
				//SmithWaterman a  = new SmithWaterman(rna1.getSequenceArray() ,rna2.getSequenceArray(), match, mismatch, gap);
				String[] res=  a.getAlignedSequences();
				System.out.printf("%d,%d\n",res[0].length(),res[1].length());				
				totalScore += rnaCompete.getScoreForSubsequence(res[0])+rnaCompete.getScoreForSubsequence(res[1]);
				//totalScore += rnaCompete.getScoreForSubsequence(rna1.getSequenceArray())+rnaCompete.getScoreForSubsequence(rna2.getSequenceArray());
				
			}
			i++;
//			if (i>1000)
//			{
//				//return;
//			}
		}
		System.out.printf("\n%f\n",totalScore);
		// Perform the local alignment.
//		String[] aa = a.getAlignment();
//		System.out.println("\nLocal alignment with Smith-Waterman:\n"
//				+ aa[0] + "\n" + aa[1]);

	}

}
