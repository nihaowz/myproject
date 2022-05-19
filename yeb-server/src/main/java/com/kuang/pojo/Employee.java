package com.kuang.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author sx-9773
 * @since 2022-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_employee")
@ApiModel(value="Employee对象", description="")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "员工编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工姓名")
    @TableField("name")
    @Excel(name = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    @Excel(name = "性别")
    private String gender;

    @ApiModelProperty(value = "出生日期")
    @TableField("birthday")
    @Excel(name = "出生日期",width = 20,format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTC")
    private LocalDate birthday;

    @ApiModelProperty(value = "身份证号")
    @TableField("idCard")
    @Excel(name = "身份证号",width = 30)
    private String idCard;

    @ApiModelProperty(value = "婚姻状况")
    @TableField("wedlock")
    @Excel(name = "婚姻状况")
    private String wedlock;

    @ApiModelProperty(value = "民族")
    @TableField("nationId")
    private Integer nationId;

    @ApiModelProperty(value = "籍贯")
    @TableField("nativePlace")
    @Excel(name = "籍贯")
    private String nativePlace;

    @ApiModelProperty(value = "政治面貌")
    @TableField("politicId")
    private Integer politicId;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    @Excel(name = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话号码")
    @TableField("phone")
    @Excel(name = "电话号码")
    private String phone;

    @ApiModelProperty(value = "联系地址")
    @TableField("address")
    @Excel(name = "联系地址")
    private String address;

    @ApiModelProperty(value = "所属部门")
    @TableField("departmentId")
    private Integer departmentId;

    @ApiModelProperty(value = "职称ID")
    @TableField("jobLevelId")
    private Integer jobLevelId;

    @ApiModelProperty(value = "职位ID")
    @TableField("posId")
    private Integer posId;

    @ApiModelProperty(value = "聘用形式")
    @TableField("engageForm")
    @Excel(name = "聘用形式")
    private String engageForm;

    @ApiModelProperty(value = "最高学历")
    @TableField("tiptopDegree")
    @Excel(name = "最高学历")
    private String tiptopDegree;

    @ApiModelProperty(value = "所属专业")
    @TableField("specialty")
    @Excel(name = "所属专业")
    private String specialty;

    @ApiModelProperty(value = "毕业院校")
    @TableField("school")
    @Excel(name = "毕业院校")
    private String school;

    @ApiModelProperty(value = "入职日期")
    @TableField("beginDate")
    @Excel(name = "入职日期",width = 20,format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTC")
    private LocalDate beginDate;

    @ApiModelProperty(value = "在职状态")
    @TableField("workState")
    @Excel(name = "在职状态")
    private String workState;

    @ApiModelProperty(value = "工号")
    @TableField("workID")
    @Excel(name = "工号")
    private String workID;

    @ApiModelProperty(value = "合同期限")
    @TableField("contractTerm")
    @Excel(name = "合同期限")
    private Double contractTerm;

    @ApiModelProperty(value = "转正日期")
    @TableField("conversionTime")
    @Excel(name = "转正日期",width = 20,format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTC")
    private LocalDate conversionTime;

    @ApiModelProperty(value = "离职日期")
    @TableField("notWorkDate")
    @Excel(name = "离职日期",width = 20,format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTC")
    private LocalDate notWorkDate;

    @ApiModelProperty(value = "合同起始日期")
    @TableField("beginContract")
    @Excel(name = "合同起始日期",width = 20,format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTC")
    private LocalDate beginContract;

    @ApiModelProperty(value = "合同终止日期")
    @TableField("endContract")
    @Excel(name = "合同终止日期",width = 20,format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTC")
    private LocalDate endContract;

    @ApiModelProperty(value = "工龄")
    @TableField("workAge")
    @Excel(name = "工龄")
    private Integer workAge;

    @ApiModelProperty(value = "工资账套ID")
    @TableField("salaryId")
    private Integer salaryId;


    /**
     * 外键id对应其中的数据库表
     */

    @ApiModelProperty(value = "部门表")
    @TableField(exist = false)
    @ExcelEntity(name = "部门")
    private Department department;

    @ApiModelProperty(value = "职位级别")
    @TableField(exist = false)
    @ExcelEntity(name = "职位级别")
    private Joblevel joblevel;

    @ApiModelProperty(value = "职位")
    @TableField(exist = false)
    @ExcelEntity(name = "职位")
    private  Position position;

    @ApiModelProperty("民族")
    @ExcelEntity(name = "民族")
    @TableField(exist = false)
    private Nation nation;

    @ApiModelProperty("政治面貌")
    @TableField(exist = false)
    @ExcelEntity(name = "政治面貌")
    private  PoliticsStatus politicsStatus;


}
