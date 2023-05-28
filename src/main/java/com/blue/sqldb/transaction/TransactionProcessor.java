package com.blue.sqldb.transaction;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TransactionProcessor {
    public static Map<Socket, String> transactionIdMap = new HashMap<Socket, String>();

    public static void begin() {

    }

    public static void procces(String sql) {

    }

    public static void commit() {

    }

    public static void rollback() {

    }
}