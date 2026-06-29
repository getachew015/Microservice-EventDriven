package com.app.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BaseEntity {

    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdDate;  //TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
    @Column(updatable = false)
    private String createdBy = "SYSTEM";
    @Column(name = "updatedAt", insertable = false)
    private LocalDateTime updatedDate; // TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    @Column(insertable = false)
    private String updatedBy;
}
