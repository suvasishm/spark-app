# SparkApp
$ mvn clean package

# Use spark-submit to run your application
$ /path-to-spark-home/bin/spark-submit --class "SimpleApp" --master local[4] target/simple-spark-1.0-SNAPSHOT.jar
$ /path-to-spark-home/bin/spark-submit --class "BasicRDD" --master local[4] target/simple-spark-1.0-SNAPSHOT.jar
