/*
 * Copyright 2011-2016 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 */
package ch.ethz.globis.tinspin;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import ch.ethz.globis.tinspin.data.AbstractTest;
//import ch.ethz.globis.phtree.util.PhTreeStats;
//import ch.ethz.globis.tinspin.data.TestPoint;
import ch.ethz.globis.tinspin.wrappers.Candidate;

public class TestStats implements Serializable, Cloneable {

	/** Edge length of the populated data area. */
	//private final double DEFAULT_LEN = (1L<<31)-1;
	//private final double DEFAULT_LEN = 1000.0;
	public static final double DEFAULT_DATA_LEN = 1.0;
	/** Average edge length of the data rectangles. */
	public static final double DEFAULT_RECT_LEN = 0.00001;

	public static final int DEFAULT_DUPLICATES = 1;

	/** How often are tests repeated? */
	public static int DEFAULT_CFG_REPEAT = 3;
	
	public static int DEFAULT_W_QUERY_SIZE = 1_000;
	public static int DEFAULT_N_WINDOW_QUERY = 1_000; //number of range queries
	public static int DEFAULT_N_POINT_QUERY = 100_000; //number of point queries
	public static int DEFAULT_N_KNN_QUERY = 10_000;
	public static int DEFAULT_N_UPDATES = 100_000;
	public static int DEFAULT_N_UPDATE_CYCLES = 10;


	/** */
	private static final long serialVersionUID = 1L;
	public TestStats(TestHandle test, IndexHandle index, int N, int DIM, double param1) {
		this(test, index, N, DIM, test.isRangeData(), param1, 0);
	}
	
	public TestStats(TestHandle test, IndexHandle index, int N, int DIM, boolean isRangeData,
			double param1, double param2) {
		this.cfgNEntries = N;
		this.cfgNDims = DIM;
		this.INDEX = index;
		this.TEST = test;
		this.SEEDmsg = "" + seed;
		this.isRangeData = isRangeData;
		this.param1 = param1;
		this.param2 = param2;
	}

	public TestStats setDuplicates(int duplicates) {
		cfgDuplicates = duplicates;
		return this;
	}

	//configuration
	/** how often to repeat the test. */
	int cfgNRepeat = DEFAULT_CFG_REPEAT;
	public int cfgNBits = 64; //default
	public int cfgNDims;
	public int cfgNEntries;

	/** How often kNN queries are repeated. This is reduced
	 * automatically with increasing dimensionality. */
	public int cfgKnnQueryBaseRepeat = DEFAULT_N_KNN_QUERY;
	public int cfgPointQueryRepeat = DEFAULT_N_POINT_QUERY;
	public int cfgUpdateRepeat = DEFAULT_N_UPDATE_CYCLES;
	public int cfgUpdateSize = DEFAULT_N_UPDATES;
	public int cfgWindowQueryRepeat = DEFAULT_N_WINDOW_QUERY;
	/** Expected average number of entries in a query result. */
	public int cfgWindowQuerySize = DEFAULT_W_QUERY_SIZE;
	
	/** length of the populated data area */
	public double cfgDataLen = DEFAULT_DATA_LEN;
	/** length of the data rectangles */
	public double cfgRectLen = DEFAULT_RECT_LEN;

	/** Number of point duplicates. n=1 means no duplicates, n=2 means every point exists 2 times. */
	public int cfgDuplicates = DEFAULT_DUPLICATES;

	public final IndexHandle INDEX;
	public final TestHandle TEST;
	public String SEEDmsg;
	public long seed;
	public final double param1;
	public double param2;
	public String paramStr;
	public String paramStr2;
	public boolean paramEnforceGC = true;
	public final boolean isRangeData;
	public boolean isMultimap = false;
	/**
	 * Tests are repeated until a minimum amount of time has passed. 
	 * This avoid problems with very fasts implementations (timing, not warmed up). 
	 */
	public double minimumMsPerTest = 2000;

