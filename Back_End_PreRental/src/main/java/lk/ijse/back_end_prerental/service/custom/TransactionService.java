package lk.ijse.back_end_prerental.service.custom;

import lk.ijse.back_end_prerental.dto.TransactionDTO;

/**
 * Author: vishmee
 * Date: 3/31/25
 * Time: 8:09 PM
 * Description:
 */

public interface TransactionService {
    int saveTransaction(TransactionDTO transactionDTO);
}
