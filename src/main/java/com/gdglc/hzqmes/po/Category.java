package com.gdglc.hzqmes.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "catid", type = IdType.AUTO)
    private Integer catid;

    /**
     * 模块 1:产品分类 2:客户分类 3:供应商分类 4：原材料分类
     */
    @TableField("module_id")
    private Integer moduleId;

    /**
     * 分类名称
     */
    @TableField("catname")
    private String catname;

    /**
     * 栏目目录
     */
    @TableField("catdir")
    private String catdir;

    /**
     * 链接地址
     */
    @TableField("linkurl")
    private String linkurl;

    /**
     * 字母索引
     */
    @TableField("letter")
    private String letter;

    /**
     * 级别
     */
    @TableField("level")
    private Integer level;

    /**
     * 信息数量
     */
    @TableField("item")
    private Long item;

    /**
     * 属性数量
     */
    @TableField("property")
    private Integer property;

    /**
     * 上级ID
     */
    @TableField("parentid")
    private Integer parentid;

    /**
     * 上级所有ID
     */
    @TableField("arrparentid")
    private String arrparentid;

    /**
     * 是否有子分类 0: 无 1:有
     */
    @TableField("child")
    private Boolean child;

    /**
     * 子分类所有ID
     */
    @TableField("arrchildid")
    private String arrchildid;

    /**
     * 排序因子
     */
    @TableField("listorder")
    private Integer listorder;

    /**
     * 栏目模板
     */
    @TableField("template")
    private String template;

    /**
     * 内容模版
     */
    @TableField("show_template")
    private String showTemplate;

    /**
     * 允许浏览栏目的会员组
     */
    @TableField("group_list")
    private String groupList;

    /**
     * 允许浏览栏目信息内容的会员组
     */
    @TableField("group_show")
    private String groupShow;

    /**
     * 允许在栏目发布信息的会员组
     */
    @TableField("group_add")
    private String groupAdd;


}