	Class<? extends AbstractTest> testClass;
	Class<? extends Candidate> indexClass;

	// results: times
	public long statTGen;
	public long statTLoad;
	public long statTUnload;
	public long statTq1;
	public long statTq1E;
	public long statTq2;
	public long statTq2E;
	public long statTqp1;
	public long statTqp1E;
	public long statTqp2;
	public long statTqp2E;
	public long statTqk1_1;
	public long statTqk1_1E;
	public long statTqk1_2;
	public long statTqk1_2E;
	public long statTqk10_1;
	public long statTqk10_1E;
	public long statTqk10_2;
	public long statTqk10_2E;
	public long statTu1;
	public long statTu1E;
	public long statTu2;
	public long statTu2E;
	//per second
	public long statPSLoad;
	public long statPSUnload;
	public long statPSq1;
	public long statPSq1E;
	public long statPSq2;
	public long statPSq2E;
	public long statPSqp1;
	public long statPSqp1E;
	public long statPSqp2;
	public long statPSqp2E;
	public long statPSqk1_1;
	public long statPSqk1_1E;
	public long statPSqk1_2;
	public long statPSqk1_2E;
	public long statPSqk10_1;
	public long statPSqk10_1E;
	public long statPSqk10_2;
	public long statPSqk10_2E;
	public long statPSu1;
	public long statPSu1E;
	public long statPSu2;
	public long statPSu2E;
	public int statNnodes;
	public long statNpostlen;
	public int statNNodeAHC;
	public long statNDistCalc;
	public long statNDistCalc1NN;
	public long statNDistCalcKNN;
	public int statNNodeNT;
	public int statNNodeInternalNT;
	public int statNq1;
	public int statNq2;
	public int statNqp1;
	public int statNqp2;
	public double statDqk1_1;
	public double statDqk1_2;
	public double statDqk10_1;
	public double statDqk10_2;
	public int statNu1;
	public int statNu2;
	public long statSCalc;
	public long statSjvmF;
	public long statSjvmE;
	public long statGcDiffL;
	public long statGcTimeL;
	public long statGcDiffWq;
	public long statGcTimeWq;
	public long statGcDiffPq;
	public long statGcTimePq;
	public long statGcDiffUp;
	public long statGcTimeUp;
	public long statGcDiffK1;
	public long statGcTimeK1;
	public long statGcDiffK10;
	public long statGcTimeK10;
	public long statGcDiffUl;
	public long statGcTimeUl;
	public String assortedInfo = "";

	Throwable exception = null;

	public void setFailed(Throwable t) {
		SEEDmsg = SEEDmsg + "-F";
		exception = t;
	}

