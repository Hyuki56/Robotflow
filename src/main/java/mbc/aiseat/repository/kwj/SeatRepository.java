package mbc.aiseat.repository.kwj;

import mbc.aiseat.entity.kwj.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    boolean existsBySeatName(String seatName);
    Seat findByseatName(String seatName);
    List<Seat> findAll();
    Optional<Seat> findBySeatName(String seatName);
    Seat findByReservedBy(String reservedBy);

}
