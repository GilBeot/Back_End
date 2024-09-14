package gilBeot.Course.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Builder
@Getter
public class routeList {
    @Id
    private String routeIdx;
    @Column
    private String routeName;
    @Column(columnDefinition = "TEXT")
    private String content;

    public routeList(String routeIdx, String routeName, String content){
        this.content = content;
        this.routeName = routeName;
        this.routeIdx = routeIdx;
    }

    public routeList() {

    }
}
