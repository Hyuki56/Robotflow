package mbc.aiseat.repository.hyuk.reserve;

import mbc.aiseat.entity.hyuk.reserve.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByLibraryId(Long libraryId);
    List<Seat> findByAvailableTrueAndLibraryId(Long libraryId);
}
