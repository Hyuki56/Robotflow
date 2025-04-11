package mbc.aiseat.entity.kwj;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatName;

    private LocalDateTime reservedAt = LocalDateTime.now();

    private boolean reserved; // 예약취소시 이 값만 변경하면됨. 데이터삭제보다 상태변경방식으로

    private boolean approved; // 관리자의 승인여부

    private String reservedBy;

    // 기본 생성자 필수!
    public Seat() {}

    public Seat(String seatName, boolean reserved, boolean approved, String reservedBy) {
        this.seatName = seatName;
        this.reservedBy = reservedBy;
        this.reserved = reserved;
        this.approved = approved;
    }

    public static Seat createSeat(String seatName, boolean reserved, boolean approved, String reservedBy) {
        Seat seat = new Seat(seatName, reserved, approved, reservedBy);
        seat.setSeatName(seatName);
        seat.setReserved(reserved);
        seat.setApproved(approved);
        seat.setReservedBy(reservedBy);
        return seat;
    }

}
