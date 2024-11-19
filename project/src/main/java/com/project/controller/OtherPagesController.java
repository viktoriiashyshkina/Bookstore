package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherPagesController {



    @GetMapping("/category")
    public String categoryPage() {
        return "category";
    }


    @GetMapping("/payment")
    public String paymentPage() {
        return "payment";
    }

    @GetMapping("/purchase-history")
    public String purchaseHistoryPage() {
        return "purchase-history";
    }

    @GetMapping("/register-admin")
    public String registerAdminPage() {
        return "register-admin";
    }


}
