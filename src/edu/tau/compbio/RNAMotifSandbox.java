package edu.tau.compbio;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import com.ibm.compbio.seqalign.CustomSmithWaterman;

import edu.tau.compbio.util.RNACompeteScorer;
import edu.tau.compbio.util.RNAWithPairingProbabilities;

public class RNAMotifSandbox {

	public static void main(String[] args) throws IOException{
		int match = 5;
		int mismatch = -4;
		int gap = -10;
		
		Vector<RNAWithPairingProbabilities> data = RNAWithPairingProbabilities.readFromFiles(args[0], args[1]);
		RNACompeteScorer rnaCompete = new RNACompeteScorer(new File("W:\\master\\alignerTest\\z_scores.txt"), "RNCMPT00121_z_setA");
		
		int i = 0;
		for (RNAWithPairingProbabilities rna1 : data)
		{
			for (RNAWithPairingProbabilities rna2 : data.subList(data.indexOf(rna1)+1, data.size()))
			{
				CustomSmithWaterman a  = new CustomSmithWaterman(rna1.getSequenceArray() ,rna2.getSequenceArray(), match, mismatch, gap, rna1.getProbabilitiesArray(), rna2.getProbabilitiesArray());
				String[] res=  a.getAlignedSequences();
				//System.out.println(res[0] + "\t" + res[1]);
				
				System.out.printf("%f\t%f\n",rnaCompete.getScoreForSubsequence(res[0]),rnaCompete.getScoreForSubsequence(res[1]));
				
				i++;
				if (i>1000)
				{
				//	return;
				}
			}			
		}
		
		// Perform the local alignment.
//		String[] aa = a.getAlignment();
//		System.out.println("\nLocal alignment with Smith-Waterman:\n"
//				+ aa[0] + "\n" + aa[1]);

	}

}
