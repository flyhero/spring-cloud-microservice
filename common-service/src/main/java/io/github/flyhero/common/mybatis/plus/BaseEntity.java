package io.github.flyhero.common.mybatis.plus;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	@TableField(fill = FieldFill.INSERT)
	private Long tenantId;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	@TableLogic
	private Boolean isDeleted;
}
