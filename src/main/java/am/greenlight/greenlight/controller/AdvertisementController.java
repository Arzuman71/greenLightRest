package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.model.Advertisement;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    @GetMapping("/advertisement")
    public String advertisement(ModelMap modelMap) {
        List<Advertisement> advertisement = advertisementService.findAll();
        modelMap.addAttribute("advertisement", advertisement);
        return "advertisement";
    }


    @PostMapping("/advertisement")
    public String offerPage(Model model, @ModelAttribute Advertisement advertisement, @AuthenticationPrincipal CurrentUser currentUser, @RequestParam("image") MultipartFile file) {
        model.addAttribute("currentUser", currentUser.getUser());
        advertisement.setUser(currentUser.getUser());
        advertisement.setCreateDate(LocalDateTime.now());
        advertisementService.saveAdvertisement(advertisement,file);
        return "redirect:/advertisement";
    }
    @GetMapping("/advertisement/delete")
    public String deleteBook(@RequestParam("id") int id) {
        advertisementService.deleteById(id);
        return ("redirect:/advertisement");


    }

}
