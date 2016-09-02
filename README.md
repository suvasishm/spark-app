# Pre-requisites
The following should be installed along with proper PATH variable setup. Consult respective s/w setup document for details.
For example (Linux),<br />
export JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"<br />
export PATH=${PATH}:${JAVA_HOME}/bin

- Java 1.8 or higher
- Latest Maven
- Latest Spark
- IntelliJ Idea (Optional)

# Build the App
$ mvn clean package


# Run the App

## Run on 'local' deploy mode
$ /path-to-spark-home/bin/spark-submit --class "SimpleApp" --master local[4] target/simple-spark-1.0-SNAPSHOT.jar<br />
$ /path-to-spark-home/bin/spark-submit --class "BasicRDD" --master local[4] target/simple-spark-1.0-SNAPSHOT.jar

<hr />

## Run on Standalone(cluster manager) deploy mode

### Start cluster manually
$ /path-to-spark-home/sbin/start-master.sh <br />
- Once started, the master will print out a spark://HOST:PORT URL for itself, which you can use to connect workers to it, or pass as the “master” argument to SparkContext. You can also find this URL on the master’s web UI, which is http://localhost:8080 by default. <br />
$ /path-to-spark-home/sbin/start-slave.sh <master-spark-URL> <br />
- Once you have started a worker, look at the master’s web UI (http://localhost:8080 by default). You should see the new node listed there, along with its number of CPUs and memory (minus one gigabyte left for the OS).

### Submit application on standalone
$ s/path-to-spark-home/bin/park-submit --class "SimpleApp" --master spark://suvasish-pc:6066 --deploy-mode "cluster" target/simple-spark-1.0-SNAPSHOT.jar

