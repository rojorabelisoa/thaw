package fr.upem.factory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.vertx.core.json.JsonObject;

public class QueryFactoryKit {
	public static List<JsonObject> getFormattedResult(ResultSet rset) throws SQLException {
	    List<JsonObject> ret = new ArrayList<JsonObject>();
        // get column names
        ResultSetMetaData rsMD = rset.getMetaData();
        int size = rsMD.getColumnCount();
        List<String> column = new ArrayList<String>();
        for(int i=1;i<=size;i++) {
        	column.add(rsMD.getColumnName(i).toUpperCase());
        }
        while(rset.next()) { // convert each object to an human readable JSON object
        	JsonObject obj = new JsonObject();
            for(int i=1;i<=size;i++) {
                String key = column.get(i - 1);
                String value = rset.getString(i);
                obj.put(key, value);
            }
            ret.add(obj);
        }
	    return ret;
	}
}
