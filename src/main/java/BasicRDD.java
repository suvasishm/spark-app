/**
 * Created by suvasish on 1/8/16.
 */

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.storage.StorageLevel;

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
        int totalValue = distData.map(s -> s.intValue()).reduce((a, b) -> a + b);
        System.out.println("### Total value of the array: " + totalValue);


        // 2. Create text file RDDs using SparkContext’s textFile method
        JavaRDD<String> distFile = jsc.textFile("/home/suvasish/IdeaProjects/SparkApp/src/main/resources/data.txt"); // distFile is merely a pointer to the file
        // Now distFile can be acted on by dataset operations

        // The following defines lineLengths as the result of a map transformation
        JavaRDD<Integer> lineLengths = distFile.map(s -> s.length()); //lineLengths is not immediately computed, due to laziness

        // If we also wanted to use lineLengths again later
        lineLengths.persist(StorageLevel.MEMORY_ONLY());

        // Finally, we run reduce, which is an action
        int totalLength = lineLengths.reduce((a, b) -> a + b);
        /* At this point Spark breaks the computation into tasks to run on separate machines,
           and each machine runs both its part of the map and a local reduction,
           returning only its answer to the driver program. */

        System.out.println("### Total Length of data.txt: " + totalLength);
    }
}
