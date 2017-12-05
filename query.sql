CREATE TABLE matagaruda.snort(
	timestamp string,
	sig_generator string,
	sig_id string,
	sig_rev string,
	msg string,
	proto string,
	src string,
	srcport string,
	dst string,
	dstport string,
	ethsrc string,
	ethdst string,
	ethlen string,
	tcpflags string,
	tcpseq string,
	tcpack string,
	tcplen string,
	tcpwindow string,
	ttl string,
	tos string,
	id string,
	dgmlen string,
	iplen string,
	icmptype string,
	icmpcode string,
	icmpid string,
	icmpseq string
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;

CREATE FUNCTION geoip2 as 'com.matagaruda.udf.GenericUDTFGeoIP' USING JAR 'hdfs://hadoop-master:9000/user/hive/udf/hive-udf.jar';

CREATE FUNCTION iptolong as 'com.matagaruda.udf.GenericIPToLong' USING JAR 'hdfs://hadoop-master:9000/user/hive/udf/hive-udf.jar'; 

INSERT OVERWRITE LOCAL DIRECTORY '/user/hduser/geoloc/' 
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE
select src, ip.country_name, ip.latitude, ip.longitude from matagaruda.snort_temp s lateral view geoip2(src, '/home/hduser/GeoLite2-City.mmdb') ip as country_name, country_iso_code, subdivision_name, city, postal_code, latitude, longitude; 

add jar hdfs://hadoop-master:9000/user/hive/serde/csv-serde.jar; 
 
 CREATE TABLE matagaruda.dbip_lookup (
  ip_start STRING,
  ip_end STRING,
  country STRING,
  stateprov STRING,
  city STRING,
  latitude STRING,
  longitude STRING,
  timezone_offset STRING,
  timezone_name STRING,
  isp_name STRING,
  connection_type STRING,
  organization_name STRING
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   separatorChar = ,,
   quoteChar     = \,
   escapeChar    = \\
)   
STORED AS TEXTFILE;

load data local inpath '/home/hduser/dbip-full-2014-11.csv' into table matagaruda.dbip_lookup;


CREATE TABLE snort_log AS
SELECT
    event.timestamp,
    signature.sig_gid as sig_generator,
	signature.sig_id,
	signature.sig_rev,
	signature.sig_name as msg,
	iphdr.ip_proto as proto,
    int8ip_to_str(iphdr.ip_src) as src,
    tcphdr.tcp_sport as srcport,
	int8ip_to_str(iphdr.ip_dst) as dst,
	tcphdr.tcp_dport as dstport,
    '' as ethsrc,
	'' as ethdst,
    '' as ethlen,
    tcphdr.tcp_flags as tcpflags,
	tcphdr.tcp_seq as tcpseq,
    tcphdr.tcp_ack as tcp_ack,
    tcphdr.tcp_csum as tcplen,
    tcphdr.tcp_win as tcpwindow,
    iphdr.ip_ttl as ttl,
    iphdr.ip_tos as tos,
    iphdr.ip_id as id,
    iphdr.ip_csum as dmglen,
    iphdr.ip_len as iplen,
	icmphdr.icmp_type as icmptype,
    icmphdr.icmp_code as icmpcode,
    icmphdr.icmp_id as icmpid,
    icmphdr.icmp_seq as icmpseq
   FROM 
(event LEFT JOIN iphdr ON (((event.sid = iphdr.sid) AND (event.cid = iphdr.cid))))
     JOIN signature ON ((event.signature = signature.sig_id))
		 LEFT JOIN tcphdr ON (((event.sid = tcphdr.sid) AND (event.cid = tcphdr.cid)))
		 LEFT JOIN udphdr ON (((event.sid = udphdr.sid) AND (event.cid = udphdr.cid)))		 
     LEFT JOIN icmphdr ON (((event.sid = icmphdr.sid) AND (event.cid = icmphdr.cid)))
	 
------	 
INSERT OVERWRITE LOCAL DIRECTORY '/home/hduser/kmeans/' 
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE
SELECT 
    d.country, d.latitude, d.longitude 
FROM matagaruda.snort_temp s, matagaruda.dbip_lookup_temp d 
WHERE (iptolong(s.src) > iptolong(d.ip_start) AND iptolong(s.src) < iptolong(d.ip_end));

--------------------------------
SELECT 
    d.country, d.latitude, d.longitude 
FROM matagaruda.dbip_lookup d 
WHERE (iptolong('8.8.8.8') > iptolong(d.ip_start) AND iptolong('8.8.8.8') < iptolong(d.ip_end));	 

------------------------
SELECT 
    d.country, d.latitude, d.longitude 
FROM matagaruda.dbip_lookup d 
WHERE IN (SELECT src FROM snort iptolong(s.src)) BETWEEN iptolong(d.ip_start) AND iptolong(d.ip_end) limit 10;

-------------------------
SELECT 
    dbip_lookup.country, dbip_lookup.latitude, dbip_lookup.longitude 
FROM snort JOIN dbip_lookup 
ON (iptolong(snort.src) = iptolong(dbip_lookup.ip_start) AND iptolong(snort.src) = iptolong(dbip_lookup.ip_end)) limit 10;

-----------------------
SELECT 
    d.country, d.latitude, d.longitude 
FROM matagaruda.dbip_lookup d 
WHERE EXISTS (SELECT src FROM matagaruda.snort s WHERE iptolong(s.src) BETWEEN iptolong(d.ip_start) AND iptolong(d.ip_end)) limit 10;


CREATE TABLE matagaruda.ip_map (
	ip STRING,
	country STRING,
	stateprov STRING,
	city STRING,
	latitude DOUBLE,
	longitude DOUBLE,
	timezone_offset STRING,
	timezone_name STRING,
	isp_name STRING,
	connection_type STRING,
	organization_name STRING
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','   
STORED AS TEXTFILE;

INSERT OVERWRITE DIRECTORY '/user/hduser/kmeans/' 
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
SELECT latitude, longitude FROM matagaruda.ip_map;

CREATE TABLE matagaruda.geoloc 
ROW FORMAT DELIMITED FIELDS TERMINATED BY ' ' 
LINES TERMINATED BY '\n'  
STORED AS TEXTFILE LOCATION '/user/hduser/geoloc' 
AS 
SELECT ip.latitude, ip.longitude FROM matagaruda.snort_temp s LATERAL VIEW geoip2(src, '/home/hduser/GeoLite2-City.mmdb') ip AS country_name, country_iso_code, subdivision_name, city, postal_code, latitude, longitude;	

./mahout clusterdump --input /user/hduser/kmeans/output/clusters-3-final -of GRAPH_ML --pointsDir /user/hduser/kmeans/output/clusteredPoints --output kmeans/clusteranalyze.txt	


CREATE TABLE matagaruda.ip_map
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE
AS
SELECT
 s.timestamp,
 s.sig_generator,
 s.sig_id,
 s.sig_rev,
 s.msg,
 ipsrc.latitude AS src_latitude,
 ipsrc.longitude AS src_longitude,
 ipsrc.country_iso_code AS src_country_iso_code,
 ipsrc.country_name AS src_country_name,
 ipsrc.city AS src_city,
 ipdst.latitude AS dst_latitude,
 ipdst.longitude AS dst_longitude,
 ipdst.country_iso_code AS dst_country_iso_code,
 ipdst.country_name AS dst_country_name,
 ipdst.city AS dst_city
FROM
 matagaruda.snort s
LATERAL VIEW
 geoip2(src, '/home/hduser/GeoLite2-City.mmdb')
 ipsrc AS country_name, country_iso_code, subdivision_name, city, postal_code, latitude, longitude
LATERAL VIEW
 geoip2(dst, '/home/hduser/GeoLite2-City.mmdb')
 ipdst AS country_name, country_iso_code, subdivision_name, city, postal_code, latitude, longitude;
 
 

CREATE TABLE matagaruda.kmeans(
	number_of_k int,
	cluster_id int,
	latitude double,
	longitude double,
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;	

INSERT OVERWRITE LOCAL DIRECTORY '/home/hduser/kmeans_6/' 
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE
SELECT i.*, k.cluster_id
  FROM (SELECT DISTINCT *
               FROM matagaruda.kmeans
              WHERE number_of_k = 7) k
      JOIN matagaruda.ip_map i
          ON (k.longitude = i.src_longitude AND k.latitude = i.src_latitude);
		  
j288ab25y5nyscwjtkf5  
		  		  