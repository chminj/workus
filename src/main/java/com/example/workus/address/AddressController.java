package com.example.workus.address;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/address-book")
public class AddressController {

    @GetMapping("/list")
    public String addressBook() {
        return "addressbook/list";
    }
}
