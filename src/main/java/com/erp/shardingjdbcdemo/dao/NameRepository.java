package com.erp.shardingjdbcdemo.dao;

import com.erp.shardingjdbcdemo.domain.ShardingName;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * @author 2p
 * @date 2021/8/19 14:22
 * @desc NameRepository
 */
public interface NameRepository extends JpaRepository<ShardingName, Integer> {
}
