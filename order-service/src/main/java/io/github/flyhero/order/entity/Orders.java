package io.github.flyhero.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.flyhero.common.mybatis.plus.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author flyhero
 * @since 2025-04-22
 */
@Getter
@Setter
@ToString
@TableName("orders")
public class Orders extends BaseEntity {

    private Long programId;

    private Long userId;

    private Long orderNumber;
}
