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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.demidrolll.pet.bank.domain.client.repository",
    excludeFilters = @ComponentScan.Filter(ReadOnlyRepository.class),
    entityManagerFactoryRef = "masterEntityManagerFactory",
    transactionManagerRef = "masterTransactionManager"
)
@EnableTransactionManagement(proxyTargetClass = true)
public class MasterDatasourceConfiguration extends DatasourceConfiguration {

  private final JpaProperties jpaProperties;

  @Autowired
  public MasterDatasourceConfiguration(JpaProperties jpaProperties) {
    this.jpaProperties = jpaProperties;
  }

  @Bean
  @ConfigurationProperties("spring.datasource.master")
  public HikariConfig hikariConfig() {
    return new HikariConfig();
  }

  @Bean
  @Primary
  public DataSource masterDataSource() {
    return new HikariDataSource(hikariConfig());
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory() {
    return buildEntityManagerFactory(masterDataSource(), jpaProperties);
  }

  @Bean
  public PlatformTransactionManager masterTransactionManager() {
    return buildTransactionManager(masterEntityManagerFactory());
  }
}
