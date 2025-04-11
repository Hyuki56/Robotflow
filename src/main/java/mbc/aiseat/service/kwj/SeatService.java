package mbc.aiseat.service.kwj;

import mbc.aiseat.entity.kwj.Seat;
import mbc.aiseat.repository.kwj.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll(); // 또는 seatRepository.findByReservedTrue();
    }

    public List<Seat> getReservedSeats() {
        return seatRepository.findByReservedTrue(); // 또는 seatRepository.findByReservedTrue();
    }

    public void reserveSeat(String username, String seatName) {

        if (seatRepository.findByseatName(seatName) != null) {
            throw new RuntimeException("이미 예약된 좌석입니다.");
        }

        Seat seat = Seat.createSeat(seatName,true, false, username);

        seatRepository.save(seat);
    }

    public void approveSeatByName(String seatName) {
        Seat seat = seatRepository.findBySeatName(seatName)
                .orElseThrow(() -> new RuntimeException("해당 좌석을 찾을 수 없습니다."));

        seat.setApproved(true);
        seatRepository.save(seat);
    }

}
