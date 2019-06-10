package cn.i7baozh.boot.service;

import cn.i7baozh.boot.bean.DeptBean;

/**
 * @Title:
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
public interface DeptService {

    /**
     * 根据id获取部门
     * @param id
     * @return
     */
    DeptBean getById(Long id);
}
