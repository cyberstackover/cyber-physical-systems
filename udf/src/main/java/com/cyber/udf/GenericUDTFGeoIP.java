/**
 * GeoIP2.java.
 *
 * Copyright (C) 2015 Daniel Muller - Spuul,
 *
 * Copyright (C) 2013 Petra Barus,
 * https://github.com/petrabarus/HiveUDFs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.cyber.udf;

import java.io.File;
import java.net.InetAddress;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.UDFType;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
 * This is a UDF to look a property of an IP address using MaxMind GeoIP2
 * library.
 *
 * The function will need three arguments. <ol> <li>IP Address in string
 * format.</li> <li>IP attribute (e.g. COUNTRY, CITY, REGION, etc)</li>
 * <li>Database file name.</li> </ol>
 *
 * This is a derived version from https://github.com/petrabarus/HiveUDFs.
 * (Please let me know if I need to modify the license)
 *
 * @author Daniel Muller <daniel@spuul.com>
 * @see https://github.com/petrabarus/HiveUDFs
 */
@UDFType(deterministic = true)
@Description(
        name = "geoip2",
        value = "_FUNC_(ip, database) - looks a property for an IP address from"
        + "a library loaded\n"
        + "The GeoIP2 database comes separated. To load the GeoIP2 use ADD FILE.\n"
        + "Usage:\n"
        + " > _FUNC_(\"8.8.8.8\", \"database\")")
public class GenericUDTFGeoIP extends GenericUDTF {

    private PrimitiveObjectInspector stringOI = null;

    private PrimitiveObjectInspector databaseOI = null;

    @Override
    public StructObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {

        if (args.length != 2) {
            throw new UDFArgumentException("GeoIP2GenericUDTF() takes exactly two argument");
        }

        if (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE
                && ((PrimitiveObjectInspector) args[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("GeoIP2GenericUDTF() takes a string as a parameter");
        }

        // input inspectors
        stringOI = (PrimitiveObjectInspector) args[0];
        databaseOI = (PrimitiveObjectInspector) args[1];

        // output inspectors -- an object with two fields!
        List<String> fieldNames = new ArrayList<>(7);
        List<ObjectInspector> fieldOIs = new ArrayList<>(7);
        fieldNames.add("country_name");
        fieldNames.add("country_iso_code");
        fieldNames.add("subdivision_name");
        fieldNames.add("city");
        fieldNames.add("postal_code");
        fieldNames.add("latitude");
        fieldNames.add("longitude");

        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaDoubleObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaDoubleObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }

    public ArrayList<Object[]> processInputRecord(String ip, String databaseFile) {
        ArrayList<Object[]> result = new ArrayList<>();

        // ignoring null or empty input
        if (ip == null || ip.isEmpty()) {
            return result;
        }

        File database = new File(databaseFile);

        try {
            // This creates the DatabaseReader object, which should be reused across
            // lookups.
            DatabaseReader reader = new DatabaseReader.Builder(database).build();
            InetAddress ipAddress = InetAddress.getByName(ip);

            CityResponse response = reader.city(ipAddress);

            result.add(new Object[]{
                response.getCountry().getName(),
                response.getCountry().getIsoCode(),
                response.getMostSpecificSubdivision().getName(),
                response.getCity().getName(),
                response.getPostal().getCode(),
                response.getLocation().getLatitude(),
                response.getLocation().getLongitude()
            });

        } catch (Exception e) {
            //System.out.println(e);
        } finally {
            
        }

        return result;
    }

    @Override
    public void process(Object[] record) throws HiveException {

        final String name = stringOI.getPrimitiveJavaObject(record[0]).toString();
        final String databaseName = databaseOI.getPrimitiveJavaObject(record[1]).toString();

        ArrayList<Object[]> results = processInputRecord(name, databaseName);

        Iterator<Object[]> it = results.iterator();

        while (it.hasNext()) {
            Object[] r = it.next();
            forward(r);
        }
    }

    @Override
    public void close() throws HiveException {
        // do nothing
    }

}
