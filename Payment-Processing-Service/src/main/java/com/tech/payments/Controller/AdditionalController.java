package com.tech.payments.Controller;

import com.tech.payments.Service.ReconService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AdditionalController {

    private final ReconService reconService;

    @PostMapping("/recon")
    public String triggerRecon() {




        return "Recon triggered";
    }
}
