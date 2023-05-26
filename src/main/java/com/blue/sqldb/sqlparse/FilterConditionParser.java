package com.blue.sqldb.sqlparse;

import java.util.Stack;

public class FilterConditionParser {
    private static final String[] keywords = {"or","and"};
    private static final String[] compare = {"in","=",">","<",">=","<="};

    public static FilterCondition parseWhereSubSql(String whereSubSql) {
        whereSubSql = whereSubSql.strip();
        Stack<Integer> bracketIndexStack = new Stack<Integer>();
        Stack<FilterCondition> filterConditionStack = new Stack<FilterCondition>();
        FilterCondition filterConditionRoot = new FilterCondition();
        filterConditionRoot.setExcpress(whereSubSql);
        for (int i = 0; i < whereSubSql.length(); i++) {
            char character = whereSubSql.charAt(i);
            if(character == '(') {
                bracketIndexStack.add(i);
                FilterCondition filterCondition = new FilterCondition();
                filterConditionStack.add(filterCondition);
            }
            if(character == ')') {
                int lastLeftBracketIndex = bracketIndexStack.pop();
                FilterCondition filterCondition = filterConditionStack.pop();
                String sub = whereSubSql.substring(lastLeftBracketIndex,i+1);
                if (sub.contains(" or ") || sub.contains(" and ")) {
                    filterCondition.setExcpress(sub);
                    FilterCondition filterConditionParent = null;
                    if (filterConditionStack.size() > 0){
                        filterConditionParent = filterConditionStack.peek();
                    } else {
                        filterConditionParent = filterConditionRoot;
                    }
                    filterConditionParent.addChildren(filterCondition);
                    filterCondition.setParent(filterConditionParent);
                }
            }
        }

        setAndOr(filterConditionRoot);
        return filterConditionRoot;
    }

    public static void setAndOr(FilterCondition filterCondition) {
        String excpress = filterCondition.getExcpress();
        if (filterCondition.children != null) {
            for (FilterCondition filterConditionChild : filterCondition.children) {
                String excpressChild = filterConditionChild.getExcpress();
                excpress = excpress.replace(excpressChild,"#");
                setAndOr(filterConditionChild);
            }
        }

        String[] excpressArray = null;
        if(excpress.contains(" or ")) {
            filterCondition.setChildrenIsAnd(false);
            excpressArray = excpress.split(" or ");

        } else {
            filterCondition.setChildrenIsAnd(true);
            excpressArray = excpress.split(" and ");
        }

        for (int i = 0; i < excpressArray.length; i++) {
            String excpressSub = excpressArray[i];
            if (!excpressSub.contains("#")) {
                FilterCondition filterCondition0 = new FilterCondition();
                filterCondition0.setExcpress(excpressSub);
                filterCondition0.parent = filterCondition;
                if (i == 0) {
                    filterCondition.addFirstChildren(filterCondition0);
                } else {
                    filterCondition.addChildren(filterCondition0);
                }
            }
        }
    }

    public static void main(String[] args) {
        String sql = "(uid in (2400,521) or id in (1,34,56)) and (((status = 0 or status = 1) or (ctime >= 0 and ctime <= 100)) and contract_id = 1551033199875420160)";
        FilterConditionParser filterConditionParser = new FilterConditionParser();
        FilterCondition filterCondition = filterConditionParser.parseWhereSubSql(sql);
    }
}