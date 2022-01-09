package com.wrc.tutor.common.entity.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayQueryBizContent implements Serializable {

//    out_trade_no 外部订单号 特殊可选
    private String outTradeNo;

//    trade_no 支付宝交易号 特殊可选
    private String tradeNo;

}
