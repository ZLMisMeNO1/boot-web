package cn.i7baozh.boot.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
@Data
public class DeptBean implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String deptName;
}
