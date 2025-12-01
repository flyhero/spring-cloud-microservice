package io.github.flyhero.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.flyhero.common.mybatis.plus.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
/**
 * <p>
 * 预约抢购
 * </p>
 *
 * @author flyhero
 * @since 2025-05-06
 */
@Getter
@Setter
@ToString
@TableName("reserve_buy")
public class ReserveBuy extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long programId;

    /**
     * 演出场次
     */
    private Long scheduleId;
}
