package com.java.myblogproject.controller;

import com.java.myblogproject.entity.User;
import com.java.myblogproject.service.UserService;
import com.java.myblogproject.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/showRegistrationForm")
    public String registerForm(Model model){

        model.addAttribute("crmUser",new CrmUser());
        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser crmUser, BindingResult bindingResult,Model model){

        if(bindingResult.hasErrors()){
            return "registration-form";
        }

        User existing=userService.findByUserName(crmUser.getUserName());
        if(existing!=null){
            model.addAttribute("crmUser",new CrmUser());
            model.addAttribute("registrationError","Username already exist!");

            return "registration-form";
        }

        userService.save(crmUser);

        return "registration-confirmation";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor editor=new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,editor);
    }
}
