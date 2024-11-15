package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/")
    public String printUsers() {
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String printUsers(@ModelAttribute("user") User user, ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User signedUser = (User) authentication.getPrincipal();
        model.addAttribute("allUsers", userService.listUsers());
        model.addAttribute("signedUser", signedUser);
        model.addAttribute("allRoles", roleService.rolesSet());
        model.addAttribute("roleSelect", new ArrayList<Long>());
        return "admin";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }


    @PostMapping("/admin/new")
    public String addUser(@ModelAttribute("user") User user) {
        Set<Role> roles = new HashSet<>();
        user.getRoles().forEach(role -> {
            roles.add(
                    roleService.findRoleByName(role.getRole())
            );
        });
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/admin";
    }


    @PostMapping("/admin/change")
    public String update(@RequestParam(required = false) List<Long> roleSelectedID, @ModelAttribute("user") User changedUser) {
        for (Long roleIdFromFront : roleSelectedID) {
            changedUser.setRoles(roleService.findRole(roleIdFromFront));
        }
        userService.update(changedUser);
        return "redirect:/admin";
    }

}
