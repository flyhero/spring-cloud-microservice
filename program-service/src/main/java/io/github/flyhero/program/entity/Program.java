package io.github.flyhero.program.entity;

import io.github.flyhero.common.mybatis.plus.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * 
 * </p>
 *
 * @author flyhero
 * @since 2025-11-26
 */
@Getter
@Setter
@ToString
public class Program extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 介绍
     */
    private String introduction;

    /**
     * 是否支持选座
     */
    private Boolean chooseSeat;
}
