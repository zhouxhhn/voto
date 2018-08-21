package bjl.core.upload;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by pengyi on 2016/4/11.
 */
public interface IFileUploadService {
    UploadResult upload(MultipartFile[] files) throws IOException;

    boolean deleteFile(File file);

    void delete(String roomNo);

    JSONObject uploadHead(MultipartFile file, String username);

    JSONObject uploadChat(MultipartFile file, String username, Integer roomType);

    String uploadBetImg(String fileName, BufferedImage image);

    void uploadLottery(MultipartFile lotteryImg, MultipartFile roadImg,MultipartFile sceneImg,String[] strings);

    JSONObject uploadNotice(MultipartFile noticeImg);

    JSONObject validateCode(JSONObject jsonObject);

    void deleteCode(String flag);
}
