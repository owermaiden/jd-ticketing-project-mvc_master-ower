package com.cybertek.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)  // it will not change after update...Hibernate server validations
    private LocalDateTime insertDateTime;
    @Column(nullable = false, updatable = false)
    private Long insertUserId;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    private Long LastUpdateUserId;

    private Boolean isDeleted=false;

    @PrePersist
    private void onPrePersist(){
        this.insertDateTime = LocalDateTime.now();
        this.lastUpdateDateTime = LocalDateTime.now();
        this.insertUserId = 1L;
        this.LastUpdateUserId = 1L;
    }

    @PreUpdate
    private void onPreUpdate(){
        this.lastUpdateDateTime = LocalDateTime.now();
        this.LastUpdateUserId = 1L;
    }
}
