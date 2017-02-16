package dotinc.attendancemanager2.Objects;

/**
 * Created by vellapanti on 17/1/16.
 */
public class SubjectsList {

    public int id;
    public String subjectName;

    public int getPast_total_classes() {
        return past_total_classes;
    }

    public void setPast_total_classes(int past_total_classes) {
        this.past_total_classes = past_total_classes;
    }

    public int getPast_attended_classes() {
        return past_attended_classes;
    }

    public void setPast_attended_classes(int past_attended_classes) {
        this.past_attended_classes = past_attended_classes;
    }

    public int past_total_classes;
    public int past_attended_classes;
    public SubjectsList(int id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }
    public SubjectsList(int id, String subjectName,int past_attended_classes,int past_total_classes){
        this.id = id;
        this.subjectName = subjectName;
        this.past_attended_classes = past_attended_classes;
        this.past_total_classes = past_total_classes;
    }
    public SubjectsList(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }


}
