package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.PageRequestDto;
import am.greenlight.greenlight.model.Announcement;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Role;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.AnnouncementService;
import am.greenlight.greenlight.service.MainService;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final AnnouncementService announcementService;


    //todo  AnnouncementResponseDto
    @GetMapping("/")
    public ResponseEntity<PageRequestDto> mainPage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size,
                Sort.by(Sort.Order.desc("createdDate")));
        Page<Announcement> allAnnouncement = announcementService.findAll(pageRequest);
        PageRequestDto pageDto = new PageRequestDto();
        int totalPages = allAnnouncement.getTotalPages();
        if (totalPages > 0) {

            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            pageDto.setPageNumbers(pageNumbers);
        }
        pageDto.setAllAnnouncement(allAnnouncement);
        return ResponseEntity.ok(pageDto);
    }


        @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)

        public @ResponseBody
        byte[] getImage (@RequestParam("name") String imageName) throws IOException {

            return mainService.getImage(imageName);
        }

    }
