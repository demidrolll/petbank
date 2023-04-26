package com.demidrolll.pet.bank.domain.client.config;

public enum TransactionManager {
  MASTER,
  SLAVE,
  ;

  public static final String MASTER_BEAN = "masterTransactionManager";
  public static final String SLAVE_BEAN = "slaveTransactionManager";
}
