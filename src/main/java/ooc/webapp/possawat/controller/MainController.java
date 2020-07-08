package ooc.webapp.possawat.controller;


import java.security.Principal;

import lombok.var;
import ooc.webapp.possawat.model.AppUser;
import ooc.webapp.possawat.service.IAdminService;
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

    @Autowired
    IAdminService adminService;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcomePage";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String addUserForm(Model model){
        model.addAttribute("userToAdd", new AppUser());
        String confirmPassword = new String();
        model.addAttribute("confirmPassword", confirmPassword);

        return "registration";
    }

    @PostMapping(value = "/registration")
    public String addUserSubmit(@ModelAttribute AppUser user){
        adminService.addNewUser(user);
        return "redirect:admin";
    }

    /*
    ! Still, the remove confirmation cannot take the variable across the page.
     */
    @PostMapping(value = "/remove")
    public String remove(@ModelAttribute(value="userRow") AppUser user, Model model){
        model.addAttribute("toRemove", user);
        return "removeConfirm";
    }

    @RequestMapping(value = "/removeConfirm", method = RequestMethod.GET)
    public String confirmRemove(Model model){
        AppUser chosen = (AppUser) model.getAttribute("toRemove");
        model.addAttribute("removing", chosen);
        return "removeConfirm";
    }
    @PostMapping(value = "/removeConfirm")
    public String confirmRemoveClicked(@ModelAttribute(value="removing") AppUser user){
        adminService.removeUser(user);
        return "admin";
    }

    /*
    ! Fix above methods.
     */

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

    @RequestMapping(value = { "/", "/login"}, method = RequestMethod.GET)
    public String loginPage(Model model, Principal principal) {
        if (principal != null) {
            User loggedinUser = (User) ((Authentication) principal).getPrincipal();
            if(loggedinUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) return "redirect:admin";
            else return "redirect:userInfo";
        }
        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // After user login successfully.
        String userName = principal.getName();

        System.out.println("AppUser Name: " + userName);

        User loggedinUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loggedinUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfoPage";
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
