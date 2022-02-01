package com.tipikae.paymybuddy.converters;

import com.tipikae.paymybuddy.dto.TransactionDTO;
import com.tipikae.paymybuddy.entities.Transfer;

/**
 * Converter interface Transfer list to TransactionDTO list.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IConverterListTransferToTransactionDTO extends IConverterListToDTO<Transfer, TransactionDTO> {

}
