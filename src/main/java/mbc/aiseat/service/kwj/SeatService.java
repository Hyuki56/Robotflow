package mbc.aiseat.service.kwj;

import lombok.extern.log4j.Log4j2;
import mbc.aiseat.entity.kwj.Seat;
import mbc.aiseat.repository.kwj.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getReservedSeats() {
        return seatRepository.findAll(); // 또는 seatRepository.findByReservedTrue();
    }

    public void reserveSeat(String username, String seatName) {

        if (seatRepository.findByseatName(seatName) != null) {
            throw new RuntimeException("이미 예약된 좌석입니다.");
        }

        Seat seat = Seat.builder()
                .seatName(seatName)
                .approved(false) // 초기에는 미승인 상태
                .reservedBy(username)
                .build();

        seatRepository.save(seat);
    }

    public void approveSeatByName(String seatName) {
        Seat seat = seatRepository.findBySeatName(seatName)
                .orElseThrow(() -> new RuntimeException("해당 좌석을 찾을 수 없습니다."));

        seat.setApproved(true);
        seatRepository.save(seat);
    }

    public Seat getReservedSeatByUser(String reservedBy) {
        Seat seat = seatRepository.findByReservedBy(reservedBy);
        return seat;
    }


    public boolean updateReservation(String seatName, String reservedBy) {
        Seat currentSeat = seatRepository.findByReservedBy(reservedBy);
        if (currentSeat != null) {
            currentSeat.setSeatName(seatName);
            seatRepository.save(currentSeat);  // 업데이트된 좌석 정보를 DB에 저장
            return true;
        }
        return false;
    }

    // 예약 취소 (삭제)
    public boolean cancelReservation(String username) {
        // 해당 사용자가 예약한 좌석을 찾기

        log.info("유저: " + username);

        Seat reservedSeat = seatRepository.findByReservedBy(username);

        log.info("좌석: " + reservedSeat);

        if (reservedSeat != null) {
            // 예약된 좌석을 삭제
            seatRepository.delete(reservedSeat);

            return true;  // 예약 삭제 성공
        }

        return false;  // 예약된 좌석을 찾지 못한 경우
    }
}
