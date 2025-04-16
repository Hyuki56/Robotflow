package mbc.aiseat.controller.hyuk.reserve;

import lombok.RequiredArgsConstructor;
import mbc.aiseat.entity.hyuk.reserve.Reservation;
import mbc.aiseat.entity.hyuk.reserve.Seat;
import mbc.aiseat.service.hyuk.reserve.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hyuk/reservation")
public class HyukReservationController {

    private final ReservationService reservationService;

    // 좌석 예약 요청
    @PostMapping("/reserve")
    public String reserveSeat(
            @RequestParam Long userId,
            @RequestParam Long seatId,
            @RequestParam String startTime,
            @RequestParam String endTime) {

        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        return reservationService.reserveSeat(userId, seatId, start, end);
    }

    // 예약 취소
    @DeleteMapping("/cancel/{reservationId}")
    public String cancelReservation(@PathVariable Long reservationId) {
        return reservationService.cancelReservation(reservationId);
    }

    // 사용자 예약 조회
    @GetMapping("/user/{userId}")
    public List<Reservation> getUserReservations(@PathVariable Long userId) {
        return reservationService.getUserReservations(userId);
    }

    // 도서관 전체 좌석 조회
    @GetMapping("/library/{libraryId}/seats")
    public List<Seat> getSeatsInLibrary(@PathVariable Long libraryId) {
        return reservationService.getSeatsInLibrary(libraryId);
    }

    // 도서관 사용 가능한 좌석 조회
    @GetMapping("/library/{libraryId}/available-seats")
    public List<Seat> getAvailableSeats(@PathVariable Long libraryId) {
        return reservationService.getAvailableSeats(libraryId);
    }
}
