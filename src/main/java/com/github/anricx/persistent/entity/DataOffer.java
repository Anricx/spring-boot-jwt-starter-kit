package com.github.anricx.persistent.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by dengt on 2019/8/31
 */
@Setter
@Getter
@Entity(name = "_data_offer")
public class DataOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Batch Number
     */
    @Column(unique = true, nullable = false, updatable = false)
    private String batchNo;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updateTime;

    /**
     * 发放年月
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date offerTime;

}
