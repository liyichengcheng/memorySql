package com.blue.sqldb.sqlparse;

import com.blue.sqldb.datastruct.baddtree.Entity;
import java.util.LinkedList;
import java.util.List;

public class SelectParser {
    public List<Entity> parse(String sql) {
        List<Entity> valueList = new LinkedList();

        sql = sql.replaceAll("  "," ").replaceAll(", ",",");
        String lowerCaseSql = sql.toLowerCase();

        int index_1 = lowerCaseSql.indexOf("from");
        if (index_1 > 0 && lowerCaseSql.startsWith("select")) {
            int index_2 = lowerCaseSql.indexOf("where");
            String tableName = lowerCaseSql.substring(index_1 + 5,index_2).strip();
            int index_where_end = lowerCaseSql.length();
            int index_3 = lowerCaseSql.indexOf("order by");
            if (index_3 > 0) {
                index_where_end = index_3;
            }
            int index_4 = lowerCaseSql.indexOf("group by");
            if (index_4 > 0 && index_4 < index_3) {
                index_where_end = index_4;
            }
            String whereSubSql = lowerCaseSql.substring(index_2 + 6,index_where_end);
            FilterCondition filterCondition = FilterConditionParser.parseWhereSubSql(whereSubSql);

            //查询获取
            if("coorder".equals(tableName)) {
                CoOrderParser coOrderSelectParser = new CoOrderParser();
                List<Entity> entityList = coOrderSelectParser.parse(filterCondition);
                entityList = coOrderSelectParser.orderBy(entityList,lowerCaseSql.substring(index_3));
                return entityList;
            }
        }
        return null;
    }
}