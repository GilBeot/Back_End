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
    private List<toiletInfo> toiletInfoList;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> seoul;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> busan;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> daegu;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> incheon;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> gwangju;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> daejeon;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> ulsan;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> sejong;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> gyeonggi;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> gangwon;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> chungbuk;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> chungnam;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> jeonbuk;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> jeonnam;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> gyeongbuk;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> gyeongnam;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<toiletInfo> jeju;

    public toilet() {

    }
}
