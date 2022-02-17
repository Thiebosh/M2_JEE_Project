package com.videoGamesWeb.vgweb.controllers;

import com.videoGamesWeb.vgcore.entity.User;
import com.videoGamesWeb.vgcore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Objects;

@Controller
@RequestMapping(value = "/user")
public class UserViewController {

    private final static Logger logger = LoggerFactory.getLogger(UserViewController.class);

    private final static String CREATE_PAGE = "user_create";
    private final static String CONNECT_PAGE = "user_connect";
    private final static String PROFILE_PAGE = "user_profile";
    private final static String UPDATE_PAGE = "user_update";

    private final static String SESSION_USER_ID = "userID";

    private final UserService userService;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

    public UserViewController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/create")
    public String getCreate(){
        String logger_anchor = "VC-/user/create:get";
        return CREATE_PAGE;
    }

    @PostMapping("/create")
    public String PostCreate(Model model,
                             @RequestParam String name,
                             @RequestParam String password,
                             @RequestParam String confirm_password,
                             @RequestParam(required=false) String mail,
                             @RequestParam(required=false) String address){
        String logger_anchor = "VC-/user/create:post";

        if (name.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
            model.addAttribute("error_msg", "Champ(s) vide(s)");
            return CREATE_PAGE;
        }
        if (!Objects.equals(password, confirm_password)) {
            model.addAttribute("error_msg", "Mots de passes différents");
            return CREATE_PAGE;
        }
        if (this.userService.existByName(name)) {
            model.addAttribute("error_msg", "Nom déjà utilisé");
            return CREATE_PAGE;
        }

        logger.info("{} - start creating instance", logger_anchor);
        User user = new User();
        user.setName(name);
        user.setPassword(this.bCryptPasswordEncoder.encode(password));
        user.setMail(mail != null ? mail : "");
        user.setAddress(address != null ? address : "");
        user.setNbrComments(0);
        this.userService.save(user);
        logger.info("{} - created user", logger_anchor);

        return "redirect:/user/connect";
    }

    @GetMapping("/connect")
    public String getConnect(){
        String logger_anchor = "VC-/user/connect:get";

        return CONNECT_PAGE;
    }

    @PostMapping("/connect")
    public String PostConnect(Model model,
                              @RequestParam String name,
                              @RequestParam String password,
                              HttpSession session) {
        String logger_anchor = "VC-/user/connect:post";

        if (name.isEmpty() || password.isEmpty()) {
            model.addAttribute("error_msg", "Champ(s) vide(s)");
            return CONNECT_PAGE;
        }
        User user = this.userService.getByName(name);
        if (user == null || !this.bCryptPasswordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error_msg", "Informations invalides");
            return CONNECT_PAGE;
        }

        session.setAttribute(SESSION_USER_ID, user.getId());

        return "redirect:/user/profile";
    }

    @GetMapping("/disconnect")
    public String getDisconnect(HttpServletRequest request){
        String logger_anchor = "VC-/user/connect:get";

        request.getSession().invalidate();

        return "redirect:/user/connect";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, HttpServletRequest request){
        String logger_anchor = "VC-/user/profile:get";

        long userId = (long) request.getSession().getAttribute(SESSION_USER_ID);

        model.addAttribute("user", this.userService.getById(userId));
        return PROFILE_PAGE;
    }

    @GetMapping("/update")
    public String getUpdate(Model model, HttpServletRequest request){
        String logger_anchor = "VC-/user/update:get";

        long userId = (long) request.getSession().getAttribute(SESSION_USER_ID);

        return UPDATE_PAGE;
    }

    @PostMapping("/update")
    public String postUpdate(Model model, HttpServletRequest request) {
        String logger_anchor = "VC-/user/update:post";

        long userId = (long) request.getSession().getAttribute(SESSION_USER_ID);
        //process post params
        if (false) return UPDATE_PAGE;
        return "redirect:/user/profile/1";
    }

    @GetMapping("/delete")
    public String getDelete(HttpServletRequest request) {
        String logger_anchor = "VC-/user/delete:get";

        long userId = (long) request.getSession().getAttribute(SESSION_USER_ID);
        if (this.userService.existById(userId)) {
            logger.info("{} - delete user {}", logger_anchor, userId);
            this.userService.deleteById(userId);
        }

        return "redirect:/user/disconnect";
    }
}
