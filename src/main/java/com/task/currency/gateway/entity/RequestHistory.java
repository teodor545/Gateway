package com.task.currency.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request_history")
public class RequestHistory {

    @Id
    @Column(name = "request_id")
    private String requestId;

    private LocalDateTime timestamp;

    @Column(name = "client_id")
    private Long clientId;

    private String currency;

    @Column(name = "service_name")
    private String serviceName;
}
