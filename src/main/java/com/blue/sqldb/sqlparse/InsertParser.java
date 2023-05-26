package com.blue.sqldb.sqlparse;

import com.blue.sqldb.model.CoOrder;
import com.blue.sqldb.model.CoOrderDataGrid;

public class InsertParser {

    public static boolean parse(String sql) {
        sql = sql.replaceAll("  "," ").replaceAll(", ",",");
        String lowerCaseSql = sql.toLowerCase();

        if (lowerCaseSql.contains("insert into")) {
            int index_1 = lowerCaseSql.indexOf("insert into ");
            String subSql = lowerCaseSql.substring(index_1 + 12);
            int index_2 = subSql.indexOf("(") + index_1 + 12;
            String tableName = lowerCaseSql.substring(index_1 + 12,index_2);

            int index_3 = subSql.indexOf(")") + index_1 + 12;
            String[] filedsArray = lowerCaseSql.substring(index_2 + 1,index_3).split(",");

            int index_4 = lowerCaseSql.indexOf("values");
            String subSql2 = lowerCaseSql.substring(index_4 + 6);
            int index_5 = subSql2.indexOf("(");
            int index_6 = subSql2.indexOf(")");
            String[] valueArray = subSql2.substring(index_5+1,index_6).split(",");

            if("futures.co_order_e_linkusdt".equals(tableName)) {
                CoOrder coOrder = CoOrder.build(filedsArray,valueArray);
                CoOrderDataGrid.insert(coOrder);
            }
        }
        return true;
    }
}
