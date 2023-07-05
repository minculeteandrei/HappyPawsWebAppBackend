package com.happypaws.restcontrollers;

import com.happypaws.domain.ContactMessage;
import com.happypaws.services.EmailService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/contact")
public class EmailRestController {
    private final EmailService emailService;

    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/send")
    public ResponseEntity<?> postContactEmail(@RequestBody ContactMessage messageData) {
        JSONObject result = new JSONObject();
        try {
            this.emailService.sendSimpleMessage(
                    this.emailService.getHostAddress(),
                    "contact mail",
                    "from :" + messageData.getEmail() + " \nname: " + messageData.getNumeComplet() +
                            " \nphone number: " + messageData.getTelefon() + " \nmessage: " +
                            messageData.getMessage());

            result.put("success:", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        } catch (Exception e) {
        e.printStackTrace();
        result.put("error", "Internal server error");
        return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
