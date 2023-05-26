package com.blue.sqldb;

import com.blue.sqldb.datastruct.baddtree.Entity;
import com.blue.sqldb.model.CoOrder;
import com.blue.sqldb.model.CoOrderDataGrid;
import com.blue.sqldb.sqlparse.InsertParser;
import com.blue.sqldb.sqlparse.SelectParser;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        long uid = 4L;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 25000; i++) {
            String sql = "INSERT INTO futures.co_order_e_linkusdt(id, client_id, uid, position_type, open, side, type, leverage_level, price, volume, " +
                    "open_taker_fee_rate, open_maker_fee_rate, close_taker_fee_rate, close_maker_fee_rate, realized_amount, deal_volume, deal_money, " +
                    "avg_price, trade_fee, status, memo, source, broker_id, trader_id, order_type, trader_order_id, fingerprint, plat_form, " +
                    "plat_source) VALUES ("+(uid + i)+", '2', 1009834844, 1, 'OPEN', 'SELL', 1, 50, 6.3430000000000000, 1.0000000000000000, 0.00075000, " +
                    "0.00025000, 0.00075000, 0.00025000, 0.0000000000000000, 1, 6.3430000000000000, 6.34300000, 0.0015857500000000, "+ (i%5 +1) +", 0, 1," +
                    "1000, 0, 0, 0, NULL, '', 1);";
            InsertParser.parse(sql);
        }
        System.out.println(System.currentTimeMillis() - start);

        Date now = new Date();
        uid = 4L;
        start = System.currentTimeMillis();
        for (int i = 0; i < 2500; i++) {
            CoOrder coOrder = new CoOrder(uid + i, "2", 20L+i, 1, "OPEN", "SELL", 1, 50,
                    new BigDecimal(6.343), new BigDecimal(1), new BigDecimal(0.00075), new BigDecimal(0.00025), new BigDecimal(0.00025),
                    new BigDecimal(0.00025), new BigDecimal(0.00025), 2, new BigDecimal("0.00025"), new BigDecimal("25"),
                    new BigDecimal("0.00025"), (i%5 +1), 1, 1,1, 1L, "LINKUSDT", true,
                    new BigDecimal(0.00025), 1L, 1,now);
            CoOrderDataGrid.update(coOrder);
        }
        System.out.println(System.currentTimeMillis() - start);

        List<Entity> coOrderList = null;
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            coOrderList = CoOrderDataGrid.getByCtime(now);
        }
        System.out.println(System.currentTimeMillis() - start + "/" + coOrderList.size());

        String sql = "select * from coOrder where uid in (2400,521) and (status = 0 or (ctime >= 0 and ctime <= 100)) and contract_id = 1551033199875420160 order by mtime desc;";
        SelectParser selectParserCoOrder = new SelectParser();
        selectParserCoOrder.parse(sql);
    }
}