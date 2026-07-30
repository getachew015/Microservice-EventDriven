package com.app.loans.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BaseEntity {

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdDate;  //TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
    @Column(name = "created_by", updatable = false)
    private String createdBy = "SYSTEM";
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedDate; // TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    @Column(name = "updated_by", insertable = false)
    private String updatedBy;
}
