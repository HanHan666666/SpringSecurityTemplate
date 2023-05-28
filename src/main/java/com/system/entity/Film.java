package com.system.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴晗
 * @since 2023-05-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_film")
public class Film extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    private String name;

    @TableField("release_time")
    private LocalDate releaseTime;

    //序列化json时候排除
    @JsonIgnore
    @TableField("category_id")
    private Long categoryId;

    @TableField("region")
    private String region;

    @TableField("cover")
    private String cover;

    @TableField("duration")
    private Integer duration;

    @TableField("grade")
    private BigDecimal grade;

    //使用mybatis提供的方法查询的时候排除
    @TableField(exist = false, value = "genre")
    @JsonProperty("genre")
    private String genre;
}
