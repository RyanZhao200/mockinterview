package com.debuggor.mockinterview.finance.service;

import com.debuggor.mockinterview.common.enumerate.PayOperateEnum;
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

    /**
     * 用户充值，提现；修改账户金额
     *
     * @param amount
     */
    public String update(Amount amount, String operate) {
        String result = "error";
        Amount beforeAmount = amountDao.getAmountByUid(amount);
        if (PayOperateEnum.RECHARGE.key.equals(operate)) {
            // 充值;不用验证账户还有多少余额
            amount.setAmount(amount.getAmount().add(beforeAmount.getAmount()));
        } else if (PayOperateEnum.EXTRACT.key.equals(operate)) {
            // 提现；需要验证原账户还有多少余额
            BigDecimal subtract = beforeAmount.getAmount().subtract(amount.getAmount());
            if (subtract.doubleValue() >= 0) {
                // 账户余额大于等于要提现的金额
                amount.setAmount(subtract);
            } else {
                // 账户余额不够，不能提现，返回error
                return result;
            }
        }
        result = "success";
        amountDao.update(amount);
        // 更新用户的账户金额
        return result;
    }

    /**
     * 验证面试官是否可以提现
     *
     * @return
     */
    public Boolean canExtract(Amount amount) {
        Amount beforeAmount = amountDao.getAmountByUid(amount);
        // 提现；需要验证原账户还有多少余额
        BigDecimal subtract = beforeAmount.getAmount().subtract(amount.getAmount());
        if (subtract.doubleValue() >= 0) {
            // 账户余额大于等于要提现的金额
            return true;
        } else {
            // 账户余额不够，不能提现，返回error
            return false;
        }
    }

}
