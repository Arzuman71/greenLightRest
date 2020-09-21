package am.greenlight.greenlight.service;


import am.greenlight.greenlight.model.Announcement;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public void save(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    public List<Announcement> findAllByUserId(long id) {
        return announcementRepository.findAllByUserId(id);
    }

    public Page<Announcement> findAll(PageRequest pageRequest) {
        return announcementRepository.findAll(pageRequest);
    }
 public List<Announcement> findAll() {
        return announcementRepository.findAll();
    }

    public Announcement getOne(long id) {
        return announcementRepository.getOne(id);
    }

    public List<Announcement> findAllByUserIdAndState(long id, Status state) {
        return announcementRepository.findAllByUserIdAndStatus(id, state);
    }


}
