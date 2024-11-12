package com.example.workus.address;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddressController {

    @GetMapping("/address-book")
    public String addressBook() {
        return "addressbook/address-book";
    }
}
