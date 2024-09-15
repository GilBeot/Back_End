package gilBeot.Course.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class toilet {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<toiletInfo> seoul;

    public toilet() {

    }
}
