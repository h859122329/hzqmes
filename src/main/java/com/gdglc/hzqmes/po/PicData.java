package com.gdglc.hzqmes.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 图片存储表
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PicData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标识使用图片的实体/表, 如:学生、班主任、活动、咨询服务、商品等
     */
    @TableField("entity")
    private String entity;

    /**
     * 对应实体所的ID,如: user_id
     */
    @TableField("entity_id")
    private Integer entityId;

    /**
     * 图片所在的字段
     */
    @TableField("entity_field")
    private String entityField;

    /**
     * 多图字段表示图片顺序
     */
    @TableField("seq_no")
    private Integer seqNo;

    /**
     * 图片保存路径
     */
    @TableField("pic_path")
    private String picPath;

}
