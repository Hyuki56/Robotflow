package mbc.aiseat.controller.kwj;

import mbc.aiseat.dto.kwj.SeatDto;
import mbc.aiseat.entity.kwj.Seat;
import mbc.aiseat.service.kwj.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/reserve")
    public List<Seat> getReservedSeats() {
        return seatService.getReservedSeats();
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveSeat(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestBody SeatDto seatDto) {
        try {
            String username = userDetails.getUsername();  // 로그인된 사용자 이름
            String seatName = seatDto.getSeatName();
            seatService.reserveSeat(username,seatName);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }



}
