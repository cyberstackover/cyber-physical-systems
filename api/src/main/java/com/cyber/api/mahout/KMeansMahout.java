package com.cyber.api.mahout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.WeightedVectorWritable;
import org.apache.mahout.clustering.classify.WeightedPropertyVectorWritable;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.RandomSeedGenerator;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.utils.clustering.ClusterDumper;
import org.datanucleus.store.rdbms.datasource.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * http://blog.guillaumeagis.eu/k-means-clustering-apache-mahout/
 */
public class KMeansMahout {
/*
    // ---- Static
    private static final Logger LOG = LoggerFactory.getLogger(KMeansMahout.class);

    private static final String DIRECTORY_CONTAINING_CONVERTED_INPUT = "/user/hduser/kmeans/convertedinput";

    private static final String DIRECTORY_OUTPUT = "/user/hduser/kmeans/output";

    private static final String DIRECTORY_INPUT = "/user/hduser/geoloc";

    private static final int NUMBER_OF_K = 6;

    private static final JdbcTemplate hiveTemplate = hiveTemplate();

    public static void main(String[] args) throws Exception {

        Path output = new Path(DIRECTORY_OUTPUT);

        Configuration conf = getConfiguration();
        HadoopUtil.delete(conf, new Path("/user/hduser/kmeans"));

        ClusterDumper clusterDumper = run(conf, new Path(DIRECTORY_INPUT), output, new EuclideanDistanceMeasure(), NUMBER_OF_K, 0.5, 50);

        clusterDump(clusterDumper);

    }

    public static ClusterDumper run(Configuration conf, Path input, Path output, DistanceMeasure measure,
            int k, double convergenceDelta, int maxIterations) throws Exception {

// Input should be given as sequence file format 
        Path directoryContainingConvertedInput = new Path(output, DIRECTORY_CONTAINING_CONVERTED_INPUT);
        InputDriver.runJob(input, directoryContainingConvertedInput, "org.apache.mahout.math.RandomAccessSparseVector");

        // Get initial clusters randomly 
        Path clusters = new Path(output, "random-seeds");
        clusters = RandomSeedGenerator.buildRandom(conf, directoryContainingConvertedInput, clusters, k, measure);

        // Run K-means with a given K
        KMeansDriver.run(conf, directoryContainingConvertedInput, clusters, output, convergenceDelta,
                maxIterations, true, 0.0, true);

        // run ClusterDumper to display result
        Path outGlob = new Path(output, "clusters-4-final");
        Path clusteredPoints = new Path(output, "clusteredPoints");

        ClusterDumper clusterDumper = new ClusterDumper(outGlob, clusteredPoints);
        return clusterDumper;
    }

    public static void clusterDump(ClusterDumper clusterDumper) {
        List<Map> data = new ArrayList<>();
        Map<Integer, List<WeightedVectorWritable>> hasil = clusterDumper.getClusterIdToPoints();
        int clusterId = 0;
        for (Map.Entry<Integer, List<WeightedVectorWritable>> entry : hasil.entrySet()) {
            Integer key = entry.getKey();
            List<WeightedVectorWritable> value = entry.getValue();
            for (int i = 0; i < value.size(); i++) {
                Map<String, Object> row = new HashMap<>();
                WeightedPropertyVectorWritable weightedVectorWritable = (WeightedPropertyVectorWritable) ((Object) value.get(i));
                row.put("latitude", weightedVectorWritable.getVector().get(0));
                row.put("longitude", weightedVectorWritable.getVector().get(1));
                row.put("number_of_k", NUMBER_OF_K);
                row.put("cluster_id", clusterId);
                System.out.println(key + " :  " + weightedVectorWritable.getVector().get(0) + " : " + weightedVectorWritable.getVector().get(1));
                data.add(row);
                if(i > 50)
                    break;
            }
            clusterId++;
        }
        
        insert("matagaruda", "kmeans", data);
    }

    /**
     * get hadoop configuration
     *
     * @return
     * @throws IOException
     */
/*    public static Configuration getConfiguration() throws IOException {
        final DefaultResourceLoader loader = new DefaultResourceLoader();

        Configuration config = new Configuration();
        config.addResource(loader.getResource("core-site.xml").getInputStream());
        config.addResource(loader.getResource("hdfs-site.xml").getInputStream());
        config.addResource(loader.getResource("mapred-site.xml").getInputStream());
        return config;
    }

    public static DataSource hiveDataSource() {

        BasicDataSource datasource = new BasicDataSource();
        datasource.setUrl("jdbc:hive2://hadoop-master:10000/default");
        datasource.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
        datasource.setUsername("");
        datasource.setPassword("");
        datasource.setTestWhileIdle(false);
        datasource.setTestOnBorrow(true);
        datasource.setValidationQuery("show tables");
        datasource.setTestOnReturn(false);
        datasource.setTimeBetweenEvictionRunsMillis(30000);
        datasource.setMaxActive(15);
        datasource.setInitialSize(1);
        datasource.setMaxWait(10000);

        return datasource;
    }

    public static JdbcTemplate hiveTemplate() {
        return new JdbcTemplate(hiveDataSource());
    }

    public static void insert(String database, String table, List<Map> collections) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(database);
        sql.append(".");
        sql.append(table);
        sql.append(" VALUES");

        boolean commaColumn = false;
        for (Map collection : collections) {

            if (commaColumn) {
                sql.append(",");
            }

            commaColumn = true;
            boolean needComma = false;
            sql.append("(");
            for (Object column : collection.keySet()) {
                if (needComma) {
                    sql.append(",");
                }

                needComma = true;
                sql.append("'");
                sql.append(collection.get(column));
                sql.append("'");
            }
            sql.append(")");
        }
        hiveTemplate.execute(sql.toString());
    } 
*/
    }
