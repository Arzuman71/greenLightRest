package am.greenlight.greenlight.service;


import am.greenlight.greenlight.model.Announcement;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.AnnouncementRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepo announcementRepo;

    public Announcement save(Announcement announcement) {
       return announcementRepo.save(announcement);
    }

    public Page<Announcement> findAll(PageRequest pageRequest) {
        return announcementRepo.findAll(pageRequest);
    }
 public List<Announcement> findAll() {
        return announcementRepo.findAll();
    }

    public Announcement getOne(long id) {
        return announcementRepo.getOne(id);
    }

    public List<Announcement> findAllByUserIdAndState(long id, Status state) {
        return announcementRepo.findAllByUserIdAndStatus(id, state);
    }


}
