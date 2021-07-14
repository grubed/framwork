package com.zongs365.center.diboot.api;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.diboot.core.util.D;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
* 测试 Entity定义
* @author MyName
* @version 1.0
* @date 2021-01-14
* Copyright © MyCompany
*/
@Getter @Setter @Accessors(chain = true)
public class TestAscde extends BaseCustomEntity {
    private static final long serialVersionUID = 7903276954890537328L;

    /**
    * 创建人
    */
    @TableField()
    private Long createBy;

    /**
    * 更新时间
    */
    @JSONField(format = D.FORMAT_DATETIME_Y4MDHM)
    @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NOT_NULL)
    private Date updateTime;

    /**
    * 名字
    */
    @Length(max=100, message="名字长度应小于100")
    @TableField()
    private String name;


}
