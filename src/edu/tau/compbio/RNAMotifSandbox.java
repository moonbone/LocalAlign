package edu.tau.compbio;

import java.util.Vector;

import com.ibm.compbio.seqalign.CustomSmithWaterman;

import edu.tau.compbio.util.RNAWithPairingProbabilities;

public class RNAMotifSandbox {

	public static void main(String[] args) {
		int match = 5;
		int mismatch = -4;
		int gap = -10;
		
		Vector<RNAWithPairingProbabilities> data = RNAWithPairingProbabilities.readFromFiles(args[0], args[1]); 
		
		int i = 0;
		for (RNAWithPairingProbabilities rna1 : data)
		{
			for (RNAWithPairingProbabilities rna2 : data)
			{
				CustomSmithWaterman a  = new CustomSmithWaterman(rna1.getSequenceArray() ,rna2.getSequenceArray(), match, mismatch, gap, rna1.getProbabilitiesArray(), rna2.getProbabilitiesArray());
				String[] res=  a.getAlignedSequences();
				System.out.println(res[0] + "\t" + res[1]);
				i++;
				if (i>0)
				{
					return;
				}
			}			
		}
		
		// Perform the local alignment.
//		String[] aa = a.getAlignment();
//		System.out.println("\nLocal alignment with Smith-Waterman:\n"
//				+ aa[0] + "\n" + aa[1]);

	}

}
