package gilBeot.Course.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class toiletInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String city;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String openTime;
    @Column
    private String office;
    @Column
    private String X;
    @Column
    private String Y;

    public toiletInfo() {

    }
}
