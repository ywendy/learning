package com.ypb.canal.redis.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TransDetailEntry implements Serializable {
    private Long transID;

    private Long linkTransID;

    private Long groupID;

    private Long cardID;

    private Byte transType;

    private BigDecimal saveMoneyAmount;

    private BigDecimal saveReturnMoneyAmount;

    private BigDecimal deductionMoneyAmount;

    private BigDecimal giveBalancePay;

    private BigDecimal transAfterMoneyBalance;

    private BigDecimal newTransAfterMoneyBalance;

    private BigDecimal transAfterPointBalance;

    private BigDecimal newTransAfterPointBalance;

    private BigDecimal transAfterGiveBalance;

    private BigDecimal newTransAfterGiveBalance;

    private String transReceiptsTxt;

    private Boolean cancelStatus;

    private Integer action;

    private Date createStamp;

    private Date actionStamp;

    private static final long serialVersionUID = 1L;
}