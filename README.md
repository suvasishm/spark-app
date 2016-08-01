#Pre-requisites
The following should be installed along with proper PATH variable setup. Consult respective s/w setup document for details.
For example,<br>
export JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"<br>
export PATH=${PATH}:${JAVA_HOME}/bin

- Java 1.8 or higher
- Latest Maven
- Latest Spark
- IntelliJ Idea (Optional)

# Build SparkApp
$ mvn clean package

# Use spark-submit to run your application
$ /path-to-spark-home/bin/spark-submit --class "SimpleApp" --master local[4] target/simple-spark-1.0-SNAPSHOT.jar
$ /path-to-spark-home/bin/spark-submit --class "BasicRDD" --master local[4] target/simple-spark-1.0-SNAPSHOT.jar
