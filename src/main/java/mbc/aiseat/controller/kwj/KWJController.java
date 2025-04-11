package mbc.aiseat.controller.kwj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KWJController {

    @GetMapping("/KWJ")
    public String test() {
        return "tesKWJ/t";
    }

}
