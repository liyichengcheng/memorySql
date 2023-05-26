package com.blue.sqldb.model;

import com.blue.sqldb.datastruct.baddtree.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CoOrder extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户端订单标识
     */
    private String clientId;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 持仓类型(1 全仓，2 仓逐)
     */
    private Integer positionType;

    /**
     * 开平仓方向(open 开仓，close 平仓)
     */
    private String open;

    /**
     * 买卖方向（buy 买入，sell 卖出）
     */
    private String side;

    /**
     * 订单类型(1 limit; 2 market; 3 IOC; 4 FOK; 5 POST_ONLY; 6 爆仓,仅在页面展示,非数据库中记录类型;)
     */
    private Integer type;

    /**
     * 杠杆倍数
     */
    private Integer leverageLevel;

    /**
     * 下单价格
     */
    private BigDecimal price;

    /**
     * 下单数量(开仓市价单：金额)
     */
    private BigDecimal volume;

    /**
     * 开仓maker手续费
     */
    private BigDecimal openTakerFeeRate;

    /**
     * 开仓taker手续费
     */
    private BigDecimal openMakerFeeRate;

    /**
     * 平仓maker手续费
     */
    private BigDecimal closeTakerFeeRate;

    /**
     * 平仓taker手续费
     */
    private BigDecimal closeMakerFeeRate;

    /**
     * 订单累计盈亏
     */
    private BigDecimal realizedAmount;

    /**
     * 已成交的数量（张）
     */
    private Integer dealVolume;

    /**
     * 已成交的金额
     */
    private BigDecimal dealMoney;

    /**
     * 成交均价
     */
    private BigDecimal avgPrice;

    /**
     * 交易手续费
     */
    private BigDecimal tradeFee;

    /**
     * 订单状态（订单状态：0 init，1 new，2 filled，3 part_filled，4 canceled，5 pending_cancel，6 expired）
     */
    private Integer status;
    private Integer memo;
    private Integer source;
    private Date ctime;
    private Date mtime;
    private Integer brokerId;
    /***
     * 交易员Id
     */
    private Long traderId;

    private Long traderOrderId;
    private String fingerprint;

    /***
     * 0：普通，1：带单，2：跟单
     */
    private Integer orderType;

    /**
     * 平台类型1：web、2：h5 3：IOS、4：android
     */
    private Integer platSource;

    private Long positionId;
    private String symbol;
    private boolean existDB;
    private BigDecimal thisFee;

    public CoOrder(final Long id, final String clientId, final Long uid, final Integer positionType, final String open, final String side, final Integer type,
                   final Integer leverageLevel, final BigDecimal price, final BigDecimal volume, final BigDecimal openTakerFeeRate,
                   final BigDecimal openMakerFeeRate, final BigDecimal closeTakerFeeRate, final BigDecimal closeMakerFeeRate, final BigDecimal realizedAmount,
                   final Integer dealVolume, final BigDecimal dealMoney, final BigDecimal avgPrice, final BigDecimal tradeFee, final Integer status,
                   final Integer memo, final Integer source, final Integer brokerId, final Long positionId,final String symbol,
                   final boolean existDB, final BigDecimal thisFee, final Long traderId, final Integer orderType,final Date ctime) {
        super(id);
        this.clientId = clientId;
        this.uid = uid;
        this.positionType = positionType;
        this.open = open;
        this.side = side;
        this.type = type;
        this.leverageLevel = leverageLevel;
        this.price = price;
        this.volume = volume;
        this.openTakerFeeRate = openTakerFeeRate;
        this.openMakerFeeRate = openMakerFeeRate;
        this.closeTakerFeeRate = closeTakerFeeRate;
        this.closeMakerFeeRate = closeMakerFeeRate;
        this.realizedAmount = realizedAmount;
        this.dealVolume = dealVolume;
        this.dealMoney = dealMoney;
        this.avgPrice = avgPrice;
        this.tradeFee = tradeFee;
        this.status = status;
        this.memo = memo;
        this.source = source;
        this.ctime = ctime;
        this.mtime = ctime;
        this.brokerId = brokerId;
        this.positionId = positionId;
        this.symbol = symbol;
        this.existDB = existDB;
        this.thisFee = thisFee;
        this.traderId = traderId;
        this.orderType = orderType;
    }

    private static boolean $default$existDB() {
        return false;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    private static BigDecimal $default$thisFee() {
        return BigDecimal.ZERO;
    }

    public boolean isInit() {
        return this.dealVolume == 0;
    }

    public boolean isClose() {
        return "CLOSE".equals(this.open);
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public void sumFee(BigDecimal fee) {
        this.thisFee = this.thisFee.add(fee);
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(final Long uid) {
        this.uid = uid;
    }

    public Integer getPositionType() {
        return this.positionType;
    }

    public void setPositionType(final Integer positionType) {
        this.positionType = positionType;
    }

    public String getOpen() {
        return this.open;
    }

    public void setOpen(final String open) {
        this.open = open;
    }

    public String getSide() {
        return this.side;
    }

    public void setSide(final String side) {
        this.side = side;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(final Integer type) {
        this.type = type;
    }

    public Integer getLeverageLevel() {
        return this.leverageLevel;
    }

    public void setLeverageLevel(final Integer leverageLevel) {
        this.leverageLevel = leverageLevel;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVolume() {
        return this.volume;
    }

    public void setVolume(final BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getOpenTakerFeeRate() {
        return this.openTakerFeeRate;
    }

    public void setOpenTakerFeeRate(final BigDecimal openTakerFeeRate) {
        this.openTakerFeeRate = openTakerFeeRate;
    }

    public BigDecimal getOpenMakerFeeRate() {
        return this.openMakerFeeRate;
    }

    public void setOpenMakerFeeRate(final BigDecimal openMakerFeeRate) {
        this.openMakerFeeRate = openMakerFeeRate;
    }

    public BigDecimal getCloseTakerFeeRate() {
        return this.closeTakerFeeRate;
    }

    public void setCloseTakerFeeRate(final BigDecimal closeTakerFeeRate) {
        this.closeTakerFeeRate = closeTakerFeeRate;
    }

    public BigDecimal getCloseMakerFeeRate() {
        return this.closeMakerFeeRate;
    }

    public void setCloseMakerFeeRate(final BigDecimal closeMakerFeeRate) {
        this.closeMakerFeeRate = closeMakerFeeRate;
    }

    public BigDecimal getRealizedAmount() {
        return this.realizedAmount;
    }

    public void setRealizedAmount(final BigDecimal realizedAmount) {
        this.realizedAmount = realizedAmount;
    }

    public Integer getDealVolume() {
        return this.dealVolume;
    }

    public void setDealVolume(final Integer dealVolume) {
        this.dealVolume = dealVolume;
    }

    public BigDecimal getDealMoney() {
        return this.dealMoney;
    }

    public void setDealMoney(final BigDecimal dealMoney) {
        this.dealMoney = dealMoney;
    }

    public BigDecimal getAvgPrice() {
        return this.avgPrice;
    }

    public void setAvgPrice(final BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getTradeFee() {
        return this.tradeFee;
    }

    public void setTradeFee(final BigDecimal tradeFee) {
        this.tradeFee = tradeFee;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public Integer getMemo() {
        return this.memo;
    }

    public void setMemo(final Integer memo) {
        this.memo = memo;
    }

    public Integer getSource() {
        return this.source;
    }

    public void setSource(final Integer source) {
        this.source = source;
    }

    public Date getCtime() {
        return this.ctime;
    }

    public void setCtime(final Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return this.mtime;
    }

    public void setMtime(final Date mtime) {
        this.mtime = mtime;
    }

    public Integer getBrokerId() {
        return this.brokerId;
    }

    public void setBrokerId(final Integer brokerId) {
        this.brokerId = brokerId;
    }

    public Long getPositionId() {
        return this.positionId;
    }

    public void setPositionId(final Long positionId) {
        this.positionId = positionId;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public boolean isExistDB() {
        return this.existDB;
    }

    public void setExistDB(final boolean existDB) {
        this.existDB = existDB;
    }

    public BigDecimal getThisFee() {
        return this.thisFee;
    }

    public void setThisFee(final BigDecimal thisFee) {
        this.thisFee = thisFee;
    }

    public Long getTraderOrderId() {
        return traderOrderId;
    }

    public void setTraderOrderId(Long traderOrderId) {
        this.traderOrderId = traderOrderId;
    }

    public Integer getPlatSource() {
        return platSource;
    }

    public void setPlatSource(Integer platSource) {
        this.platSource = platSource;
    }

    @Override
    public String toString() {
        return "CoOrder(id=" + id + ", clientId=" + this.getClientId() + ", uid=" + this.getUid() + ", positionType=" + this.getPositionType() + ", open=" + this.getOpen() + ", side=" + this.getSide() + ", type=" + this.getType() + ", leverageLevel=" + this.getLeverageLevel() + ", price=" + this.getPrice() + ", volume=" + this.getVolume() + ", openTakerFeeRate=" + this.getOpenTakerFeeRate() + ", openMakerFeeRate=" + this.getOpenMakerFeeRate() + ", closeTakerFeeRate=" + this.getCloseTakerFeeRate() + ", closeMakerFeeRate=" + this.getCloseMakerFeeRate() + ", realizedAmount=" + this.getRealizedAmount() + ", dealVolume=" + this.getDealVolume() + ", dealMoney=" + this.getDealMoney() + ", avgPrice=" + this.getAvgPrice() + ", tradeFee=" + this.getTradeFee() + ", status=" + this.getStatus() + ", memo=" + this.getMemo() + ", source=" + this.getSource() + ", ctime=" + this.getCtime() + ", mtime=" + this.getMtime() + ", brokerId=" + this.getBrokerId() + ", positionId=" + this.getPositionId() + ", symbol=" + this.getSymbol() + ", existDB=" + this.isExistDB() + ", thisFee=" + this.getThisFee() + ")";
    }

    public static CoOrder build(String[] filedsArray,String[] valueArray) {
        Long id = null;
        String clientId = null;
        Long uid = null;
        Integer positionType = null;
        String open = null;
        String side = null;
        Integer type = 0;
        Integer leverageLevel = null;
        BigDecimal price = null;
        BigDecimal volume = null;
        BigDecimal openTakerFeeRate = null;
        BigDecimal openMakerFeeRate = null;
        BigDecimal closeTakerFeeRate = null;
        BigDecimal closeMakerFeeRate = null;
        BigDecimal realizedAmount = null;
        Integer dealVolume = null;
        BigDecimal dealMoney = null;
        BigDecimal avgPrice = null;
        BigDecimal tradeFee = null;
        Integer status = 0;
        Integer memo = null;
        Integer source = null;
        Integer brokerId = null;
        Long positionId = null;
        String symbol = null;
        boolean existDB = false;
        BigDecimal thisFee = null;
        Long traderId = null;
        Integer orderType = 0;

        for (int i = 0; i < filedsArray.length; i++) {
            String field = filedsArray[i];
            if(field.equals("id")) {
                id = Long.parseLong(valueArray[i]);
            } else if(field.equals("client_id")) {
                clientId = valueArray[i];
            } else if(field.equals("uid")) {
                uid = Long.parseLong(valueArray[i]);
            } else if(field.equals("position_type")) {
                positionType = Integer.parseInt(valueArray[i]);
            } else if(field.equals("open")) {
                open = valueArray[i];
            } else if(field.equals("side")) {
                side = valueArray[i];
            } else if(field.equals("type")) {
                type = Integer.parseInt(valueArray[i]);
            } else if(field.equals("leverage_level")) {
                leverageLevel = Integer.parseInt(valueArray[i]);
            } else if(field.equals("price")) {
                price = new BigDecimal(valueArray[i]);
            } else if(field.equals("volume")) {
                volume = new BigDecimal(valueArray[i]);
            } else if(field.equals("open_taker_fee_rate")) {
                openTakerFeeRate = new BigDecimal(valueArray[i]);
            } else if(field.equals("open_maker_fee_rate")) {
                openMakerFeeRate = new BigDecimal(valueArray[i]);
            } else if(field.equals("close_taker_fee_rate")) {
                closeTakerFeeRate = new BigDecimal(valueArray[i]);
            } else if(field.equals("close_maker_fee_rate")) {
                closeMakerFeeRate = new BigDecimal(valueArray[i]);
            } else if(field.equals("realized_amount")) {
                realizedAmount = new BigDecimal(valueArray[i]);
            } else if(field.equals("deal_volume")) {
                dealVolume = Integer.parseInt(valueArray[i]);
            } else if(field.equals("deal_money")) {
                dealMoney = new BigDecimal(valueArray[i]);
            } else if(field.equals("avg_price")) {
                avgPrice = new BigDecimal(valueArray[i]);
            } else if(field.equals("trade_fee")) {
                tradeFee = new BigDecimal(valueArray[i]);
            } else if(field.equals("status")) {
                status = Integer.parseInt(valueArray[i]);
            } else if(field.equals("memo")) {
                memo = Integer.parseInt(valueArray[i]);
            } else if(field.equals("source")) {
                source = Integer.parseInt(valueArray[i]);
            } else if(field.equals("broker_id")) {
                brokerId = Integer.parseInt(valueArray[i]);
            } else if(field.equals("positionId")) {
                positionId = Long.parseLong(valueArray[i]);
            } else if(field.equals("symbol")) {
                symbol = valueArray[i];
            } else if(field.equals("existDB")) {
                existDB = Boolean.parseBoolean(valueArray[i]);
            } else if(field.equals("thisFee")) {
                thisFee = new BigDecimal(valueArray[i]);
            } else if(field.equals("trader_id")) {
                traderId = Long.parseLong(valueArray[i]);
            } else if(field.equals("order_type")) {
                orderType = Integer.parseInt(valueArray[i]);
            }
        }

        CoOrder coOrder = new CoOrder(id,clientId,uid,positionType,open,side,type,leverageLevel,price,volume,openTakerFeeRate,
           openMakerFeeRate,closeTakerFeeRate,closeMakerFeeRate,realizedAmount, dealVolume,dealMoney,avgPrice,tradeFee, status,
           memo, source,brokerId,positionId,symbol, existDB,thisFee,traderId,orderType,new Date());
        return coOrder;
    }
}