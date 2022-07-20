package self.alan.model.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Teacher {

  @NotNull(message = "teacher id can not be empty")
  private String id;


  @Valid
  private List<Student> studentList;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Student> getStudentList() {
    return studentList;
  }

  public void setStudentList(List<Student> studentList) {
    this.studentList = studentList;
  }
}
