package ooc.webapp.possawat.controller;


import java.security.Principal;

import lombok.var;
import ooc.webapp.possawat.model.AppUser;
import ooc.webapp.possawat.service.IAdminService;
import ooc.webapp.possawat.service.UserDetailsServiceImpl;
import ooc.webapp.possawat.utilities.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {

    /*
     Just good services
     */

    @Autowired
    IAdminService adminService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private Boolean success=true;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This website is made by Parmcoder!");
        return "welcomePage";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String addUserForm(Model model){
        model.addAttribute("userToAdd", new AppUser());
        String confirmPassword = new String();
        model.addAttribute("confirmPassword", confirmPassword);

        return "registration";
    }

    @RequestMapping(value = "/registration/failed", method = RequestMethod.GET)
    public String addUserFormFailed(Model model){
        addUserForm(model);
        if(!success){
            model.addAttribute("errorMsg", "Try again! Your username is already taken.");
        }
        success = true;
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String addUserSubmit(@ModelAttribute AppUser user, Model model){
        success = adminService.addNewAdmin(user);
        if(!success){
            return "redirect:registration/failed";
        }
        return "redirect:admin";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        //only admin can log in here

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        var users = adminService.findAll();
        model.addAttribute("appUserList", users);
        model.addAttribute("userRow", new AppUser());

        return "adminPage";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String remove(@ModelAttribute("userRow") AppUser user, Model model){
        return "removeConfirm";
    }

    @PostMapping(value = "/remove")
    public String confirmRemoveClicked(@ModelAttribute("userRow") AppUser user, Model model){
        success = adminService.removeUser(user);

        if(!success){
            model.addAttribute("errorMsg", "Try again! That user is not existed.");
        }

        return "redirect:admin";
    }

    @RequestMapping(value = { "/", "/login"}, method = RequestMethod.GET)
    public String loginPage(Model model, Principal principal) {
        model.addAttribute("title", "Welcome");

        if (principal != null) {
            User loggedinUser = (User) ((Authentication) principal).getPrincipal();
            if(loggedinUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) return "redirect:admin";
            else return "redirect:userInfo";
        }
        return "loginPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // After user login successfully.
        String userName = principal.getName();

        System.out.println("AppUser Name: " + userName);

        User loggedinUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loggedinUser);
        model.addAttribute("userInfo", userInfo);

        AppUser editUser = new AppUser();
        model.addAttribute("newUserData", editUser);

        AppUser currentUser = adminService.getCurrentInfo(userName);
        model.addAttribute("currentUser", currentUser);

        return "userInfoPage";
    }

    @RequestMapping(value = "/userInfo/failed", method = RequestMethod.GET)
    public String userInfoFailed(Model model, Principal principal) {
        // After user login successfully.
        userInfo(model,principal);
        if(!success){
            model.addAttribute("errorMsg", "Try again! Your username is not available.");
        }
        success = true;
        return "userInfoPage";
    }

    @PostMapping(value = "/userInfo")
    public String updateUserInfo(@ModelAttribute("newUserData") AppUser user, Principal principal, Model model) {
        success = adminService.updateUserInfo(principal.getName(), user);
        if(user.getUserName().isEmpty()){
            return "redirect:userInfo";
        }
        if(!success){
            return "redirect:userInfo/failed";
        }
        return "redirect:logout";
    }

    @RequestMapping(value = {"/403"}, method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {

            User loggedinUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loggedinUser);
            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "welcomePage";
    }

}
