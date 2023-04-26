package com.demidrolll.pet.bank.domain.client.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

public abstract class DatasourceConfiguration {

  protected LocalContainerEntityManagerFactoryBean buildEntityManagerFactory(
      DataSource dataSource,
      JpaProperties jpaProperties
  ) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("com.demidrolll.pet.bank.domain.client.repository.model");
    em.getJpaPropertyMap().putAll(jpaProperties.getProperties());

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setShowSql(jpaProperties.isShowSql());
    vendorAdapter.setDatabase(jpaProperties.getDatabase());
    vendorAdapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
    vendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaPropertyMap(jpaProperties.getProperties());

    return em;
  }

  protected PlatformTransactionManager buildTransactionManager(LocalContainerEntityManagerFactoryBean emFactoryBean) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emFactoryBean.getObject());
    return transactionManager;
  }
}
