package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.model.Advertisement;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
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
        advertisementService.save(advertisement,file);
        return "redirect:/advertisement";
    }
    @GetMapping("/advertisement/delete")
    public String deleteBook(@RequestParam("id") int id) {
        advertisementService.deleteById(id);
        return ("redirect:/advertisement");


    }

}
