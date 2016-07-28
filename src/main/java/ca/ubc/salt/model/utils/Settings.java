package ca.ubc.salt.model.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Settings
{

    public final static Logger consoleLogger = LogManager.getRootLogger();
    // public final static Logger fileLogger =
    // LogManager.getLogger("FileLogger");
    public static final String LIBRARY_JAVA = "/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home/jre/lib/rt.jar";
    // public static final String LIBRARY_JAVA =
    // "/Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/lib/rt.jar";
    public static final String PROJECT_PATH = "/Users/arash/Research/repos/commons-math";
    // public static final String PROJECT_PATH =
    // "/Users/arash/Documents/workspace-mars/Calculator";
    public static final String PROJECT_INSTRUMENTED_PATH = PROJECT_PATH + "-instrumented";
    public static final String PROJECT_MERGED_PATH = PROJECT_PATH + "-merged";
    public final static String tracePaths = PROJECT_INSTRUMENTED_PATH + "/traces";
    public final static String classFileMappingPath = "classFileMapping.txt";

    public final static String[] methodBlackList = new String[] { "AdamsBashforthIntegratorTest.backward",
	    "AdamsNordsieckTransformerTest.testTransformExact", "AkimaSplineInterpolatorTest.testInterpolateCubic",
	    "AkimaSplineInterpolatorTest.testInterpolateLine", "AkimaSplineInterpolatorTest.testInterpolateParabola",
	    "ArithmeticUtilsTest.testSubAndCheckLong", "ArrayFieldVectorTest.testSerial",
	    "ArrayFieldVectorTest.testWalkInOptimizedOrderPreservingVisitor3",
	    "BiDiagonalTransformerTest.testVOrthogonal", "BicubicInterpolatingFunctionTest.testParaboloid",
	    "BicubicInterpolatingFunctionTest.testPlane", "BicubicInterpolatorTest.testParaboloid",
	    "BicubicInterpolatorTest.testPlane", "BlockFieldMatrixTest.testCopyFunctions",
	    "BlockFieldMatrixTest.testCopySubMatrix", "BlockFieldMatrixTest.testEqualsAndHashCode",
	    "BlockFieldMatrixTest.testGetSetColumnLarge", "BlockFieldMatrixTest.testGetSetColumnMatrixLarge",
	    "BlockFieldMatrixTest.testGetSetColumnVectorLarge", "BlockFieldMatrixTest.testGetSetMatrixLarge",
	    "BlockFieldMatrixTest.testGetSetRowLarge", "BlockFieldMatrixTest.testGetSetRowMatrixLarge",
	    "BlockFieldMatrixTest.testGetSetRowVectorLarge", "BlockFieldMatrixTest.testMath209",
	    "BlockFieldMatrixTest.testOperateLarge", "BlockFieldMatrixTest.testOperatePremultiplyLarge",
	    "BlockFieldMatrixTest.testSeveralBlocks", "BlockFieldMatrixTest.testToString",
	    "BlockFieldMatrixTest.testWalk", "BlockRealMatrixTest.testCopyFunctions",
	    "BlockRealMatrixTest.testGetSetMatrixLarge", "BlockRealMatrixTest.testMath209",
	    "BlockRealMatrixTest.testOperateLarge", "BlockRealMatrixTest.testOperatePremultiplyLarge",
	    "BlockRealMatrixTest.testToString", "CertifiedDataTest.testDescriptiveStatistics",
	    "CertifiedDataTest.testSummaryStatistics", "ClassicalRungeKuttaStepInterpolatorTest.derivativesConsistency",
	    "ClassicalRungeKuttaStepInterpolatorTest.serialization", "CombinationsTest.testLexicographicIterator",
	    "CombinatoricsUtilsTest.test0Choose0", "CombinatoricsUtilsTest.testBinomialCoefficientFail",
	    "CombinatoricsUtilsTest.testBinomialCoefficientLarge", "CombinatoricsUtilsTest.testCheckBinomial3",
	    "CombinatoricsUtilsTest.testFactorial", "ContinuedFractionTest.testGoldenRatio",
	    "ContinuousOutputFieldModelTest.testBoundaries", "ContinuousOutputFieldModelTest.testModelsMerging",
	    "ContinuousOutputFieldModelTest.testRandomAccess", "ContinuousOutputModelTest.testBoundaries",
	    "ContinuousOutputModelTest.testRandomAccess",
	    "CorrelatedRandomVectorGeneratorTest.testSampleWithZeroCovariance", "CovarianceTest.testLongly",
	    "CovarianceTest.testOneColumn", "DfpMathTest.testPow", "DfpMathTest.testSin",
	    "DormandPrince54StepInterpolatorTest.checkClone", "DormandPrince54StepInterpolatorTest.serialization",
	    "DormandPrince853StepInterpolatorTest.checklone", "DormandPrince853StepInterpolatorTest.serialization",
	    "EigenDecompositionTest.testBigMatrix", "EnumeratedIntegerDistributionTest.testSample",
	    "EnumeratedRealDistributionTest.testGetSupportLowerBound",
	    "EnumeratedRealDistributionTest.testIsSupportConnected", "EnumeratedRealDistributionTest.testSample",
	    "Euclidean2DTest.testDimension", "Euclidean2DTest.testSubSpace",
	    "EulerStepInterpolatorTest.interpolationInside", "EulerStepInterpolatorTest.serialization",
	    "EvaluationTest.testComputeValueAndJacobian", "EventFilterTest.testDecreasingOnly",
	    "EventFilterTest.testHistoryDecreasingBackward", "EventFilterTest.testHistoryDecreasingForward",
	    "EventFilterTest.testHistoryIncreasingBackward", "EventFilterTest.testHistoryIncreasingForward",
	    "EventFilterTest.testIncreasingOnly", "FastHadamardTransformerTest.testNoIntInverse",
	    "FieldMatrixImplTest.testWalk", "GillStepInterpolatorTest.serialization",
	    "GillStepInterpolatorTest.testDerivativesConsistency", "GraggBulirschStoerStepInterpolatorTest.checklone",
	    "GraggBulirschStoerStepInterpolatorTest.serialization", "HermiteTest.testNormalDistribution",
	    "HermiteTest.testNormalVariance", "HighamHall54StepInterpolatorTest.checkClone",
	    "HighamHall54StepInterpolatorTest.serialization", "KMeansPlusPlusClustererTest.testSmallDistances",
	    "KohonenTrainingTaskTest.testTravellerSalesmanSquareTourParallelSolver",
	    "KolmogorovSmirnovTestTest.testDRoundingMonteCarlo", "LegendreHighPrecisionTest.testCos",
	    "LegendreHighPrecisionTest.testInverse", "LocationFinderTest.test2x2Network",
	    "LutherStepInterpolatorTest.derivativesConsistency", "LutherStepInterpolatorTest.serialization",
	    "MannWhitneyUTestTest.testBigDataSetOverflow", "MidpointStepInterpolatorTest.serialization",
	    "MidpointStepInterpolatorTest.testDerivativesConsistency",
	    "MultivariateNormalDistributionTest.testSampling",
	    "MultivariateNormalMixtureModelDistributionTest.testNonUnitWeightSum",
	    "MultivariateSummaryStatisticsTest.testEqualsAndHashCode", "NaturalRankingTest.testNaNsAndInfs",
	    "NaturalRankingTest.testNaNsMaximalTiesMinimum", "NordsieckStepInterpolatorTest.derivativesConsistency",
	    "NordsieckStepInterpolatorTest.serialization", "OpenIntToDoubleHashMapTest.testConcurrentModification",
	    "OpenIntToDoubleHashMapTest.testContainsKey", "OpenIntToDoubleHashMapTest.testCopy",
	    "OpenIntToDoubleHashMapTest.testGetAbsent", "OpenIntToDoubleHashMapTest.testGetFromEmpty",
	    "OpenIntToDoubleHashMapTest.testIterator", "OpenIntToDoubleHashMapTest.testPutAbsentOnExisting",
	    "OpenIntToDoubleHashMapTest.testPutAndGet", "OpenIntToDoubleHashMapTest.testPutAndGetWith0ExpectedSize",
	    "OpenIntToDoubleHashMapTest.testPutAndGetWithExpectedSize",
	    "OpenIntToDoubleHashMapTest.testPutKeysWithCollisions", "OpenIntToDoubleHashMapTest.testPutOnExisting",
	    "OpenIntToDoubleHashMapTest.testRemove", "OpenIntToDoubleHashMapTest.testRemove2",
	    "OpenIntToDoubleHashMapTest.testRemoveAbsent", "OpenIntToFieldTest.testPutAndGetWithExpectedSize",
	    "PiecewiseBicubicSplineInterpolatingFunctionTest.testParabaloid",
	    "PiecewiseBicubicSplineInterpolatingFunctionTest.testPlane", "PolygonsSetTest.testIssue880Complete",
	    "PolyhedronsSetTest.testCross", "PolynomialCurveFitterTest.testRedundantSolvable",
	    "QRDecompositionTest.testAEqualQR", "QRDecompositionTest.testDimensions",
	    "QRDecompositionTest.testHTrapezoidal", "QRDecompositionTest.testInvertTallSkinny",
	    "QRDecompositionTest.testMatricesValues", "QRDecompositionTest.testQOrthogonal",
	    "QRDecompositionTest.testRUpperTriangular", "QRSolverTest.testOverdetermined",
	    "QRSolverTest.testUnderdetermined", "RRQRDecompositionTest.testAPEqualQR",
	    "RRQRDecompositionTest.testDimensions", "RRQRDecompositionTest.testHTrapezoidal",
	    "RRQRDecompositionTest.testQOrthogonal", "RRQRDecompositionTest.testRUpperTriangular",
	    "RRQRDecompositionTest.testRank", "RRQRSolverTest.testOverdetermined", "RRQRSolverTest.testUnderdetermined",
	    "SimplexSolverTest.testLargeModel", "SimplexSolverTest.testMath930",
	    "SingularValueDecompositionTest.testAEqualUSVt", "SingularValueDecompositionTest.testStability1",
	    "SingularValueDecompositionTest.testStability2", "SphericalPolygonsSetTest.testConcentricSubParts",
	    "SplineInterpolatorTest.testInterpolateLinearDegenerateThreeSegment",
	    "ThreeEighthesStepInterpolatorTest.derivativesConsistency",
	    "ThreeEighthesStepInterpolatorTest.serialization", "TrapezoidIntegratorTest.testQuinticFunction",
	    "TriDiagonalTransformerTest.testMatricesValues3", "TriDiagonalTransformerTest.testMatricesValues5",
	    "TricubicInterpolatingFunctionTest.testPlane", "TricubicInterpolatingFunctionTest.testQuadric",
	    "TricubicInterpolatingFunctionTest.testWave", "UniformCrossoverTest.testCrossover",
	    "ValueServerTest.testFixedSeed", "ValueServerTest.testNextDigest" };
    public static final Set<String> blackListSet = new HashSet<String>(Arrays.asList(methodBlackList));

    public static final String TEST_CLASS = "/Users/arash/Research/repos/commons-math/src/test/java/org/apache/commons/math4/transform/FastFourierTransformerTest.java";
    public static final String PROD_TEST_CLASS = "/Users/Arash/Research/repos/commons-math/src/main/java/org/apache/commons/math4/complex/Complex.java";
    // public static final String TEST_CLASS =
    // "/Users/Arash/Research/repos/commons-math/src/test/java/org/apache/commons/math4/fraction/FractionTest.java";

    public static String getInstrumentedCodePath(String oldPath)
    {
	return oldPath.replaceFirst(PROJECT_PATH, PROJECT_INSTRUMENTED_PATH);
    }

}
