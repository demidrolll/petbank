package com.demidrolll.pet.bank.domain.client.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.demidrolll.pet.bank.domain.client.repository",
    includeFilters = @ComponentScan.Filter(SlaveRepository.class),
    entityManagerFactoryRef = "slaveEntityManagerFactory",
    transactionManagerRef = TransactionManager.SLAVE_BEAN
)
@EnableTransactionManagement(proxyTargetClass = true)
public class SlaveDatasourceConfiguration extends DatasourceConfiguration {

  private final JpaProperties jpaProperties;

  @Autowired
  public SlaveDatasourceConfiguration(JpaProperties jpaProperties) {
    this.jpaProperties = jpaProperties;
  }

  @Bean
  @ConfigurationProperties("spring.datasource.slave")
  public HikariConfig slaveJpaConfig() {
    return new HikariConfig();
  }

  @Bean
  public DataSource slaveDataSource() {
    return new HikariDataSource(slaveJpaConfig());
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean slaveEntityManagerFactory() {
    return buildEntityManagerFactory(slaveDataSource(), jpaProperties);
  }

  @Bean
  public PlatformTransactionManager slaveTransactionManager() {
    return buildTransactionManager(slaveEntityManagerFactory());
  }
}