	public void setSeed(long seed) {
		this.seed = seed;
		SEEDmsg = Long.toString(seed);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public TestStats cloneStats() {
		try {
			return (TestStats) clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public static String[] testHeaderOld() {
		String D = "\t"; //delimiter

		String[][] h2 = {
//				{"", "", "",    "",     "",  "Space", "", "", "", "", 
//					"Times", "", "", "", "", "", "", "", "", "", "", "", 
//					"BSTStats", "", "", "", "", "Result sizes for verification", 
//					"", "", "", "", "", "", "", "", "", "", "", "", "GC"},
				{"Index", "data", "dim", "bits", "N", "calcMem", 
						"memory", "memory/n", "gen", 
						"load", "load/n", "q1/n", "q2/n", "pq1/n", "pq2/n", "up1/n", "up2/n", 
						"1-NN1", "1-NN2", "10-NN1", "10-NN2", 
						"unload", "unload/n", 
						"nodes", "postLen", "AHC", "NT", "NTinternal", 
						"q1-n", "q2-n", "q1p-n", "q2p-n", "up1-n", "up2-n", 
						"d1-1NN", "d2-1NN", "d1-kNN", "d2-kNN", 
						"load-s", "load-t", "w-query-s", "w-query-t", 
						"p-query-s", "p-query-t", "update-s", "update-t", 
						"1-NN-s", "1-NN-t", "10-NN-s", "10-NN-t", 
						"unload-s", "unload-t", "msg"},	
//				{"", "", "",    "",     "",  "MiB", "MiB", "MiB", "bytes", 
//							"[ms]", "[ms]", 
//							"[ns/result]", "[ns/call]", "[ns/call]", "[ns/call]", 
//							"[ns/call]", "", "", "", "", "", "", "", "", "", "", 
//							"", "", "", "", "", "", "", "", 
//							"[MB]", "[ms]", "[MB]", "[ms]", 
//							"[MB]", "[ms]", "[MB]", "[ms]", 
//							"[MB]", "[ms]", "[MB]", "[ms]", "[MB]", "[ms]"}		
		};
		
		String[] ret = new String[h2.length];
		for (int i = 0; i < h2.length; i++) {
			StringBuilder sb = new StringBuilder();
			for (String col: h2[i]) {
				sb.append(col);
				sb.append(D);
			}
			ret[i] = sb.toString();
		}
		return ret;
	}
	
	public static String[] testHeaderNew() {
		String D = "\t"; //delimiter

		String[][] h2 = {
//				{"", "", "",    "",     "",  "Space", "", "", "", "", 
//					"Times", "", "", "", "", "", "", "", "", "", "", "", 
//					"BSTStats", "", "", "", "", "Result sizes for verification", 
//					"", "", "", "", "", "", "", "", "", "", "", "", "GC"},
				{"Index", "data", "dim", "bits", "N", 
						"memory", "memory/n", "gen", 
						"load/s", "wq1/s", "wq2/s", "pq1/s", "pq2/s", 
						"1-NN1/s", "1-NN2/s", "10-NN1/s", "10-NN2/s",
						"up1/s", "up2/s",
						"unload/s", 
						"dummy1", "dummy2", 
						"dummy3", "dummy4", 
						"nodes", "postLen", "AHC", "NT", "NTinternal", 
						"wq1-n", "wq2-n", "pq1-n", "pq2-n", 
						"1-NN1-d", "1-NN2-2", "10-NN1-d", "10-NN2-d", 
						"up1-n", "up2-n",
						"distCalc-n", "distCalc1NN-n", "distCalcKNN-n", 
						"dummy-n",
						"load-s", "load-t", "w-query-s", "w-query-t", 
						"p-query-s", "p-query-t", "update-s", "update-t", 
						"1-NN-s", "1-NN-t", "10-NN-s", "10-NN-t", 
						"unload-s", "unload-t", 
						"wq-size", "wq-repeat",
						"msg"},	
//				{"", "", "",    "",     "",  "MiB", "MiB", "MiB", "bytes", 
//							"[ms]", "[ms]", 
//							"[ns/result]", "[ns/call]", "[ns/call]", "[ns/call]", 
//							"[ns/call]", "", "", "", "", "", "", "", "", "", "", 
//							"", "", "", "", "", "", "", "", 
//							"[MB]", "[ms]", "[MB]", "[ms]", 
//							"[MB]", "[ms]", "[MB]", "[ms]", 
//							"[MB]", "[ms]", "[MB]", "[ms]", "[MB]", "[ms]"}		
		};
		
		String[] ret = new String[h2.length];
		for (int i = 0; i < h2.length; i++) {
			StringBuilder sb = new StringBuilder();
			for (String col: h2[i]) {
				sb.append(col);
				sb.append(D);
			}
			ret[i] = sb.toString();
		}
		return ret;
	}
	
	public String testDescription1() {
		String ret = "";
		ret += INDEX.name();
		ret += "-" + (isRangeData ? "R" : "P");
		ret += "-" + (isMultimap ? "MM" : "M");
		return ret;
	}
	
	public String testDescription2() {
		return TEST.name() + "(" + cfgDuplicates + ","+ param1 + "," + param2 + "," + paramStr + "," + paramStr2 + ")";
	}
	
	@Override
	public String toString() {
		return toStringNew();
	}
	
	public String toStringOld() {
		String D = "\t"; //delimiter
		String ret = "";

		ret += testDescription1() + "-" + SEEDmsg + D;
		ret += testDescription2() + D;

		ret += cfgNDims + D + cfgNBits + D + cfgNEntries + D; 
		ret += (statSCalc>>20) + D + statSjvmF + D + statSjvmE + D; 
		ret += statTGen + D;
		
		//times
		ret += statTLoad + D + (statTLoad*1000000/cfgNEntries) + D;
		//			ret += statTq1 + D + statTq1E + D + statTq2 + D + statTq2E + D + statTqp1 + D + statTqp1E + D + statTqp2 + D + statTqp2E + D;
		ret += statTq1E + D + statTq2E + D + statTqp1E + D + statTqp2E + D; 
		ret += statTu1E + D + statTu2E + D;
		ret += statTqk1_1E + D + statTqk1_2E + D;
		ret += statTqk10_1E + D + statTqk10_2E + D;
		ret += statTUnload + D + (statTUnload*1000000/cfgNEntries) + D;
		
		//Result sizes, etc
		ret += statNnodes + D + statNpostlen + D + statNNodeAHC + D + statNNodeNT + D + statNNodeInternalNT + D;
		ret += statNq1 + D + statNq2 + D + statNqp1 + D + statNqp2 + D;
		ret += statNu1 + D + statNu2 + D;
		ret += statDqk1_1 + D + statDqk1_2 + D + statDqk10_1 + D + statDqk10_2 + D;

		//GC
		ret += statGcDiffL/1000000 + D + statGcTimeL + D;
		ret += statGcDiffWq/1000000 + D + statGcTimeWq + D;
		ret += statGcDiffPq/1000000 + D + statGcTimePq + D;
		ret += statGcDiffUp/1000000 + D + statGcTimeUp + D;
		ret += statGcDiffK1/1000000 + D + statGcTimeK1 + D;
		ret += statGcDiffK10/1000000 + D + statGcTimeK10 + D;
		ret += statGcDiffUl/1000000 + D + statGcTimeUl + D;
		ret += assortedInfo;
		if (exception != null) {
			ret += D + exception.getMessage();
		}
		return ret;
	}
	
	public String toStringNew() {
		String D = "\t"; //delimiter
		String ret = "";

		ret += testDescription1() + "-" + SEEDmsg + D;
		ret += testDescription2() + D;

		ret += cfgNDims + D + cfgNBits + D + cfgNEntries + D; 
		ret += statSjvmF + D + statSjvmE + D; 
		ret += statTGen + D;
		
		//throughput
		ret += statPSLoad + D;
		//			ret += statTq1 + D + statTq1E + D + statTq2 + D + statTq2E + D + statTqp1 + D + statTqp1E + D + statTqp2 + D + statTqp2E + D;
		ret += statPSq1 + D + statPSq2 + D;
		ret += statPSqp1 + D + statPSqp2 + D; 
		ret += statPSqk1_1 + D + statPSqk1_2 + D;
		ret += statPSqk10_1 + D + statPSqk10_2 + D;
		ret += statPSu1E + D + statPSu2E + D;
		ret += statPSUnload + D;
		ret += 0 + D + 0 + D;  //Dummy
		ret += 0 + D + 0 + D;  //Dummy
		
		//Result sizes, etc
		ret += statNnodes + D + statNpostlen + D + statNNodeAHC + D + statNNodeNT + D + statNNodeInternalNT + D;
		ret += statNq1 + D + statNq2 + D + statNqp1 + D + statNqp2 + D;
		ret += statDqk1_1 + D + statDqk1_2 + D + statDqk10_1 + D + statDqk10_2 + D;
		ret += statNu1 + D + statNu2 + D;
		ret += statNDistCalc + D + statNDistCalc1NN + D;  
		ret += statNDistCalcKNN + D;
		ret += 0 + D;  //Dummy

		//GC
		ret += statGcDiffL/1000000 + D + statGcTimeL + D;
		ret += statGcDiffWq/1000000 + D + statGcTimeWq + D;
		ret += statGcDiffPq/1000000 + D + statGcTimePq + D;
		ret += statGcDiffUp/1000000 + D + statGcTimeUp + D;
		ret += statGcDiffK1/1000000 + D + statGcTimeK1 + D;
		ret += statGcDiffK10/1000000 + D + statGcTimeK10 + D;
		ret += statGcDiffUl/1000000 + D + statGcTimeUl + D;
		ret += cfgWindowQuerySize + D + cfgWindowQueryRepeat + D;
		ret += assortedInfo;
		if (exception != null) {
			ret += D + exception.getMessage();
		}
		return ret;
	}
	
	public void setN(int N) {
		cfgNEntries = N;
	}
	
	public int getN() {
		return cfgNEntries;
	}
	
	public static TestStats aggregate(List<TestStats> stats) {
		TestStats t1 = stats.get(0);
		//TestStats avg = new TestStats(t1.TEST, t1.INDEX, t1.cfgNEntries, t1.cfgNDims, 
		//		t1.isRangeData, t1.param1);
		TestStats avg = t1.cloneStats();
//		avg.cfgNBits = t1.cfgNBits;
//		avg.cfgNEntries = t1.cfgNEntries;
//		avg.param2 = t1.param2;
//		avg.paramStr = t1.paramStr;
//		avg.paramWQSize = t1.paramWQSize;

		int cnt = 1;
		for (int i = 1; i < stats.size(); i++) {
			TestStats t = stats.get(i);

//			avg.testClass = t.testClass;
//			avg.indexClass = t.indexClass;

			if (t.exception != null) {
				//skip failed results
				continue;
			}
			avg.statTGen += t.statTGen;
			avg.statTLoad += t.statTLoad;
			avg.statTUnload += t.statTUnload;
			avg.statTq1 += t.statTq1;
			avg.statTq1E += t.statTq1E;
			avg.statTq2 += t.statTq2;
			avg.statTq2E += t.statTq2E;
			avg.statTqp1 += t.statTqp1;
			avg.statTqp1E += t.statTqp1E;
			avg.statTqp2 += t.statTqp2;
			avg.statTqp2E += t.statTqp2E;
			avg.statTqk1_1 += t.statTqk1_1;
			avg.statTqk1_1E += t.statTqk1_1E;
			avg.statTqk1_2 += t.statTqk1_2;
			avg.statTqk1_2E += t.statTqk1_2E;
			avg.statTqk10_1 += t.statTqk10_1;
			avg.statTqk10_1E += t.statTqk10_1E;
			avg.statTqk10_2 += t.statTqk10_2;
			avg.statTqk10_2E += t.statTqk10_2E;
			avg.statTu1 += t.statTu1;
			avg.statTu1E += t.statTu1E;
			avg.statTu2 += t.statTu2;
			avg.statTu2E += t.statTu2E;

			avg.statPSLoad += t.statPSLoad;
			avg.statPSUnload += t.statPSUnload;
			avg.statPSq1 += t.statPSq1;
			avg.statPSq1E += t.statPSq1E;
			avg.statPSq2 += t.statPSq2;
			avg.statPSq2E += t.statPSq2E;
			avg.statPSqp1 += t.statPSqp1;
			avg.statPSqp1E += t.statPSqp1E;
			avg.statPSqp2 += t.statPSqp2;
			avg.statPSqp2E += t.statPSqp2E;
			avg.statPSqk1_1 += t.statPSqk1_1;
			avg.statPSqk1_1E += t.statPSqk1_1E;
			avg.statPSqk1_2 += t.statPSqk1_2;
			avg.statPSqk1_2E += t.statPSqk1_2E;
			avg.statPSqk10_1 += t.statPSqk10_1;
			avg.statPSqk10_1E += t.statPSqk10_1E;
			avg.statPSqk10_2 += t.statPSqk10_2;
			avg.statPSqk10_2E += t.statPSqk10_2E;
			avg.statPSu1 += t.statPSu1;
			avg.statPSu1E += t.statPSu1E;
			avg.statPSu2 += t.statPSu2;
			avg.statPSu2E += t.statPSu2E;

			avg.statNnodes += t.statNnodes;
			//avg.statNBits += t.statNBits;
			//avg.statNDims += t.statNDims;
			//avg.statNEntries += t.statNEntries;
			avg.statNpostlen += t.statNpostlen;
			avg.statNNodeAHC += t.statNNodeAHC;
			avg.statNNodeNT += t.statNNodeNT;
			avg.statNNodeInternalNT += t.statNNodeInternalNT;
			avg.statNDistCalc += t.statNDistCalc;
			avg.statNDistCalc1NN += t.statNDistCalc1NN;
			avg.statNDistCalcKNN += t.statNDistCalcKNN;
			avg.statNq1 += t.statNq1;
			avg.statNq2 += t.statNq2;
			avg.statNqp1 += t.statNqp1;
			avg.statNqp2 += t.statNqp2;
			avg.statDqk1_1 += t.statDqk1_1;
			avg.statDqk1_2 += t.statDqk1_2;
			avg.statDqk10_1 += t.statDqk10_1;
			avg.statDqk10_2 += t.statDqk10_2;
			avg.statNu1 += t.statNu1;
			avg.statNu2 += t.statNu2;
			avg.statSCalc += t.statSCalc;
			avg.statSjvmF += t.statSjvmF;
			avg.statSjvmE += t.statSjvmE;
			avg.statGcDiffL += t.statGcDiffL;
			avg.statGcTimeL += t.statGcTimeL;
			avg.statGcDiffWq += t.statGcDiffWq;
			avg.statGcTimeWq += t.statGcTimeWq;
			avg.statGcDiffPq += t.statGcDiffPq;
			avg.statGcTimePq += t.statGcTimePq;
			avg.statGcDiffUp += t.statGcDiffUp;
			avg.statGcTimeUp += t.statGcTimeUp;
			avg.statGcDiffUl += t.statGcDiffUl;
			avg.statGcTimeUl += t.statGcTimeUl;
			//we just use the info of the last test run
			avg.assortedInfo = t.assortedInfo;
			cnt++;
		}

		avg.statTGen /= (double)cnt;
		avg.statTLoad /= (double)cnt;
		avg.statTUnload /= (double)cnt;
		avg.statTq1 /= (double)cnt;
		avg.statTq1E /= (double)cnt;
		avg.statTq2 /= (double)cnt;
		avg.statTq2E /= (double)cnt;
		avg.statTqp1 /= (double)cnt;
		avg.statTqp1E /= (double)cnt;
		avg.statTqp2 /= (double)cnt;
		avg.statTqp2E /= (double)cnt;
		avg.statTqk1_1 /= (double)cnt;
		avg.statTqk1_1E /= (double)cnt;
		avg.statTqk1_2 /= (double)cnt;
		avg.statTqk1_2E /= (double)cnt;
		avg.statTqk10_1 /= (double)cnt;
		avg.statTqk10_1E /= (double)cnt;
		avg.statTqk10_2 /= (double)cnt;
		avg.statTqk10_2E /= (double)cnt;
		avg.statTu1 /= (double)cnt;
		avg.statTu1E /= (double)cnt;
		avg.statTu2 /= (double)cnt;
		avg.statTu2E /= (double)cnt;

		avg.statPSLoad /= (double)cnt;
		avg.statPSUnload /= (double)cnt;
		avg.statPSq1 /= (double)cnt;
		avg.statPSq1E /= (double)cnt;
		avg.statPSq2 /= (double)cnt;
		avg.statPSq2E /= (double)cnt;
		avg.statPSqp1 /= (double)cnt;
		avg.statPSqp1E /= (double)cnt;
		avg.statPSqp2 /= (double)cnt;
		avg.statPSqp2E /= (double)cnt;
		avg.statPSqk1_1 /= (double)cnt;
		avg.statPSqk1_1E /= (double)cnt;
		avg.statPSqk1_2 /= (double)cnt;
		avg.statPSqk1_2E /= (double)cnt;
		avg.statPSqk10_1 /= (double)cnt;
		avg.statPSqk10_1E /= (double)cnt;
		avg.statPSqk10_2 /= (double)cnt;
		avg.statPSqk10_2E /= (double)cnt;
		avg.statPSu1 /= (double)cnt;
		avg.statPSu1E /= (double)cnt;
		avg.statPSu2 /= (double)cnt;
		avg.statPSu2E /= (double)cnt;

		avg.statNnodes /= (double)cnt;
		//avg.statNBits /= (double)cnt;
		//avg.statNDims /= (double)cnt;
		//avg.statNEntries /= (double)cnt;
		avg.statNpostlen /= (double)cnt;
		avg.statNNodeAHC /= (double)cnt;
		avg.statNNodeNT /= (double)cnt;
		avg.statNNodeInternalNT /= (double)cnt;
		avg.statNDistCalc /= (double)cnt;
		avg.statNDistCalc1NN /= (double)cnt;
		avg.statNDistCalcKNN /= (double)cnt;
		avg.statNq1 /= (double)cnt;
		avg.statNq2 /= (double)cnt;
		avg.statNqp1 /= (double)cnt;
		avg.statNqp2 /= (double)cnt;
		avg.statDqk1_1 /= (double)cnt;
		avg.statDqk1_2 /= (double)cnt;
		avg.statDqk10_1 /= (double)cnt;
		avg.statDqk10_2 /= (double)cnt;
		avg.statNu1 /= (double)cnt;
		avg.statNu2 /= (double)cnt;
		avg.statSCalc /= (double)cnt;
		avg.statSjvmF /= (double)cnt;
		avg.statSjvmE /= (double)cnt;
		avg.statGcDiffL /= (double)cnt;
		avg.statGcTimeL /= (double)cnt;
		avg.statGcDiffWq /= (double)cnt;
		avg.statGcTimeWq /= (double)cnt;
		avg.statGcDiffPq /= (double)cnt;
		avg.statGcTimePq /= (double)cnt;
		avg.statGcDiffUp /= (double)cnt;
		avg.statGcTimeUp /= (double)cnt;
		avg.statGcDiffUl /= (double)cnt;
		avg.statGcTimeUl /= (double)cnt;

		avg.SEEDmsg = "AVG-" + cnt + "/" + stats.size();

		return avg;
	}
	
	@SuppressWarnings("unchecked")
	public Candidate createTree() {
		if (indexClass == null) {
			String className;
			if (isRangeData) {
				className = INDEX.getCandidateClassNameRectangle();
			} else {
				className = INDEX.getCandidateClassNamePoint();
			}
			if (className == null || className.trim().equals("")) {
				throw new IllegalStateException("Please provide a class name "
						+ "for TestStats: " + (INDEX != null ? INDEX.name() : "no index"));
			}
			setCandidateClass(className);
		}
		try {
			Class<Candidate> cls = (Class<Candidate>) indexClass;
			Constructor<Candidate> c = cls.getConstructor(TestStats.class);
			return c.newInstance(this);
		} catch (InstantiationException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException 
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setCandidateClass(String className) {
		if (className == null || className.trim().equals("")) {
			throw new IllegalStateException("Please provide a class name "
					+ "for TestStats: " + (INDEX != null ? INDEX.name() : "no index"));
		}
		try {
			indexClass = (Class<Candidate>) Class.forName(className);
		} catch (IllegalArgumentException | SecurityException 
				| ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}