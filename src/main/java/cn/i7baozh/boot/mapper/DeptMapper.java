package cn.i7baozh.boot.mapper;

import cn.i7baozh.boot.bean.DeptBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Title:
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
public interface DeptMapper {

    /**
     * 根据id获取部门信息
     * @param id
     * @return
     */
    DeptBean findById(Long id);

    List<DeptBean> listDepts();
}
