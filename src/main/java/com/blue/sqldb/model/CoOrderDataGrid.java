package com.blue.sqldb.model;

import com.blue.sqldb.datastruct.baddtree.AvlTree;
import com.blue.sqldb.datastruct.baddtree.Entity;
import com.blue.sqldb.datastruct.index.SecondIndex;
import com.blue.sqldb.exception.DuplicationKeyException;

import java.util.*;

public class CoOrderDataGrid {
    public static AvlTree<CoOrder> coOrderData = new AvlTree<CoOrder>();
    public static Map<Long, AvlTree<CoOrder>> uidIndex = new HashMap<Long,AvlTree<CoOrder>>();
    public static Map<Integer,AvlTree<CoOrder>> statusIndex = new HashMap<Integer,AvlTree<CoOrder>>();
    public static SecondIndex<CoOrder> ctimeIndex = new SecondIndex<CoOrder>();

    public static int insert(CoOrder coOrder) {
        if (null == coOrder || null == coOrder.getId() || coOrderData.getOne(coOrder.getId()) != null) {
            throw new DuplicationKeyException(coOrder.getId());
        }
        coOrderData.add(coOrder.getId(),coOrder);
        AvlTree<CoOrder> coOrderList = uidIndex.get(coOrder.getUid());
        if(coOrderList == null) {
            coOrderList = new AvlTree<CoOrder>();
        }
        coOrderList.add(coOrder.getId(),coOrder);
        uidIndex.put(coOrder.getUid(),coOrderList);

        AvlTree<CoOrder> statusCoOrderList = statusIndex.get(coOrder.getStatus());
        if(statusCoOrderList == null) {
            statusCoOrderList = new AvlTree<CoOrder>();
        }
        statusCoOrderList.add(coOrder.getId(),coOrder);
        statusIndex.put(coOrder.getStatus(),statusCoOrderList);
        ctimeIndex.add(coOrder.getCtime().getTime(),coOrder);
        return 1;
    }

    public static int remove(CoOrder coOrder) {
        coOrderData.remove(coOrder);
        AvlTree<CoOrder> coOrderList = uidIndex.get(coOrder.getUid());
        coOrderList.remove(coOrder);

        ctimeIndex.remove(coOrder.getCtime().getTime(),coOrder);
        if(coOrderList.size() == 0) {
            uidIndex.remove(coOrder.getUid());
        }

        AvlTree<CoOrder> statusCoOrderList = statusIndex.get(coOrder.getStatus());
        statusCoOrderList.remove(coOrder);
        if(statusCoOrderList.size() == 0) {
            statusIndex.remove(coOrder.getStatus());
        }
        return 1;
    }

    public static int update(CoOrder coOrder) {
        CoOrder oldCoOrder = (CoOrder) coOrderData.getOne(coOrder.getId());
        if (oldCoOrder == null) {
            System.out.println(""+oldCoOrder.getId());
        }
        if (coOrder.getStatus().intValue() != oldCoOrder.getStatus().intValue()) {
            AvlTree<CoOrder> statusCoOrderList = statusIndex.get(oldCoOrder.getStatus());
            statusCoOrderList.remove(coOrder);
            if(statusCoOrderList.size() == 0) {
                statusIndex.remove(coOrder.getStatus());
            }

            statusCoOrderList = statusIndex.get(coOrder.getStatus());
            if(statusCoOrderList == null) {
                statusCoOrderList = new AvlTree<CoOrder>();
            }
            statusCoOrderList.add(coOrder.getId(),coOrder);
            statusIndex.put(coOrder.getStatus(),statusCoOrderList);
        }
        if (coOrder.getUid().longValue() != oldCoOrder.getUid().longValue()) {
            AvlTree<CoOrder> uidCoOrderList = uidIndex.get(oldCoOrder.getUid());
            uidCoOrderList.remove(oldCoOrder);
            if(uidCoOrderList.size() == 0) {
                uidIndex.remove(oldCoOrder.getUid());
            }

            uidCoOrderList = uidIndex.get(coOrder.getUid());
            if(uidCoOrderList == null) {
                uidCoOrderList = new AvlTree<CoOrder>();
            }
            uidCoOrderList.add(coOrder.getId(),coOrder);
            uidIndex.put(coOrder.getUid(),uidCoOrderList);
        }
        coOrderData.update(coOrder);

        ctimeIndex.remove(oldCoOrder.getCtime().getTime(),oldCoOrder);
        Long newnow = coOrder.getCtime().getTime();
        ctimeIndex.add(newnow,coOrder);
        return 1;
    }

    public static CoOrder getById(Long id) {
        CoOrder coOrder = (CoOrder) coOrderData.getOne(id);
        return coOrder;
    }

    public static List<Entity> getByUid(Long uid) {
        AvlTree<CoOrder> coOrderList = uidIndex.get(uid);
        if (null == coOrderList) {
            return null;
        }
        return coOrderList.getAll();
    }
    public static List<Entity> getByStatus(Integer status) {
        AvlTree<CoOrder> coOrderList = statusIndex.get(status);
        return coOrderList.getAll();
    }

    public static List<Entity> getByCtime(Date date) {
        return ctimeIndex.getList(date.getTime());
    }
}
