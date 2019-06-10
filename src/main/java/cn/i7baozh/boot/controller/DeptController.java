package cn.i7baozh.boot.controller;

import cn.i7baozh.boot.bean.DeptBean;
import cn.i7baozh.boot.controller.resp.DeptResp;
import cn.i7baozh.boot.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
