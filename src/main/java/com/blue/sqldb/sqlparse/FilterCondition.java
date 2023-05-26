package com.blue.sqldb.sqlparse;

import java.util.LinkedList;
import java.util.List;

public class FilterCondition {
    String excpress;
    boolean childrenIsAnd = true;
    LinkedList<FilterCondition> children;
    FilterCondition parent;
    byte result = 0;// -1 表示 false;1 表示 true

    public FilterCondition() {
        this.children = new LinkedList<>();
    }

    public String getExcpress() {
        return excpress;
    }

    public void setExcpress(String excpress) {
        this.excpress = excpress;
    }

    public boolean isChildrenIsAnd() {
        return childrenIsAnd;
    }

    public void setChildrenIsAnd(boolean childrenIsAnd) {
        this.childrenIsAnd = childrenIsAnd;
    }

    public FilterCondition getParent() {
        return parent;
    }

    public void setParent(FilterCondition parent) {
        this.parent = parent;
    }

    public List<FilterCondition> getChildren() {
        return children;
    }

    public void addChildren(FilterCondition filterCondition) {
        this.children.add(filterCondition);
    }

    public void addFirstChildren(FilterCondition filterCondition) {
        this.children.addFirst(filterCondition);
    }

    public byte isResult() {
        return result;
    }

    public void setResult(byte result) {
        this.result = result;
    }
}
