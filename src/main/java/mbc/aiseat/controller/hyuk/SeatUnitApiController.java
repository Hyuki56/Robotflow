package mbc.aiseat.controller.hyuk;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mbc.aiseat.entity.hyuk.SeatUnit;
import mbc.aiseat.repository.hyuk.SeatUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Log4j2
@RequiredArgsConstructor
public class SeatUnitApiController {

    private final SeatUnitRepository seatUnitRepository;

    @RestController
    @RequestMapping("/api")
    public class SeatController {

        private final SeatUnitRepository seatUnitRepository;

        @Autowired
        public SeatController(SeatUnitRepository seatUnitRepository) {
            this.seatUnitRepository = seatUnitRepository;
        }

        @GetMapping("/class{num}/seats")
        public Map<String, Map<String, String>> getSeats(@PathVariable("num") Long num) {
            List<SeatUnit> seats = seatUnitRepository.findByLibraryId(num);
            Map<String, String> seatStatus = new HashMap<>();

            for (SeatUnit seat : seats) {
                // available = true → 비어 있음 → "null"
                // available = false → 사용 중 → "used"
                seatStatus.put(seat.getSeatNumber(), seat.getAvailable() ? "null" : "used");
            }

            Map<String, Map<String, String>> result = new HashMap<>();
            result.put("class" + num, seatStatus);

            return result;
        }
    }




    @PostMapping("/class{num}/reserve") // 예약 post
    public ResponseEntity<String> reserveSeat(
            @PathVariable("num") Long classNum,
            @RequestParam("seatNumber") String seatNumber) {

        SeatUnit seat = seatUnitRepository.findByLibraryIdAndSeatNumber(classNum, seatNumber);
        if (seat == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seat not found");
        }

        if (!seat.getAvailable()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Seat already in use");
        }

        seat.setAvailable(false); // 예약 = 사용 중 상태로 변경
        seatUnitRepository.save(seat);
        return ResponseEntity.ok("Seat reserved");
    }

}
