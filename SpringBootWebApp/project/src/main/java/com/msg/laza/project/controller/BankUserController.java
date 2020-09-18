package com.msg.laza.project.controller;


import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.BankUser;
import com.msg.laza.project.service.BankUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class BankUserController {

    @Autowired
    private BankUserService bankUserService;

    @GetMapping("/api/users/{id}")
    public BankUser getUser(@PathVariable String id) throws CustomException.InvalidIdException {
        return bankUserService.getUserById(id);
    }

    @GetMapping("/api/users")
    public List<BankUser> getUsers(){
        return bankUserService.getAllUsers();
    }

    @PostMapping("/api/users")
    public BankUser createUser(@RequestBody BankUser b) throws CustomException.InvalidNumberOfArgumentsException {
        bankUserService.addUser(b.getName(),b.getSurname(),b.getCity(),b.getBorn());
        return bankUserService.getLastInsertedUser();
    }

    @PutMapping("/api/users")
    public void updateUser(@RequestParam String id,@RequestParam(required = false) String surname,@RequestParam(required = false) String city)
            throws CustomException.InvalidNumberOfArgumentsException, CustomException.InvalidIdException {
        bankUserService.updateUser(id,surname,city);
    }

    @DeleteMapping("/api/users")
    public void deleteUser(@RequestParam String id) throws CustomException.InvalidIdException {
        bankUserService.deleteUser(id);
    }
}
