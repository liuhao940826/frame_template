package self.alan.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import self.alan.annotation.AutoCache;
import self.alan.annotation.DelCache;
import self.alan.constants.CacheKeyConsts;
import self.alan.context.HttpContext;
import self.alan.mapper.TestMapper;
import self.alan.model.resp.JsonNewResult;
import self.alan.po.Depart;
import self.alan.utils.RedisUtil;

import java.util.List;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/3/12 下午5:19
 * @Created by liuhao
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    TestMapper testMapper;


    @Autowired
    RedisUtil redisUtil;

    /**
     * 删除草稿箱的任务
     * @return
     */
    @RequestMapping(value="/test")
    @ResponseBody
    public JsonNewResult<?> test(@RequestParam("id")Integer id){

        log.trace("======trace");
        log.debug("======debug");
        log.info(HttpContext.getTraceId() +"======info");
        log.warn("======warn");
        log.error("======error");


        PageInfo<Depart> pageInfo = null;

        PageHelper.startPage(1,2);

        List<Depart> test = testMapper.test();
        pageInfo = new PageInfo<Depart>(test);

        return JsonNewResult.success(pageInfo);
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