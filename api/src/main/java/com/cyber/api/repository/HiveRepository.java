package com.cyber.api.repository;

import com.cyber.api.hadoop.utils.HiveTypeUtil;
import java.util.List;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HiveRepository {

    @Autowired
    protected JdbcTemplate hiveTemplate;

    public List getAllDatabases() {
        List result = hiveTemplate.queryForList("SHOW DATABASES");
        return result;
    }

    public void createTable(Table table) throws SerDeException {
        StringBuilder buf = new StringBuilder();
        StringBuilder columnFamily = new StringBuilder();
        List<FieldSchema> fields = HiveTypeUtil.PDataType2HiveType(table.getSd().getCols());

        buf.append("CREATE TABLE ");
        buf.append(table.getDbName());
        buf.append(".");
        buf.append(table.getTableName());
        buf.append(" (");

        boolean needsComma = false;
        int numCols = 0;

        for (FieldSchema field : fields) {
            if (needsComma) {
                buf.append(", ");
                columnFamily.append(',');
            }
            needsComma = true;
            buf.append(field.getName());
            buf.append(" ");
            buf.append(field.getType());

            if (numCols > 0) {
                columnFamily.append("0:");
                columnFamily.append(field.getName().toUpperCase());
            }
            numCols++;
        }

        buf.append(")");
//        buf.append(" STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'");
//        buf.append(" WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key");
//        buf.append(columnFamily.toString());
//        buf.append("\")");
//        buf.append(" TBLPROPERTIES (\"hbase.table.name\" = \"");
//        buf.append(table.getDbName().toUpperCase());
//        buf.append(".");
//        buf.append(table.getTableName().toUpperCase());
//        buf.append("\")");
        hiveTemplate.execute(buf.toString());
    }

    public void createDatabase(String database) {
        StringBuilder buf = new StringBuilder();
        
        buf.append("CREATE DATABASE");
        buf.append(" ");
        buf.append(database);
        
        hiveTemplate.execute(buf.toString());
    }

    public void dropDatabase(String name) {
        StringBuilder buf = new StringBuilder();
        buf.append("DROP DATABASE");
        buf.append(" ");
        buf.append(name);
        buf.append(" ");
        buf.append("CASCADE");
        hiveTemplate.execute(buf.toString());
    }

    public List getAllTables(String database) {
        StringBuilder buf = new StringBuilder();
        buf.append("SHOW TABLES IN");
        buf.append(" ");
        buf.append(database);
        return hiveTemplate.queryForList(buf.toString());
    }

    public List getTable(String database, String table) {
        StringBuilder buf = new StringBuilder();
        buf.append("SHOW TABLES IN");
        buf.append(" ");
        buf.append(database);
        return hiveTemplate.queryForList(buf.toString());
    }

    public void dropTable(String database, String table) {
        StringBuilder buf = new StringBuilder();
        buf.append("DROP TABLE");
        buf.append(" ");
        buf.append(database);
        buf.append(".");
        buf.append(table);
        hiveTemplate.execute(buf.toString());
    }

    /**
     *
     * @param database
     * @param table
     * @return
     */
    public List describe(String database, String table) {
        StringBuilder sql = new StringBuilder();
        sql.append("DESCRIBE FORMATTED");
        sql.append(" ");
        sql.append(database);
        sql.append(".");
        sql.append(table);
        return hiveTemplate.queryForList(sql.toString());
    }

    /**
     *
     * @param database
     * @param table
     * @return
     */
    public List getColumn(String database, String table) {
        StringBuilder sql = new StringBuilder();
        sql.append("DESCRIBE");
        sql.append(" ");
        sql.append(database);
        sql.append(".");
        sql.append(table);
        return hiveTemplate.queryForList(sql.toString());
    }
}
