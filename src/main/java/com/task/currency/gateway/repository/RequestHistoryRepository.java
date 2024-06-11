package com.task.currency.gateway.repository;

import com.task.currency.gateway.entity.BaseRequest;
import com.task.currency.gateway.entity.RequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestHistoryRepository extends JpaRepository<RequestHistory, String> {
}
