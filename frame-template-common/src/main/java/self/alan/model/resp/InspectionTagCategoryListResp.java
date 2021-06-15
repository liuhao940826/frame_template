package self.alan.model.resp;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname InspectionTagListResp
 * @Description TODO
 * @Date 2021/5/11 下午4:15
 * @Created by liuhao
 */
public class InspectionTagCategoryListResp {

    private Integer id;

    private Integer groupId;

    private String  name;

    private List<InspectionTagListResp> tags =new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InspectionTagListResp> getTags() {
        return tags;
    }

    public void setTags(List<InspectionTagListResp> tags) {

        if(!CollectionUtils.isEmpty(tags)){
            this.tags = tags;
        }

    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
