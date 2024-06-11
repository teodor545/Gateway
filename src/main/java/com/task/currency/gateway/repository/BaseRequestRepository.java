package com.task.currency.gateway.repository;

import com.task.currency.gateway.entity.BaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BaseRequestRepository extends JpaRepository<BaseRequest, Long> {

    Optional<BaseRequest> findFirstByOrderByRateDateDesc();

    List<BaseRequest> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime);
}
