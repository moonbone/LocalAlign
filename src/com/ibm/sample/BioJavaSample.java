/**
 * Author: Paul Reiners
 */
package com.ibm.sample;

import org.biojava.bio.alignment.NeedlemanWunsch;
import org.biojava.bio.alignment.SequenceAlignment;
import org.biojava.bio.alignment.SmithWaterman;
import org.biojava.bio.alignment.SubstitutionMatrix;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.symbol.AlphabetManager;
import org.biojava.bio.symbol.FiniteAlphabet;
import com.ibm.compbio.seqalign.CustomSmithWaterman;;

/**
 * @author Paul Reiners
 * 
 * Based closely on code by Andreas Dr&auml;ger
 */
public class BioJavaSample {

   /**
    * @param args
    * @throws Exception
    */
   public static void main(String[] args) throws Exception {
      // The alphabet of the sequences. For this example DNA is chosen.
      FiniteAlphabet alphabet = (FiniteAlphabet) AlphabetManager
            .alphabetForName("DNA");
      // Use a substitution matrix with equal scores for every match and every
      // replace.
      int match = 1;
      int replace = -3;
      //SubstitutionMatrix matrix = new SubstitutionMatrix(alphabet, match,replace);
      // Firstly, define the expenses (penalties) for every single operation.
      int insert = 2;
      int delete = 2;
      int gapExtend = 2;
      // Global alignment.
      //SequenceAlignment aligner = new NeedlemanWunsch(match, replace, insert,delete, gapExtend, matrix);
      String q =  "aaaaaaaaccttgtaaaaaaagcaaaaatgacaacagaaaaacaatcttattccgagcattccagtaacttttttgtgtatgtacttagctgtactataagtagttggtttgtatgagatggttaaaAAGGCCAAAGATAAAAGGTTTCTTTTTTTTTCCTTTTTTGtctatgaagttgctgtttattttttttggcctgtttgatgtatgtgtgaaacaatgttgtccaacaataaacaggaattttattttgctgagttgttctaacaaagctgtctcaagcctggtttttctgtttcagtttcttc".toUpperCase();
      String t = "tgtgcccagctggctggaaacctggcagtgataccatcaagcctgatgtccaaaagagcaaagaatatttctccaagcagaagtgagcgctgggctgttttagtgccaggctgcggtgggcagccatgagAACAAAACCTCTTCTGTATTTTTTTTTTCCATTAGTAAAAcacaagacttcagattcagccgaattgtggtgtcttacaaggcaggcctttcctacagggggtggagagaccagcctttcttcctttggtaggaatggcctgagttggcgttgtgggcaggctactggtttgtatgatg".toUpperCase();
      /*
       * Sequence query = DNATools.createDNASequence(q, "query");
      Sequence target = DNATools.createDNASequence(t, "target");
      // Perform an alignment and save the results.
      aligner.pairwiseAlignment(query, // first sequence
            target // second one
            );
      // Print the alignment to the screen
      System.out.println("Global alignment with Needleman-Wunsch:\n"
            + aligner.getAlignmentString());

      // Perform a local alignment from the sequences with Smith-Waterman.
      aligner = new SmithWaterman(match, replace, insert, delete, gapExtend,
            matrix);
      // Perform the local alignment.
      aligner.pairwiseAlignment(query, target);
      System.out.println("\nLocal alignment with Smith-Waterman:\n"
            + aligner.getAlignmentString());
      */
      double[] probs1 = {0.9988392,0.9982936,0.9969889,0.9944065,0.9918667,0.9896918,0.9904453,0.9872002,0.983174,0.8529792,0.5479073,0.5000039,0.6237246,0.6641065,0.9729465,0.925585,0.9011998,0.8923047,0.8949447,0.8348565,0.8526529,0.5154435,0.591683,0.7156883,0.7826885,0.9142842,0.8294057,0.7710586,0.7873478,0.767445,0.5728022,0.458252,0.6546066,0.4853879,0.2319909,0.2715686,0.3902843,0.2191336,0.278811,0.3809499,0.4293695,0.6814668,0.8317614,0.825826,0.7987967,0.776841,0.6468512,0.5063391,0.5638242,0.7748368,0.7168951,0.7422078,0.872558,0.8774253,0.5169948,0.6243644,0.5460421,0.6227416,0.5668535,0.7865274,0.7184062,0.5803526,0.6810744,0.5533891,0.5110099,0.5306379,0.6783836,0.483842,0.5090109,0.3521046,0.5094144,0.4801416,0.3916458,0.3745695,0.3901826,0.529778,0.2602508,0.223356,0.2931203,0.5042744,0.6904948,0.1811687,0.191441,0.2254427,0.1824681,0.8477694,0.8661787,0.7745228,0.7173255,0.703962,0.7582848,0.2745962,0.3076855,0.1731548,0.1484587,0.3624522,0.3537999,0.4480719,0.4552925,0.5537819,0.5250257,0.5358117,0.5449745,0.5612927,0.2267863,0.1771412,0.100324,0.1285889,0.3371489,0.4935443,0.3752314,0.6021791,0.5839468,0.7352589,0.3890068,0.4515285,0.5232803,0.7477552,0.689598,0.60484,0.5168222,0.5296228,0.3410774,0.3493909,0.4589475,0.7452348,0.708603,0.7459434,0.4073119,0.1314315,0.06217851,0.2672123,0.2599639,0.2109076,0.3373964,0.4081023,0.3188861,0.2932043,0.612547,0.3454596,0.2420854,0.2607328,0.3223537,0.1681323,0.4511117,0.8341279,0.9766536,0.9482197,0.5017759,0.5008688,0.5018546,0.5244102,0.7128232,0.8222837,0.7115236,0.6973241,0.7283865,0.7791752,0.4576597,0.3987838,0.4604608,0.5174964,0.5760598,0.6266016,0.6991443,0.7102022,0.3375184,0.42367,0.2952549,0.6364923,0.7330475,0.7750799,0.4231155,0.418698,0.394563,0.4356937,0.6536802,0.8986668,0.6377408,0.534111,0.5589607,0.7329845,0.6976068,0.6422778,0.637036,0.3723748,0.4366521,0.4713805,0.5980441,0.5968718,0.8609986,0.8998927,0.8327167,0.6633894,0.310846,0.2964271,0.1850036,0.6272528,0.3408276,0.2811786,0.2398072,0.2431234,0.2957973,0.6604391,0.464758,0.2578242,0.5818042,0.7454952,0.9516687,0.7276793,0.7140739,0.6189044,0.3922355,0.5082886,0.77027,0.3957858,0.404463,0.1962991,0.1690134,0.3813547,0.9112369,0.6952874,0.4845978,0.3955721,0.3474553,0.3947911,0.6274808,0.6724725,0.6886495,0.3205355,0.162639,0.2434966,0.131943,0.3882955,0.1776822,0.4555458,0.4778495,0.4521996,0.02906785,0.032658,0.02141027,0.5095313,0.3525639,0.3999551,0.9489708,0.9688067,0.9254324,0.9366937,0.8841473,0.9000725,0.8662416,0.4957143,0.5112892,0.7173239,0.2412027,0.2206097,0.2175548,0.717009,0.1877766,0.2916379,0.3185011,0.2033311,0.2397069,0.3681578,0.889875,0.6958815,0.5917596,0.2657802,0.2180034,0.4210377,0.7556816,0.7829864,0.3540217,0.3812347,0.4410989,0.761658,0.8364099,0.7147806,0.5922266,0.5593032,0.7196543,0.3170441,0.1249464,0.04657692,0.9130822,0.7502306,0.6455927,0.4882712,0.641988,0.7500723,0.5349562,0.4604754,0.4297323,0.3792391,0.3168269,0.2513449,0.2706857,0.1665098,0.330849,0.7355498,0.6973584,0.6645793,0.6945442,0.7736334,0.7222766,0.6529661,0.5819115,0.6894026,0.9971557};
      double[] probs2 = {0.8971825,0.8797571,0.7683627,0.5652266,0.6347209,0.73211,0.9244131,0.9801933,0.2463492,0.2620856,0.3234346,0.7494608,0.6245966,0.6480447,0.1180431,0.008003661,0.03158698,0.1745664,0.9951071,0.9346548,0.9284414,0.6413549,0.9296245,0.1562851,0.1547447,0.04907945,0.8254765,0.599475,0.1541215,0.1961617,0.550269,0.6475844,0.9555261,0.9623728,0.4364614,0.1366456,0.08178396,0.238714,0.3627239,0.9603253,0.4436051,0.4077451,0.3913609,0.541884,0.3989374,0.6515424,0.5243662,0.5273324,0.4808737,0.5679272,0.585512,0.6173063,0.8337664,0.8079671,0.7489455,0.6927881,0.449808,0.3064017,0.5124676,0.7467511,0.8254461,0.5350957,0.6121352,0.8510443,0.9178063,0.5279329,0.4762456,0.3154613,0.1899213,0.1426253,0.09577159,0.1473859,0.6320362,0.8792027,0.916406,0.9758819,0.7233783,0.7154618,0.32749,0.2617747,0.3975032,0.4018788,0.3770683,0.1658755,0.2045118,0.952403,0.301292,0.2319687,0.2832975,0.08918232,0.07140832,0.2303753,0.3235322,0.3995289,0.6830342,0.7336036,0.8657544,0.7431018,0.5021599,0.2500024,0.1453149,0.2793855,0.1473482,0.1980276,0.2341905,0.1732396,0.3638073,0.4314318,0.1702429,0.1421809,0.02964037,0.02790917,0.03755912,0.03494556,0.9589658,0.9104638,0.8449443,0.8341187,0.8314526,0.09056404,0.1121127,0.1147996,0.09642193,0.1248247,0.1808108,0.8893769,0.6871137,0.2112706,0.3665379,0.2636385,0.6254273,0.7440894,0.8855438,0.7903868,0.8038273,0.8003316,0.8174812,0.9028866,0.6676272,0.4615722,0.328272,0.6324284,0.5785432,0.6827066,0.5079937,0.4108356,0.6550844,0.9397255,0.7537542,0.6270785,0.6209726,0.631039,0.7764924,0.7988321,0.691518,0.7726306,0.7776159,0.8222315,0.7958677,0.8175666,0.8874605,0.8709319,0.9009281,0.7658377,0.6073964,0.8413762,0.5873286,0.6126762,0.6940802,0.6086137,0.7840407,0.636806,0.7362015,0.4768283,0.3353377,0.2593582,0.239776,0.4176192,0.8881104,0.5888133,0.3488883,0.4162727,0.3407661,0.2273145,0.2545358,0.3012021,0.2809802,0.9578715,0.791729,0.7543077,0.7979433,0.1544065,0.2788285,0.2955007,0.3024546,0.3341199,0.7890058,0.3346665,0.1736094,0.3082827,0.6706073,0.2027948,0.2135706,0.2772145,0.2720455,0.5552223,0.8038647,0.7872415,0.6233141,0.3773595,0.3826773,0.3667089,0.4361809,0.688892,0.4486508,0.3603798,0.3424455,0.1425089,0.4912819,0.2430583,0.2496291,0.1634779,0.1443313,0.4444429,0.3690422,0.2884422,0.8569046,0.5355664,0.3381976,0.3000186,0.2786504,0.2628677,0.6891515,0.1088989,0.1013385,0.1787918,0.2052589,0.1408959,0.1018329,0.859292,0.5255486,0.5240489,0.7710592,0.6338048,0.6617856,0.1254247,0.1611686,0.2448361,0.3362049,0.3304785,0.2470116,0.6832423,0.1269714,0.1102759,0.1514799,0.3739568,0.5494194,0.805626,0.3466165,0.3766037,0.05925139,0.03795541,0.06656777,0.1200649,0.3036768,0.7108054,0.1737437,0.02052058,0.006063321,0.03218314,0.2272532,0.4243672,0.97414,0.7759118,0.61945,0.5705832,0.7951926,0.4164775,0.3945115,0.9785718,0.948084,0.9176881,0.4543141,0.3908736,0.8213999,0.3265738,0.4439797,0.1110863,0.1899695,0.1182476,0.07387364,0.01622509,0.09014399,0.2150366,0.3320343,0.2848749,0.2159467,0.1711713,0.1134846,0.1609843,0.4247542,0.985197,0.6968481,0.9814096,0.9761522,0.9681592,0.963702,0.9491208,0.9175007};
      CustomSmithWaterman a  = new CustomSmithWaterman(q,t, match, replace, -insert, probs1, probs2);
      // Perform the local alignment.
      String[] aa = a.getAlignment();
      System.out.println("\nLocal alignment with Smith-Waterman:\n"
            + aa[0] + "\n" + aa[1]);
   }
}
