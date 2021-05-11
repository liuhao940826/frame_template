package com.ovopark.proxy;

import com.ovopark.api.DepartmentApi;
import com.ovopark.expection.ResultCode;
import com.ovopark.expection.SysErrorException;
import com.ovopark.pojo.BaseResult;
import com.ovopark.pojo.IdAndNameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname DepartProxy
 * @Description TODO
 * @Date 2021/5/12 上午12:31
 * @Created by liuhao
 */
@Service
public class DepartProxy {

    @Autowired
    DepartmentApi departmentApi;


    public Map<Integer,String> getDeptNameMap(List<Integer> deptIdList){

        BaseResult<List<IdAndNameVo>> listBaseResult = departmentApi.queryNameOfDept(deptIdList);

        if(listBaseResult.getIsError()){
            throw new SysErrorException(ResultCode.DEPARTMENT_FEIGN_ERROR);
        }

        List<IdAndNameVo> data = listBaseResult.getData();

        if(CollectionUtils.isEmpty(data)){
            return new HashMap<>();
        }


        Map<Integer, String> result = data.stream().collect(Collectors.toMap(IdAndNameVo::getId, IdAndNameVo::getName));

        return result;
    }
}
