/**
 * Created by suvasish on 1/8/16.
 */

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

// Illustrate RDD
public class BasicRDD {
    static SparkConf sparkConf = new SparkConf().setAppName("Advanced Spark App").setMaster("local");
    static JavaSparkContext jsc = new JavaSparkContext(sparkConf);

    public static void main(String[] args) {
        // 1. Create RDD by parallelizing an existing collection in your driver program
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        JavaRDD<Integer> distData = jsc.parallelize(data);
        //printRDD(distData);

        // Calculate sum of the values in the array
        int totalValue = distData.map(s -> s.intValue()).reduce((a, b) -> a + b);
        System.out.println("### Total value of the array: " + totalValue);

        /* ========================================================================== */

        // 2. Create text file RDDs using SparkContext’s textFile method
        JavaRDD<String> distFile = jsc.textFile("/home/suvasish/IdeaProjects/SparkApp/src/main/resources/data.txt"); // distFile is merely a pointer to the file
        // Now distFile can be acted on by dataset operations
        printRDD(distFile);

        // The following defines lineLengths as the result of a map transformation
        JavaRDD<Integer> lineLengths = distFile.map(s -> s.length()); //lineLengths is not immediately computed, due to laziness
        //printRDD(lineLengths);

        // If we also wanted to use lineLengths again later
        lineLengths.persist(StorageLevel.MEMORY_ONLY());

        // Finally, we run reduce, which is an action
        int totalLength = lineLengths.reduce((a, b) -> a + b);
        /* At this point Spark breaks the computation into tasks to run on separate machines,
           and each machine runs both its part of the map and a local reduction,
           returning only its answer to the driver program. */
        System.out.println("### Total Length of data.txt: " + totalLength);

        /* ========================================================================= */

        // 3. RDDs of key-value pairs
        JavaPairRDD<String, Integer> pairs = distFile.mapToPair(s -> new Tuple2(s, 1));
        //printPairRDD(pairs);
        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);
        printPairRDD(counts);

    }

    public static void printRDD(JavaRDD rdd) {
        // print elements in the rdd
        System.out.println("### Printing elements in RDD:");
        //1. rdd.foreach(x -> System.out.println("#->" + x));
        //2. rdd.collect().forEach(x -> System.out.println("#->" + x));
        rdd.take(100).forEach(x -> System.out.println("#->" + x));
    }

    public static void printPairRDD(JavaPairRDD rdd) {
        // print elements in the pair rdd
        System.out.println("### Printing elements in Pair RDD:");
        rdd.take(100).forEach(x -> System.out.println("##->" + x));
    }
}
