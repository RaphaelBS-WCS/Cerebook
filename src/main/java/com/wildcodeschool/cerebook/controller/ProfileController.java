package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.CerebookUserFriends;
import com.wildcodeschool.cerebook.entity.Post;
import com.wildcodeschool.cerebook.repository.CerebookUserFriendsRepository;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import com.wildcodeschool.cerebook.repository.UserRepository;
import com.wildcodeschool.cerebook.service.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Controller
@RequestMapping("/profiles")
public class ProfileController extends AbstractCrudLongController<CerebookUser> {

    @Autowired
    private CerebookUserRepository cerebookUserRepository;

    @Autowired
    private CerebookUserFriendsRepository cerebookUserFriendsRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{id}/getById")
    public String getById(Model model, @PathVariable("id") Long id, Principal principal) {
        model.addAttribute("user", cerebookUserRepository.getById(id));
        model.addAttribute("userFields", getElementFields());
        // envoyer age
        if (cerebookUserRepository.findCerebookUserById(id).getBirthDate() != null) {
            model.addAttribute("date", calculateAge(cerebookUserRepository.findCerebookUserById(id).getBirthDate(), java.time.LocalDate.now()));
        }

        // Get the friend list:  retrieve the rows of friendship with isAccepted set to true
        CerebookUser currentCerebookUser = userRepository.getUserByUsername(principal.getName()).getCerebookUser();
        List<CerebookUserFriends> friendsList = cerebookUserFriendsRepository.findCerebookUserFriendsByOriginatedUserAndAccepted(currentCerebookUser);
        model.addAttribute("friends", friendsList);
        // Get the number of friends
        int countFriend = 0;
        for (CerebookUserFriends friend: friendsList) {
            countFriend++;
        }
        model.addAttribute("countFriend", countFriend);

        return getControllerRoute() + "/getById";
    }

    @Override
    protected JpaRepository<CerebookUser, Long> getRepository() {
        return cerebookUserRepository;
    }

    @Override
    protected String getControllerRoute() {
        return "profiles";
    }

    @Override
    protected String[] getElementFields() {
        return new String[]{"profilImage", "background", "superPowers", "genre", "bio", "membership", "user"};
    }

    @Override
    protected Class<CerebookUser> getElementClass() {
        return null;
    }

    @Override
    @PostMapping("/{id}/update")
    public String update(HttpServletRequest hsr, @PathVariable("id") String id, @ModelAttribute CerebookUser cerebookUser) {
       try {
            Part backgroundImagePart = hsr.getPart("backgroundImage");
            String fileName = Paths.get(backgroundImagePart.getSubmittedFileName()).getFileName().toString();
            cerebookUser.setBackground(fileName);
            String uploadDir = "src/main/resources/public/images/Profiles/" + id + "/background";
            FileUploadUtil.saveFile(uploadDir, fileName, backgroundImagePart);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        try {
            Part profileImagePart = hsr.getPart("profileImage");
            String fileName = Paths.get(profileImagePart.getSubmittedFileName()).getFileName().toString();
            cerebookUser.setProfilImage(fileName);
            String uploadDir = "src/main/resources/public/images/Profiles/" + id + "/profile";
            FileUploadUtil.saveFile(uploadDir, fileName, profileImagePart);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        preProcessElement(cerebookUser, hsr);
        getRepository().save(cerebookUser);
        return "redirect:/";
    }

    // creation de la methode pour calculer age
    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }


    @Override
    protected void preProcessElement(CerebookUser cerebookUser, HttpServletRequest _hsr) {
        if(cerebookUser.getProfilImage().isEmpty()) {
            cerebookUser.setProfilImage(
                    cerebookUserRepository.getById(cerebookUser.getId())
                            .getProfilImage());
        }
        if(cerebookUser.getBackground().isEmpty()) {
            cerebookUser.setBackground(
                    cerebookUserRepository.getById(cerebookUser.getId())
                            .getBackground());
        }
    }

    @Override
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        getRepository().deleteById(parseId(id));

        return "redirect:/logout";
    }
}

