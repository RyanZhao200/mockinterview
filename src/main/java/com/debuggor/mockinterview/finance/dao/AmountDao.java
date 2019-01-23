package com.debuggor.mockinterview.finance.dao;

import com.debuggor.mockinterview.finance.bean.Amount;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AmountDao {
    /**
     * 根据用户ID获得用户本地钱包金额
     *
     * @param amount
     * @return
     */
    Amount getAmountByUid(Amount amount);

    /**
     * 跟新用户的金额
     *
     * @param amount
     */
    void update(Amount amount);

    /**
     * 插入用户的金额
     *
     * @param amount
     */
    void insert(Amount amount);

    /**
     * 获得用户的所有金额
     *
     * @return
     */
    BigDecimal getUserAmountSum(Amount amount);
}
