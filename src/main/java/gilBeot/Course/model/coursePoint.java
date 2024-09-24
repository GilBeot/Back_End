package gilBeot.Course.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class coursePoint {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column
    private String X;
    @Column
    private String Y;

    public coursePoint() {

    }
}
