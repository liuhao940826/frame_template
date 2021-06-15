package self.alan.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import self.alan.annotation.AutoCache;
import self.alan.annotation.DelCache;
import self.alan.constants.CacheKeyConsts;
import self.alan.context.HttpContext;
import self.alan.model.req.TestEsReq;
import self.alan.model.resp.JsonNewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.alan.po.Depart;
import self.alan.utils.EsHighClientUtls;
import self.alan.utils.RedisUtil;

import java.io.IOException;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/6/8 下午1:55
 * @Created by liuhao
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private EsHighClientUtls esUtils;

    @Autowired
    RedisUtil redisUtil;


    @RequestMapping(value="/testEs")
    @ResponseBody
    public JsonNewResult<String> testEs(@RequestBody TestEsReq req){

        try {
//            esUtils.createIndex(req.getIndex(), req.getType(), null, req.getJsonStr());


            esUtils.addDocByJson(req.getIndex(),req.getType(),null,req.getJsonStr());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JsonNewResult.success();
    }



    @RequestMapping(value="/test/redis")
    @ResponseBody
    @AutoCache(value = CacheKeyConsts.TEST,
            expire = 60 * 60 * 3)
    @DelCache(CacheKeyConsts.TEST)
    public JsonNewResult<String> testRedisTemplate() {

        boolean isSucess = redisUtil.set(CacheKeyConsts.TEST, "我是测试数据");

        String value = (String)redisUtil.get(CacheKeyConsts.TEST);

        System.out.println(value);

        redisUtil.del(CacheKeyConsts.TEST);

        value= (String)redisUtil.get(CacheKeyConsts.TEST);

        System.out.println(value);

        return null;
    }

}
