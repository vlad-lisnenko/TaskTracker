package com.lisnenko.tasktracker.controller;

import com.lisnenko.tasktracker.entity.Employee;
import com.lisnenko.tasktracker.entity.User;
import com.lisnenko.tasktracker.service.EmployeeService;
import com.lisnenko.tasktracker.service.UserService;
import com.lisnenko.tasktracker.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private Logger logger = Logger.getLogger(getClass().getName());

    UserService userService;

    EmployeeService employeeService;

    public RegistrationController(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    @GetMapping("/showRegistrationForm")
    public String showRegistrationForm(Model model) {

        model.addAttribute("webUser", new WebUser());

        return "register/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("webUser") WebUser theWebUser,
            BindingResult theBindingResult,
            HttpSession session, Model theModel) {

        String userName = theWebUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()){
            return "register/registration-form";
        }

        // check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
            theModel.addAttribute("webUser", new WebUser());
            theModel.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return "register/registration-form";
        }

        // create user account and store in the databse
        userService.save(theWebUser);
        employeeService.save(new Employee(0, theWebUser.getFirstName(), theWebUser.getLastName(), theWebUser.getEmail()));

        logger.info("Successfully created user: " + userName);

        // place user in the web http session for later use
        session.setAttribute("user", theWebUser);
        theModel.addAttribute("registrationSuccess", true);
        return "index";
    }
}
