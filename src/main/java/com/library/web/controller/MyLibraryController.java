package com.library.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyLibraryController {

    @GetMapping("/mylibrary")
    public String showMyLibraryPage() {
        return "mylibrary";  // → /WEB-INF/views/mylibrary.jsp 를 보여줌
    }
}
