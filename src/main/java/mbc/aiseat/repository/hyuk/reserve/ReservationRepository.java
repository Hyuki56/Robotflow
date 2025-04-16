package mbc.aiseat.repository.hyuk.reserve;

import mbc.aiseat.entity.hyuk.reserve.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findBySeatIdAndEndTimeAfter(Long seatId, LocalDateTime now);

    List<Reservation> findByUserIdAndEndTimeAfter(Long userId, LocalDateTime now);

    List<Reservation> findBySeatId(Long seatId); // findBySeatId(Long seatId) > Spring Data JPA 메서드 쿼리 문법 > 자동으로 구현됨

    List<Reservation> findByUserId(Long userId); //
}
