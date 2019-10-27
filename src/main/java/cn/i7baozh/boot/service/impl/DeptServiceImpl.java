package cn.i7baozh.boot.service.impl;

import cn.i7baozh.boot.bean.DeptBean;
import cn.i7baozh.boot.mapper.DeptMapper;
import cn.i7baozh.boot.service.DeptService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title:
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
@Slf4j
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    /**
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(cacheNames = "dept",condition="#result!=null" ,unless = "#result == null")
    public DeptBean getById(Long id) {
        DeptBean deptBean = deptMapper.findById(id);
        log.info("db search by id : {}", JSONObject.toJSONString(deptBean));
        return deptBean;
    }


    @Override
    public PageInfo<DeptBean> listDeptByPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize, true);
        List<DeptBean> deptBeans = deptMapper.listDepts();
        return new PageInfo<DeptBean>(deptBeans);
    }
}
