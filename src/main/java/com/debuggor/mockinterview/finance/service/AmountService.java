package com.debuggor.mockinterview.finance.service;

import com.debuggor.mockinterview.finance.bean.Amount;
import com.debuggor.mockinterview.finance.dao.AmountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AmountService {

    @Autowired
    private AmountDao amountDao;

    /**
     * 获得用户的本地金额
     * 如果还未创建则先创建再查询出
     *
     * @param amount
     * @return
     */
    public Amount getUserAmount(Amount amount) {
        Amount a = amountDao.getAmountByUid(amount);
        if (a == null) {
            amount.setAmount(new BigDecimal(0));
            amountDao.insert(amount);
            a = amountDao.getAmountByUid(amount);
        }
        return a;
    }
}
