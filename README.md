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

## Run on Standalone cluster
[1] http://spark.apache.org/docs/latest/spark-standalone.html

### Start cluster manually
$ /path-to-spark-home/sbin/start-master.sh
######Once started, the master will print out a spark://HOST:PORT URL for itself, which you can use to connect workers to it, or pass as the “master” argument to SparkContext. You can also find this URL on the master’s web UI, which is http://localhost:8080 by default. <br />

$ /path-to-spark-home/sbin/start-slave.sh <master-spark-URL>
######Once you have started a worker, look at the master’s web UI (http://localhost:8080 by default). You should see the new node listed there, along with its number of CPUs and memory (minus one gigabyte left for the OS).

### Submit application on standalone
$ /path-to-spark-home/bin/park-submit --class "SimpleApp" --master spark://suvasish-pc:6066 --deploy-mode "cluster" target/simple-spark-1.0-SNAPSHOT.jar

<hr />

## Run on YARN
[1] http://spark.apache.org/docs/latest/running-on-yarn.html <br />
[2] https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/SingleCluster.html <br />
[3] https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/ClusterSetup.html <br />

### Set $HADOOP_CONF_DIR
$ export HADOOP_CONF_DIR=/path/to/hadoop/etc/hadoop

### Start HADOOP & YARN daemons
$ start-dfs.sh <br />
$ start-yarn.sh

###### Now upload the data.txt and ebay.csv into hdfs (for example within /user/suvasish) and update BasicRDD.java and SparkSql.java accordingly. Recomplile the project. <br />
e.g. <JavaRDD<String> distFile = jsc.textFile("hdfs://localhost:9000/user/suvasish/data.txt");> <br />
$ hdfs dfs -mkdir /user/suvasish
$ hdfs dfs -put /path/to/data.txt /user/suvasish/data.txt

### Submit application on yarn
$ spark-submit --class "BasicRDD" --master yarn --deploy-mode cluster target/simple-spark-1.0-SNAPSHOT.jar 

### Monitor
- Namenode/Datanone - http://localhost:50070/
- YARN cluster - http://suvasish-pc:8088/

<hr />

