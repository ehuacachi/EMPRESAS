package com.okcompu.ecommerce.backendokcompu.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //Para que no generen Bean
public interface GenericRepo<T, ID> extends JpaRepository<T, ID> {
}
