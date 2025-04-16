package mbc.aiseat.controller.hyuk;

import mbc.aiseat.entity.hyuk.reserve.Reservation;
import mbc.aiseat.repository.hyuk.reserve.ReservationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations") // 모든 경로에 /reservations prefix 붙음
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

//    @PostMapping("/reserve")
//    public Reservation createReservation(@RequestBody Reservation reservation) {
//        return reservationRepository.save(reservation);
//    }

    @GetMapping("") // get /reservations 모든 예약 데이터를 리턴해주는 API
    public List<Reservation> getAllReservations() { // 반환값은 Resrvation 객체 리스트
        return reservationRepository.findAll(); // JPA가 자동으로 테이블 전체 조회하는 메서드 ( SELECT * FROM  reservation )
    }

    @GetMapping("/seat/{seatId}") // /reservation/seat/{seatId} > 경로의 GET 처리
    public List<Reservation> getReservationsBySeat(@PathVariable Long seatId) { // @PathVariable Long seatID > URL의 {seatID} 값을 long 타입 변수로받음
        return reservationRepository.findBySeatId(seatId); // findBySeatId(seatID) > seatId를 기준으로 DB에서 Reservation 목록 조회
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUser(@PathVariable Long userId) {
        return reservationRepository.findByUserId(userId);
    }

}
