package cn.i7baozh.boot.controller.resp;

import cn.i7baozh.boot.bean.DeptBean;
import com.google.common.base.Converter;
import lombok.Builder;
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
@Builder
public class DeptResp implements Serializable {

    private Long id;

    private String deptName;

    public static class DeptRespConverter extends Converter<DeptBean, DeptResp> {

        @Override
        protected DeptResp doForward(DeptBean deptBean) {
            DeptRespBuilder deptRespBuilder = DeptResp.builder()
                    .id(deptBean.getId())
                    .deptName(deptBean.getDeptName());
            return deptRespBuilder.build();
        }

        @Override
        protected DeptBean doBackward(DeptResp deptResp) {
            return null;
        }
    }

}
