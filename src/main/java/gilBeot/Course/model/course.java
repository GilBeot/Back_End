package gilBeot.Course.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class course {
    @Id
    private String crsIdx;
    @Column
    private String name;
    @Column
    private String level;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(columnDefinition = "TEXT")
    private String summary;
    @Column(columnDefinition = "TEXT")
    private String tourInfo;
    @Column(columnDefinition = "TEXT")
    private String travelInfo;
    @Column
    private String address;
    @Column
    private String X;
    @Column
    private String Y;
    @Column
    private int cnt;
    @Column
    private String gpxLink;

    @OneToMany(cascade = CascadeType.ALL)
    private List<coursePoint> courseRoot;

    public void update (String X, String Y){
        this.X = X;
        this.Y = Y;
    }
}
