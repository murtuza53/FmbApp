package org.fmb.controller;

import java.util.List;
import org.fmb.entity.Users;
import org.fmb.repo.ItsMasterRepository;
import org.fmb.repo.UserRepository;
import org.fmb.util.JsfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController extends AbstractController<Users> {

    private UserRepository facade;

    @Autowired
    private ItsMasterRepository itsRepo;
    
    private List<Users> userList;
    
    private String password;
    
    private String confirmPassword;
    
    @Autowired
    public UsersController(UserRepository repo) {
        // Inform the Abstract parent controller of the concrete Users Entity
        super(Users.class, repo);
        this.facade = repo;
    }

    public List<Users> getUserList(){
        if(userList==null){
            userList = facade.findUsersSortByActive();
            for(Users u: userList){
                u.setFullName(findFullName(u.getUserNo()));
            }
        }
        return userList;
    }
    
    public String findFullName(Integer itsNo){
        return itsRepo.findById(itsNo).get().getFullName();
    }
    
    @Override
    public void save(){
        //getSelected().setPassword(bCryptPasswordEncoder.encode(password));
        facade.save(getSelected());
        JsfUtil.addSuccessMessage("Success", "Password saved sucessfuly");
    }
    
    public void saveActive(Users user){
        setSelected(user);
        facade.save(getSelected());
        String msg = user.getFullName() + " is now Inactive";
        if(user.getActive()){
            msg = user.getFullName() + " is now Active";
        }
        userList=null;
        JsfUtil.addSuccessMessage("Success", msg);
    }
    
    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "login";
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        return "index.xhtml";
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
