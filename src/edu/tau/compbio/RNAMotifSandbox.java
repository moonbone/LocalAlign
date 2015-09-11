package edu.tau.compbio;

import edu.tau.compbio.util.AlignmentScorePair;
import edu.tau.compbio.util.RNACompeteScorer;
import edu.tau.compbio.util.RNAWithPairingProbabilities;
import gnu.getopt.Getopt;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.TreeSet;
import java.util.Vector;

import com.ibm.compbio.seqalign.CustomSmithWaterman;
import com.ibm.compbio.seqalign.SmithWaterman;

public class RNAMotifSandbox {

	public static void main(String[] args) throws IOException{
		
		Getopt go = new Getopt("RNAMotifSandbox",args, "s:p:c:i:m:r:g:o:t:bx:y:wlu:");
		int match = 5;
		int mismatch = -4;
		int gap = -10;
		double offset = -3;
		String sequenceInputFile = null;
		String probabilitiesInputFile = null;
		String rnaCompeteInputFile = null;
		String rnaCompeteExperimentID = null;
		String method = "pairwise";
		int firstSeqID = 0,secondSeqID = 0;
		boolean findBest = false;
		boolean useClassicSmithWaterman = false;
		boolean outputLengthHist = false;
		double probRatio = 1;
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
			case 't':
				method = go.getOptarg();
				break;
			case 'b':
				findBest = true;
				break;
			case 'x':
				firstSeqID = Integer.parseInt(go.getOptarg());
				break;
			case 'y':
				secondSeqID = Integer.parseInt(go.getOptarg());
				break;
			case 'w':
				useClassicSmithWaterman = true;
				break;
			case 'l':
				outputLengthHist = true;
				break;
			case 'u':
				probRatio = Double.parseDouble(go.getOptarg());
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
		
		int[] lengthHist = new int[100];
		
		Vector<RNAWithPairingProbabilities> data = RNAWithPairingProbabilities.readFromFiles(sequenceInputFile, probabilitiesInputFile,true);
		RNACompeteScorer rnaCompete = new RNACompeteScorer(new File(rnaCompeteInputFile), rnaCompeteExperimentID);
				
		int i = 0;
		double totalSequences = data.size();
		double totalScore = 0;
		TreeSet<AlignmentScorePair> topAlignments = new TreeSet<AlignmentScorePair>(AlignmentScorePair.getComparator());
		TreeSet<AlignmentScorePair> worstAlignments = new TreeSet<AlignmentScorePair>(AlignmentScorePair.getComparator());
		
		//Collections.shuffle(data);
		if(method.equals("pairwise"))
		{
			for (RNAWithPairingProbabilities rna1 : data)
			{
				System.err.printf("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b%4d%% (%4d/%4d)",(int)(100*i/totalSequences),i+1,(int)totalSequences);
	
				for (RNAWithPairingProbabilities rna2 : data.subList(data.indexOf(rna1)+1, data.size()))
				{
					SmithWaterman a  = CustomSmithWaterman.createSmithWatermanSolver(rna1.getSequenceArray() ,rna2.getSequenceArray(), match, mismatch, gap, offset,probRatio, rna1.getProbabilitiesArray(), rna2.getProbabilitiesArray(),useClassicSmithWaterman);
					
					String[] res=  a.getAlignedSequences();
					lengthHist[Math.min(99,res[0].replace("-", "").length())]++;
					lengthHist[Math.min(99,res[1].replace("-", "").length())]++;
					//System.out.printf("%d,%d\n",res[0].length(),res[1].length());				
					double score = rnaCompete.getScoreForSubsequence(res[0])+rnaCompete.getScoreForSubsequence(res[1]);
					totalScore += score;
					
					if(findBest)
					{
						topAlignments.add(new AlignmentScorePair(a.getAlignment(), score));
						worstAlignments.add(new AlignmentScorePair(a.getAlignment(), score));
						if (topAlignments.size() > 10) topAlignments.pollFirst();
						if (worstAlignments.size() > 10) worstAlignments.pollLast();
					}
					
				}
				i++;

			}
			System.out.printf("\n%f\n",totalScore);
		}
		else if (method.equals("specific"))
		{
			SmithWaterman a  = CustomSmithWaterman.createSmithWatermanSolver(data.get(firstSeqID).getSequenceArray() ,data.get(secondSeqID).getSequenceArray(), match, mismatch, gap, offset,probRatio, data.get(firstSeqID).getProbabilitiesArray(), data.get(secondSeqID).getProbabilitiesArray(),useClassicSmithWaterman);
			lengthHist[Math.min(99,a.getAlignedSequences()[0].length())]++;
			lengthHist[Math.min(99,a.getAlignedSequences()[1].length())]++;
			System.out.printf("score:%f\n%s\n%s\n\n",rnaCompete.getScoreForSubsequence(a.getAlignedSequences()[0])+rnaCompete.getScoreForSubsequence(a.getAlignedSequences()[1]),a.getAlignment()[0], a.getAlignment()[1]);
		}
		
		if(findBest)
		{
			System.out.printf("Top alignments:\n=================\n\n");
			for (AlignmentScorePair asp : topAlignments)
			{
				System.out.printf("score:%f\n%s\n%s\n\n",asp.getScore(),asp.getAlignment()[0], asp.getAlignment()[1]);
			}
			
			System.out.printf("\n\n\n\nWorst alignments:\n=================\n\n");
			for (AlignmentScorePair asp : worstAlignments)
			{
				System.out.printf("score:%f\n%s\n%s\n\n",asp.getScore(),asp.getAlignment()[0], asp.getAlignment()[1]);
			}
		}
		
		if(outputLengthHist)
		{
			System.out.print("LengthHistData:\n======================\n\nlengths = [");
			for (int count : lengthHist)
			{
				System.out.printf("%d ",count);
			}
			System.out.print("];");
		}

	}

}
