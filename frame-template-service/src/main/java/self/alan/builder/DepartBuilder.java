package self.alan.builder;


import self.alan.po.Depart;


/**
 * @Classname DepartBuilder
 * @Description TODO
 * @Date 2021/5/8 下午4:15
 * @Created by liuhao
 */
public class DepartBuilder {

    private Depart build = new Depart();

    public DepartBuilder id(Integer  id) {
        build.setId(id);
        return this;
    }

    public DepartBuilder name(String  name) {
        build.setName(name);
        return this;
    }

    public DepartBuilder status(Integer  status) {
        build.setStatus(status);
        return this;
    }

    // 构建对象
    public Depart build() {
        return build;
    }

}
