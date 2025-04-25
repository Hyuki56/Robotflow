//package mbc.aiseat.repository.hyuk;
//
//import mbc.aiseat.entity.hyuk.SeatUnit;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface SeatUnitRepository extends JpaRepository<SeatUnit, Long> {
//    List<SeatUnit> findByLibrary_Id(Long libraryId);  // ✅ library_id로 조회
//}

package mbc.aiseat.repository.hyuk;

import mbc.aiseat.entity.hyuk.SeatUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatUnitRepository extends JpaRepository<SeatUnit, Long> {
    List<SeatUnit> findByLibraryId(Long libraryId);
    SeatUnit findByLibraryIdAndSeatNumber(Long libraryId, String seatNumber);
}
