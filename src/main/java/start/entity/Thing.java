package start.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Date;

@DataObject(generateConverter = true)
public class Thing {
    private Integer id;
    private String title;
    private Boolean completed;
    private Integer order;
    private Date createDate;

    public Thing(JsonObject obj) {
        // 还未生成Converter的时候需要先注释掉，生成过后再取消注释
        ThingConverter.fromJson(obj, this);
    }

    public Thing(String jsonStr) {
        ThingConverter.fromJson(new JsonObject(jsonStr), this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ThingConverter.toJson(this, json);
        return json;
    }

    public Thing() {
    }

    public Thing(Integer id, String title, Boolean completed, Integer order, Date createDate) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.order = order;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
