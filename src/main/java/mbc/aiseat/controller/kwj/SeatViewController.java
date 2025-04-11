package mbc.aiseat.controller.kwj;

import mbc.aiseat.entity.kwj.Seat;
import mbc.aiseat.service.kwj.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SeatViewController {

    private final SeatService seatService;

    @GetMapping("/info")
    public String info(Model model) {

        List<Seat> seats = seatService.getReservedSeats(); // 전체 좌석 정보
        model.addAttribute("seats", seats);
        return "KWJ/info";
    }

    @PostMapping("/info")
    public ResponseEntity<?> approveBySeatName(@RequestParam String seatName) {
        try {
            seatService.approveSeatByName(seatName);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

}
