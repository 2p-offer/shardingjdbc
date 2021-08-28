package com.erp.shardingjdbcdemo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 2p
 * @date 2021/8/19 11:56
 * @desc Name
 */
@Data
@Entity
@Table(name = "name")
public class ShardingName implements Serializable {

    private static final long serialVersionUID =  2432499336908976274L;

    @Id
    @Column(name = "uid")
    private Integer uId;

    private String name;

    private int age;

    @Column(name = "create_time")
    private Date createTime;
}
