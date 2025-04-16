package mbc.aiseat.service.hyuk.reserve;

import lombok.RequiredArgsConstructor;
import mbc.aiseat.entity.hyuk.reserve.Reservation;
import mbc.aiseat.entity.hyuk.reserve.Seat;
import mbc.aiseat.entity.hyuk.reserve.User;
import mbc.aiseat.repository.hyuk.reserve.ReservationRepository;
import mbc.aiseat.repository.hyuk.reserve.SeatRepository;
import mbc.aiseat.repository.hyuk.reserve.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    // 좌석 예약
    public String reserveSeat(Long userId, Long seatId, LocalDateTime startTime, LocalDateTime endTime) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Seat> seatOpt = seatRepository.findById(seatId);

        if (userOpt.isEmpty() || seatOpt.isEmpty()) {
            return "사용자 또는 좌석이 존재하지 않습니다.";
        }

        // 이미 예약된 좌석인지 확인
        List<Reservation> existing = reservationRepository.findBySeatIdAndEndTimeAfter(seatId, LocalDateTime.now());
        if (!existing.isEmpty()) {
            return "해당 좌석은 이미 예약되어 있습니다.";
        }

        Reservation reservation = Reservation.builder()
                .user(userOpt.get())
                .seat(seatOpt.get())
                .startTime(startTime)
                .endTime(endTime)
                .build();

        seatOpt.get().setAvailable(false); // 예약 완료 후 좌석 비활성화
        reservationRepository.save(reservation);
        return "예약이 완료되었습니다.";
    }

    // 예약 취소
    public String cancelReservation(Long reservationId) {
        Optional<Reservation> resOpt = reservationRepository.findById(reservationId);
        if (resOpt.isEmpty()) return "예약 정보를 찾을 수 없습니다.";

        Reservation reservation = resOpt.get();
        Seat seat = reservation.getSeat();
        seat.setAvailable(true); // 취소 후 다시 사용 가능

        reservationRepository.delete(reservation);
        return "예약이 취소되었습니다.";
    }

    // 유저의 현재 예약 조회
    public List<Reservation> getUserReservations(Long userId) {
        return reservationRepository.findByUserIdAndEndTimeAfter(userId, LocalDateTime.now());
    }

    // 도서관의 전체 좌석 조회
    public List<Seat> getSeatsInLibrary(Long libraryId) {
        return seatRepository.findByLibraryId(libraryId);
    }

    // 도서관의 사용 가능한 좌석만 조회
    public List<Seat> getAvailableSeats(Long libraryId) {
        return seatRepository.findByAvailableTrueAndLibraryId(libraryId);
    }
}
