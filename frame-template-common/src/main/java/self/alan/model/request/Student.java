package self.alan.model.request;


import javax.validation.constraints.NotNull;

public class Student {

  @NotNull(message = "Student id can not be empty")
  private String id;

  @NotNull(message = "Student id can not be empty")
  private String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
