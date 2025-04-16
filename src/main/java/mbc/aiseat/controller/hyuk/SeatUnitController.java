package mbc.aiseat.controller.hyuk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import mbc.aiseat.entity.hyuk.SeatUnit;
import mbc.aiseat.repository.hyuk.SeatUnitRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/class1")
public class SeatUnitController {
    private final SeatUnitRepository seatUnitRepository;

    @GetMapping("/seats")
    public Map<String, String> getClass1Seats() {
        List<SeatUnit> seats = seatUnitRepository.findByLibrary_Id(1L); // 또는 class1에 해당하는 이름

        System.out.println("좌석들:" + seats);

        // "a1": "used" or "null"
        return seats.stream()
                .collect(Collectors.toMap(
                        SeatUnit::getSeatNumber,
                        seat -> seat.isAvailable() ? "null" : "used"
                ));
    }
}
