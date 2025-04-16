package mbc.aiseat.entity.hyuk.reserve;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber; // 예: A1, B2 등

    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;
}
