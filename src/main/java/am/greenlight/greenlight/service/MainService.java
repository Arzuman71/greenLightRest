package am.greenlight.greenlight.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
@RequiredArgsConstructor
public class MainService {

    @Value("${file.upload.userAvatar.dir}")
    private String userAvatarDir;
    @Value("${file.upload.carPicture.dir}")
    private String carPictureDir;
    @Value("${file.upload.AdvertisementPic.dir}")
    private String AdvertisementPicDir;


    public byte[] getUserAvatarOrNull(String imageName) {
        return getBytes(imageName, userAvatarDir);
    }

    public byte[] getCarImageOrNull(String imageName) {
        return getBytes(imageName, carPictureDir);
    }

    public byte[] getAdvertisementImageOrNull(String imageName) {

        return getBytes(imageName, AdvertisementPicDir);
    }

    private byte[] getBytes(String imageName, String uploadDir) {
        if (imageName.equals("str")) {
            imageName = "17.png";
        }
        try {
            InputStream in = new FileInputStream(uploadDir + File.separator + imageName);

            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
