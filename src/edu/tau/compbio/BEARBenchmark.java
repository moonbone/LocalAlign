package edu.tau.compbio;

import edu.tau.compbio.util.AlignmentPair;
import edu.tau.compbio.util.RNAWithPairingProbabilities;
import gnu.getopt.Getopt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.ibm.compbio.seqalign.CustomSmithWaterman;
import com.ibm.compbio.seqalign.SmithWaterman;



public class BEARBenchmark 
{

	
public static void main(String[] args) throws IOException{
		
		Getopt go = new Getopt("RNAMotifSandbox",args, "s:m:r:g:o:wfu:x:");
		int match = 5;
		int mismatch = -4;
		int gap = -10;
		double offset = -3;
		Path sequenceInputDir = null;
		boolean useClassicSmithWaterman = false;
		boolean outputResultAsFiles = false;
		double probRatio = 1;
		int c;
		String filesSuffix = ".fa";
		while ((c = go.getopt()) != -1)
		{
			switch (c)
			{
			case 's':
				sequenceInputDir = Paths.get(go.getOptarg());
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
			case 'w':
				useClassicSmithWaterman = true;
				break;
			case 'f':
				outputResultAsFiles = true;
				break;
			case 'u':
				probRatio = Double.parseDouble(go.getOptarg());
				break;
			case 'x':
				filesSuffix = go.getOptarg();
				break;
			}
		}
		if(null == sequenceInputDir )
		{
			System.err.println("A mandatory option is missing. check input files and protein ID and run again.");
			System.exit(2);
		}
		System.out.printf("Working with params:\n\tmatch:%12d\n\tmismatch:%9d\n\tgap:%14d\n",match,mismatch,gap);
		
		String[] folderNames = {"."}; //{"rRNA",	"tRNA",	"U5","g2intron"};//change  between these according to data set.
		float allCountFound = 0;
		int allCountTotal = 0;
		for (String sequenceCollectionFolderName : folderNames)
		{
			Path collectionFolder = sequenceInputDir.resolve(sequenceCollectionFolderName);
			Path unalignedFolder = collectionFolder.resolve("unaligned");
			Path structuralFolder = collectionFolder.resolve("structural");
			Path alignedFolder = collectionFolder.resolve("aligned");
			if(!alignedFolder.toFile().exists())
			{
				alignedFolder.toFile().mkdir();
			}
			
			//System.out.printf("\n\n%s:\n==============\n\n",sequenceCollectionFolderName);
			//for each file name in structured folder:
			for (File file : structuralFolder.toFile().listFiles())
			{
				if(!file.getName().endsWith(filesSuffix))
				{
					continue;
				}
			
				//get pairs list matrix from "structural" folder
				Collection<AlignmentPair>[][] refMatrix = getRefMatrix(structuralFolder.resolve(file.getName()));
				
				//read data from unaligned files (and probabilities)
				Vector<RNAWithPairingProbabilities> data = RNAWithPairingProbabilities.readFromFiles(unalignedFolder.resolve(file.getName()).toString() ,unalignedFolder.resolve(file.getName()+".probs.csv").toString() );
				//System.out.printf("%s\n",file.getName());
				//for each pair in file:
				int i = 0;
				int j = 0;
				for (RNAWithPairingProbabilities rna1 : data)
				{
					j=i+1;
					for (RNAWithPairingProbabilities rna2 : data.subList(data.indexOf(rna1)+1, data.size()))
					{
						//calculate alignment
						SmithWaterman a  = CustomSmithWaterman.createSmithWatermanSolver(rna1.getSequenceArray() ,rna2.getSequenceArray(), match, mismatch, gap, offset,probRatio, rna1.getProbabilitiesArray(), rna2.getProbabilitiesArray(),useClassicSmithWaterman);

						if(outputResultAsFiles)
						{
							BufferedWriter writer = new BufferedWriter(new FileWriter(alignedFolder.toString() + File.separatorChar + (file.getName())));
							writer.write(rna1.getID());
							writer.write('\n');
							writer.write(a.getAlignedSequences()[0]);
							writer.write('\n');
							writer.write(rna2.getID());
							writer.write('\n');
							writer.write(a.getAlignedSequences()[1]);
							writer.write('\n');
							writer.close();
						}
						
						//get pairs list from alignment
						Collection<AlignmentPair> pairs = getAlignmentAsPairs(a.getAlignedSequences());
						
						//get ref pairs from pre-calculated matrix
						Collection<AlignmentPair> refPairs = refMatrix[i][j];
						
						//count pairs also in the list in pairs list matrix (reference)
						int countFound = 0;
						int countTotal = 0;
						for (AlignmentPair p : refPairs)
						{
							if( p.first >= 0)
							{
								countTotal++;
//								allCountTotal++;
								if (pairs.contains(p))
								{
									countFound++;
//									allCountFound++;
								}
							}
							if( p.second >= 0)
							{
								countTotal++;
//								allCountTotal++;
								if (pairs.contains(p))
								{
									countFound++;
//									allCountFound++;
								}
							}
						}
						++allCountTotal;
						allCountFound += ((float)countFound)/countTotal;
						
						//System.out.printf("%12d\t%12d\n",countFound,countTotal);
						j++;
					}
					i++;

				}
				//break;
					
				
			}
		}
		
		System.out.printf("%12f\t%12d\n",allCountFound,allCountTotal);
		System.out.printf("Score: %12f\n",(double)(allCountFound)/allCountTotal);
		
	
	}

	private static Collection<AlignmentPair>[][] getRefMatrix(Path maFilePath) 
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(maFilePath.toFile()));
			Vector<String> v = new Vector<String>(5);
			String line;
			while( null != (line = reader.readLine()))
			{
				if(line.startsWith(">"))
				{
					v.add("");
					continue;
				}
				else
				{
					v.set(v.size()-1, v.lastElement()+line.trim());
				}
			}
			reader.close();
			
			Collection<AlignmentPair>[][] res = new Collection[v.size()][v.size()];
			
			for(int i = 0 ; i < v.size(); i++)
			{
				for(int j = 0 ; j < v.size(); j++)
				{
					res[i][j] = getAlignmentAsPairs(new String[] {v.elementAt(i),v.elementAt(j)});
				}
			}
			return res;
		}
		catch (FileNotFoundException e)
		{
			System.err.printf("File not found: %s\n",maFilePath);
			return null;
		}
		catch(IOException e)
		{
			System.err.printf("IO Error in %s\n",maFilePath);
			return null;
		}
		
	}

	private static Collection<AlignmentPair> getAlignmentAsPairs(String[] alignment)
	{
		String first = alignment[0];
		String second = alignment[1];
		
		int i=0,j=0;
		
		int maxIter = first.length();
		
		char ci;
		char cj;
		
		List<AlignmentPair> resList = new LinkedList<AlignmentPair>();
		
		for (int iter = 0; iter < maxIter; iter++)
		{
			ci = first.charAt(iter);
			cj = second.charAt(iter);
			if(ci != '-' || cj != '-')
			{
				if(ci == '-' )
				{
					resList.add(new AlignmentPair(-1, j));
				}
				else if(cj == '-' )
				{
					resList.add(new AlignmentPair(i, -1));
				}
				else
				{
					resList.add(new AlignmentPair(i, j));
				}
			}
			if(ci != '-')
			{
				i++;
			}
			if(cj != '-')
			{
				j++;
			}
		}
		
		return resList;
	}
		
	
}
