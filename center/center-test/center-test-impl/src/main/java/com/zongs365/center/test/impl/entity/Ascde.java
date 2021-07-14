package com.zongs365.center.test.impl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 测试
 * </p>
 *
 * @author jobob
 * @since 2021-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ascde")
public class Ascde implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 删除标记
     */
    private Boolean isDeleted;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 名字
     */
    private String name;


}
