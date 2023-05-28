package com.blue.sqldb.model;

import com.blue.sqldb.datastruct.baddtree.AvlTree;
import com.blue.sqldb.datastruct.baddtree.Entity;
import com.blue.sqldb.exception.DuplicationKeyException;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CoOrderDataGridIntransaction {
    public static int insert(CoOrder coOrder, String transactionId) {
        if (null == coOrder || null == coOrder.getId() || CoOrderDataGrid.coOrderData.getOne(coOrder.getId()) != null) {
            throw new DuplicationKeyException(coOrder.getId());
        }
        AvlTree<CoOrder> coOrderAvlTree = CoOrderDataGrid.transactionMap.get(transactionId);
        coOrderAvlTree.add(coOrder.getId(),coOrder);
        return 1;
    }

    public static int update(List<Entity> valueList, String setClause, String transactionId) throws InvocationTargetException, IllegalAccessException {
        if (CollectionUtils.isNotEmpty(valueList)) {
            String[] fieldValueArray = setClause.split(",");
            int count = fieldValueArray.length;
            String[] fieldArray = new String[count];
            String[] valueArray = new String[count];
            for (int i = 0; i < count; i++) {
                String fieldValueCombine = fieldValueArray[i];
                String[] fieldValue = fieldValueCombine.split("=");
                String field = fieldValue[0].strip();
                String value = fieldValue[1].strip();

                fieldArray[i] = field;
                valueArray[i] = value;
            }

            Method[] methodArray = CoOrder.class.getMethods();
            for (Entity entity: valueList) {
                CoOrder coOrder = (CoOrder)entity;
                coOrder.setOps(Entity.ops_update);
                for (int i = 0; i < count; i++) {
                    String field = fieldArray[i];
                    String value = valueArray[i];

                    field = field.substring(0, 1).toUpperCase() + field.replaceFirst("\\w", "");
                    String setMethod = "set" + field;
                    for (int j = 0; j < count; j++) {
                        Method method = methodArray[j];
                        if(setMethod.equals(method.getName())) {
                            method.invoke(coOrder, value);
                        }
                    }
                }

                AvlTree<CoOrder> coOrderAvlTree = CoOrderDataGrid.transactionMap.get(transactionId);
                coOrderAvlTree.add(coOrder.getId(),coOrder);
            }
        }
        return valueList.size();
    }
}
