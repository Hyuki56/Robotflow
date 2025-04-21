//package mbc.aiseat.entity.hyuk;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//public class SeatUnit {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "library_id")
//    private Library library;
//
//    @Column(length = 10)
//    private String seatNumber;
//
//    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
//    private boolean available = true;
//
//    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Reservation> reservations;
//
//    public String getSeatNumber() {
//        return this.seatNumber;
//    }
//
//    public boolean isAvailable() {
//        return this.available;
//    }
//}

package mbc.aiseat.entity.hyuk;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seat_unit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "available")
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;


}
