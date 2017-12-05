package com.cyber.api.hadoop.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hive.metastore.api.FieldSchema;

import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.phoenix.schema.types.PDataType;

public class HiveTypeUtil {

    private HiveTypeUtil() {
    }
    
    /**
     * This method returns the most appropriate PDataType associated with the
     * incoming hive type name.
     *
     * @param hiveType
     * @return PDataType
     */
    public static String HiveType2PDataType(String hiveType) throws SerDeException {
        final String lctype = hiveType.toLowerCase();
        if ("string".equals(lctype)) {
            return "varchar";
        } else if ("char".equals(lctype)) {
            return "char";
        } else if ("varchar".equals(lctype)) {
            return "varchar";
        } else if ("float".equals(lctype)) {
            return "float";
        } else if ("double".equals(lctype)) {
            return "double";
        } else if ("boolean".equals(lctype)) {
            return "boolean";
        } else if ("tinyint".equals(lctype)) {
            return "tinyint";
        } else if ("smallint".equals(lctype)) {
            return "smallint";
        } else if ("int".equals(lctype)) {
            return "integer";
        } else if ("bigint".equals(lctype)) {
            return "bigint";
        } else if ("timestamp".equals(lctype)) {
            return "timestamp";
        } else if ("binary".equals(lctype)) {
            return "binary";
        } else if ("date".equals(lctype)) {
            return "date";
        } else if ("array".equals(lctype)) {
            // return PArrayDataType
        }
        throw new SerDeException("Phoenix unrecognized column type: " + hiveType);
    }

    /**
     * 
     * @param fields
     * @return
     * @throws SerDeException 
     */
    public static List<FieldSchema> HiveType2PDataType(List<FieldSchema> fields) throws SerDeException {
        List<FieldSchema> result = new ArrayList<>();
        for (FieldSchema field : fields) {
            field.setType(HiveType2PDataType(field.getType()));
            result.add(field);
        }
        return result;
    }

    /**
     * 
     * @param fields
     * @return
     * @throws SerDeException 
     */
    public static List<FieldSchema> PDataType2HiveType(List<FieldSchema> fields) throws SerDeException {
        List<FieldSchema> result = new ArrayList<>();
        for (FieldSchema field : fields) {
            field.setType(PDataType2HiveType(field.getType()));
            result.add(field);
        }
        return result;
    }

    /**
     * 
     * @param pDataType
     * @return
     * @throws SerDeException 
     */
    public static String PDataType2HiveType(String pDataType) throws SerDeException {
        final String lctype = pDataType.toLowerCase();
        if ("string".equals(lctype)) {
            return "string";
        }
        if ("varchar".equals(lctype)) {
            return "string";
        }
        if ("char".equals(lctype)) {
            return "char";
        }
        if ("float".equals(lctype)) {
            return "float";
        }
        if ("double".equals(lctype)) {
            return "double";
        }
        if ("boolean".equals(lctype)) {
            return "boolean";
        }
        if ("tinyint".equals(lctype)) {
            return "tinyint";
        }
        if ("smallint".equals(lctype)) {
            return "smallint";
        }
        if ("int".equals(lctype)) {
            return "int";
        }
        if ("integer".equals(lctype)) {
            return "int";
        }
        if ("bigint".equals(lctype)) {
            return "bigint";
        }
        if ("timestamp".equals(lctype)) {
            return "timestamp";
        }
        if ("binary".equals(lctype)) {
            return "binary";
        }
        if ("date".equals(lctype)) {
            return "date";
        }
        throw new SerDeException("Phoenix unsupported column type: "
                + lctype);
    }
}
