package io.github.flyhero.order.entity;

import io.github.flyhero.common.mybatis.plus.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author flyhero
 * @since 2025-04-29
 */
@Getter
@Setter
@ToString
@TableName("test")
public class Test extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String data;
}
