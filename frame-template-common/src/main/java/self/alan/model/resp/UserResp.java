package self.alan.model.resp;

import java.io.Serializable;

/**
 * @Classname UserResp
 * @Description TODO
 * @Date 2021/6/16 下午10:52
 * @Created by liuhao
 */
public class UserResp implements Serializable {

    private String name;

    private Integer age;

    private String birth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
