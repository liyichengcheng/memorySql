package com.blue.sqldb.sqlparse;

import com.blue.sqldb.datastruct.baddtree.Entity;

import java.util.LinkedList;
import java.util.List;

public class UpdateParser {

    public int parse(String sql) {
        sql = sql.replaceAll("  "," ").replaceAll(", ",",");
        String lowerCaseSql = sql.toLowerCase();

        int index_1 = lowerCaseSql.indexOf("update ");
        int index_2 = lowerCaseSql.indexOf(" set ");
        String tableName = lowerCaseSql.substring(index_1 + 7,index_2).strip();
        int index_3 = lowerCaseSql.indexOf(" where ");
        String setClause = lowerCaseSql.substring(index_2 + 5,index_3).strip();
        String whereClause = lowerCaseSql.substring(index_3 + 7).strip();

        FilterCondition filterCondition = FilterConditionParser.parseWhereSubSql(whereClause);

        List<Entity> entityList = new LinkedList<>();
        //查询获取
        if("coorder".equals(tableName)) {
            CoOrderParser coOrderSelectParser = new CoOrderParser();
            entityList = coOrderSelectParser.parse(filterCondition);
        }
        return entityList.size();
    }

    public static void main(String[] args) {
        String sql = "udpate coorder set status = 1,ctime = 1000 where uid in (2400,521) and (status = 0 or (ctime >= 0 and ctime <= 100)) and contract_id = 1551033199875420160";
        UpdateParser updateParser = new UpdateParser();
        updateParser.parse(sql);
    }
}
