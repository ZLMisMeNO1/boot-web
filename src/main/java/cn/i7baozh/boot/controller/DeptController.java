package cn.i7baozh.boot.controller;

import cn.i7baozh.boot.bean.DeptBean;
import cn.i7baozh.boot.controller.resp.DeptResp;
import cn.i7baozh.boot.service.DeptService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title:
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
@Slf4j
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController{

    @Autowired
    private DeptService deptService;

    @GetMapping("/view/{id}")
    public DeptResp getById(@PathVariable Long id) {
        DeptBean deptBean = deptService.getById(id);

        DeptResp.DeptRespConverter converter = new DeptResp.DeptRespConverter();

        return converter.convert(deptBean);
    }
    @GetMapping("/list")
    public PageInfo<DeptBean> list(@RequestParam(required = false,defaultValue = "1") int pageNo,
                               @RequestParam(required = false,defaultValue = "5") int pageSize) {

        PageInfo<DeptBean> deptBeans = deptService.listDeptByPage(pageNo, pageSize);
        return deptBeans;
    }

}
