package com.blue.sqldb.sqlparse;

import com.blue.sqldb.datastruct.baddtree.Entity;
import com.blue.sqldb.model.CoOrder;
import com.blue.sqldb.model.CoOrderDataGrid;
import com.blue.sqldb.utils.ListSortUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CoOrderParser {
    public static List<Entity> parse(FilterCondition filterCondition) {
        FilterCondition rootFilterCondition = filterCondition;
        while (filterCondition.children.size() != 0) {
            filterCondition = filterCondition.children.get(0);
        }
        List<Entity> valueList = singleExpressSearch(filterCondition);
        return filter(rootFilterCondition,valueList);
    }

    public static List<Entity> singleExpressSearch(FilterCondition filterCondition) {
        String express = filterCondition.excpress;
        List<Entity> valueList = new LinkedList();
        if (express.contains(">=")) {
            String[] fieledValueArray = express.split(">=");
            String fieled = fieledValueArray[0].strip();
            long toScore = Long.MAX_VALUE;
            boolean includeTo = false;
            if(fieled.contains("(")) {
                String filedValueAnother = filterCondition.parent.excpress.split(" and ")[1];//只有第一个条件查询条件
                String[] fieledValueArrayAnother = null;
                if (filedValueAnother.contains("<=")) {
                    fieledValueArrayAnother = filedValueAnother.split("<=");
                    includeTo = true;
                } else if(filedValueAnother.contains("<")) {
                    fieledValueArrayAnother = filedValueAnother.split("<");
                }
                String valueAnother = fieledValueArrayAnother[1].replace(")","")
                        .replace(" ","").strip();
                toScore = Long.parseLong(valueAnother);
            }
            if("(ctime".equals(fieled)) {
                CoOrderDataGrid.ctimeIndex.getList(Long.parseLong(fieledValueArray[1]
                        .replace(" ","").strip()),true,toScore,false);
            }
        }
        if (express.contains("<=")) {

        }
        if (express.contains(">")) {

        }
        if (express.contains("<")) {

        }
        if (express.contains("=")) {
            String[] fieledValueArray = express.split("=");
            String fieled = fieledValueArray[0].strip();
            String value = fieledValueArray[1].strip();
            if("id".equals(fieled)) {
                CoOrder coOrder = CoOrderDataGrid.getById(Long.parseLong(value));
                valueList.add(coOrder);
            }
            if("uid".equals(fieled)) {
                List<Entity> coOrder = CoOrderDataGrid.getByUid(Long.parseLong(value));
                valueList = coOrder;
            }
            if("status".equals(fieled)) {
                List<Entity> coOrder = CoOrderDataGrid.getByStatus(Integer.parseInt(value));
                valueList = coOrder;
            }
            if("ctime".equals(fieled)) {
                List<Entity> coOrder = CoOrderDataGrid.getByCtime(new Date(Long.parseLong(value)));
                valueList = coOrder;
            }
        }
        if (express.contains("in (")) {
            String[] fieledValueArray = express.split("in");
            String fieled = fieledValueArray[0].strip();
            String value = fieledValueArray[1].replace("(","")
                    .replace(")","").strip();
            String[] valueArray = value.split(",");
            for (int j = 0; j < valueArray.length; j++) {
                if("id".equals(fieled)) {
                    CoOrder coOrder = CoOrderDataGrid.getById(Long.parseLong(valueArray[j]));
                    if (null != coOrder) {
                        valueList.add(coOrder);
                    }
                }
                if("uid".equals(fieled)) {
                    List<Entity> coOrderList = CoOrderDataGrid.getByUid(Long.parseLong(valueArray[j]));
                    if(null != coOrderList && coOrderList.size() > 0){
                        valueList.addAll(coOrderList);
                    }
                }
                if("status".equals(fieled)) {
                    List<Entity> coOrderList = CoOrderDataGrid.getByStatus(Integer.parseInt(valueArray[j]));
                    if(null != coOrderList && coOrderList.size() > 0){
                        valueList.addAll(coOrderList);
                    }
                }
            }
        }
        return valueList;
    }

    public static List<Entity> filter(FilterCondition filterCondition,List<Entity> valueList) {
        List<Entity> valueResultList = new LinkedList();
        for (int k = 0; k < valueList.size(); k++) {
            CoOrder coOrder = (CoOrder) valueList.get(k);
            boolean match = filter(filterCondition,coOrder);
            if (match) {
                valueResultList.add(coOrder);
            }
        }

        return valueResultList;
    }

    /**
     * 判断 coOrder 是否符合 filterCondition
     * @param filterCondition
     * @param coOrder
     * @return
     */
    public static boolean filter(FilterCondition filterCondition,CoOrder coOrder) {
        if (filterCondition.children == null || filterCondition.children.size() == 0) {
            String express = filterCondition.excpress;
            boolean isMatch = singleExpressCheck(express,coOrder);
            if (!isMatch) {
                filterCondition.setResult((byte)-1);
                return false;
            }
            if (!filterCondition.childrenIsAnd && isMatch) {
                filterCondition.setResult((byte)1);
                return true;
            }
        }
        if (filterCondition.children != null) {
            for (FilterCondition filterConditionChild : filterCondition.children) {
                boolean isMatch = filter(filterConditionChild,coOrder);
                if (filterCondition.childrenIsAnd && !isMatch) {
                    filterCondition.setResult((byte)-1);
                    return false;
                }
                if (!filterCondition.childrenIsAnd && isMatch) {
                    filterCondition.setResult((byte)1);
                    return true;
                }
            }
        }
        if (filterCondition.childrenIsAnd) {
            filterCondition.setResult((byte)1);
            return true;
        } else {
            filterCondition.setResult((byte)-1);
            return false;
        }
    }

    public static boolean singleExpressCheck(String express,CoOrder coOrder) {
        if(express.startsWith("(")) {
            express = express.substring(1);
        } else if(express.endsWith(")")) {
            if (express.contains("))")) {
                express = express.replace("))",")");
            }
            if (!express.contains("(")) {
                express = express.substring(0,express.length() - 1);
            }
        }

        if (express.contains(">=")) {
            String[] fieledValueArray = express.split(">=");
            String fieled = fieledValueArray[0].strip();
            String value = fieledValueArray[1].strip();
            if("ctime".equals(fieled)) {
                if(coOrder.getCtime().getTime() < Long.parseLong(value)) {
                    return false;
                }
            }
        }
        if (express.contains("<=")) {
            String[] fieledValueArray = express.split("<=");
            String fieled = fieledValueArray[0].strip();
            String value = fieledValueArray[1].strip();
            if("ctime".equals(fieled)) {
                if(coOrder.getCtime().getTime() > Long.parseLong(value)) {
                    return false;
                }
            }
        }
        if (express.contains(">")) {

        }
        if (express.contains("<")) {

        }
        if (express.contains("=")) {
            String[] fieledValueArray = express.split("=");
            String fieled = fieledValueArray[0].strip();
            String value = fieledValueArray[1].strip();
            if("uid".equals(fieled)) {
                if(coOrder.getUid().longValue() != Long.parseLong(value)){
                    return false;
                }
            }
            if("status".equals(fieled)) {
                if(coOrder.getStatus().intValue() != Integer.parseInt(value)){
                    return false;
                }
            }
            if("ctime".equals(fieled)) {
                if(coOrder.getCtime().getTime() != Long.parseLong(value)){
                    return false;
                }
            }
        }
        if (express.contains("in (")) {
            String[] fieledValueArray = express.split("in");
            String fieled = fieledValueArray[0].strip();
            String value = fieledValueArray[1].replace("(","")
                    .replace(")","").strip();
            String[] valueArray = value.split(",");
            for (int j = 0; j < valueArray.length; j++) {
                if("uid".equals(fieled)) {
                    if(coOrder.getUid().longValue() == Long.parseLong(valueArray[j])){
                        return true;
                    }
                }
                if("status".equals(fieled)) {
                    if(coOrder.getStatus().intValue() == Integer.parseInt(valueArray[j])) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    public static List<Entity> orderBy(List<Entity> valueList,String orderByClause) {
        if(CollectionUtils.isNotEmpty(valueList)) {
            ListSortUtil listSortUtil = new ListSortUtil();
            String subClause = orderByClause.replace("order by ","");
            subClause = subClause.substring(0,subClause.indexOf(" "));
            int index_desc = orderByClause.indexOf(" desc");
            if(index_desc > -1) {
                listSortUtil.sort(valueList,subClause,"desc");
            } else {
                listSortUtil.sort(valueList,subClause,null);
            }
        }
        return valueList;
    }

    public static int orderBy(List<Entity> valueList,String setClause) {
        if (CollectionUtils.isNotEmpty(valueList)) {
            String[] fieldValueArray = setClause.split(",");
            for (String fieldValueCombine : fieldValueArray) {
                String[] fieldValue = fieldValueCombine.split("=");
                String field = fieldValue[0].strip();
                String value = fieldValue[1].strip();
            }
            for (Entity entity: valueList) {
                CoOrder coOrder = (CoOrder)entity;
                CoOrderDataGrid.update(coOrder);
            }
        }
    }
}
