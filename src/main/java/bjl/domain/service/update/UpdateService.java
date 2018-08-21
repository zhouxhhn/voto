package bjl.domain.service.update;

import bjl.application.update.command.EditUpdateCommand;
import bjl.core.upload.FileUploadConfig;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.update.IUpdateRepository;
import bjl.domain.model.update.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2017/12/23.
 */
@Service("updateService")
public class UpdateService implements IUpdateService{

    @Autowired
    private IUpdateRepository<Update, String> updateRepository;
    @Autowired
    private FileUploadConfig fileUploadConfig;

    /**
     * 获取资源包信息
     * @return
     */
    @Override
    public Update get() {
        List<Update> list = updateRepository.findAll();
        return list.size() == 0 ? null : list.get(0);
    }

    /**
     * 更新资源包
     * @param file
     * @return
     */
    @Override
    public boolean updateFile(MultipartFile file, EditUpdateCommand command) {

        try {
            String url = null;
            if(file.getSize() > 0){
                File folder = new File(fileUploadConfig.getPath(), fileUploadConfig.getResourcePackage());//创建工作目录
                folder.mkdirs();

                //返回客户端的文件系统中的原始文件名
                String fileName = file.getOriginalFilename();
                //获取文件后缀名
                FileOutputStream out = new FileOutputStream(folder + "/" + fileName);
                // 写入文件
                out.write(file.getBytes());
                out.flush();
                out.close();

                //资源包链接
                url = fileUploadConfig.getDomainName() + fileUploadConfig.getResourcePackage()+fileName;
            }

            //更新资源包信息
            List<Update> list = updateRepository.findAll();

            Update update;
            if (list.size() >0) {
                update = list.get(0);
                if(!CoreStringUtils.isEmpty(command.getAndroidVersion())){
                    update.setAndroidVersion(command.getAndroidVersion());
                }
                if(!CoreStringUtils.isEmpty(command.getIosVersion())){
                    update.setIosVersion(command.getIosVersion());
                }
                if(!CoreStringUtils.isEmpty(command.getHtmlVersion())){
                    update.setHtmlVersion(command.getHtmlVersion());
                }

                if(!CoreStringUtils.isEmpty(command.getAndroidUrl())){
                    update.setAndroidUrl(command.getAndroidUrl());
                }
                if(!CoreStringUtils.isEmpty(command.getIosUrl())){
                    update.setIosUrl(command.getIosUrl());
                }
                if(url != null){
                    update.setHtmlUrl(url);
                }
                update.setLastUpdateDate(new Date());
                update.setModifier(command.getModifier());
                updateRepository.update(update);
            } else {
                update = new Update();
                update.setCreateDate(new Date());
                update.setLastUpdateDate(new Date());
                update.setAndroidVersion(command.getAndroidVersion());
                update.setIosVersion(command.getAndroidVersion());
                update.setHtmlVersion(command.getHtmlVersion());
                update.setAndroidUrl(command.getAndroidUrl());
                update.setIosUrl(command.getIosUrl());
                update.setHtmlUrl(url);
                update.setModifier(command.getModifier());
                updateRepository.save(update);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
