package bjl.core.upload;

import bjl.application.account.IAccountAppService;
import bjl.application.chat.command.CreateChatCommand;
import bjl.core.chat.ChatProcess;
import bjl.core.redis.RedisService;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreImgUtils;
import bjl.core.util.CoreStringUtils;
import bjl.core.util.ServiceUtil;
import bjl.domain.model.account.Account;
import bjl.domain.model.user.User;
import bjl.domain.service.account.IAccountService;
import bjl.tcp.GlobalVariable;
import bjl.websocket.command.GameStatus;
import com.alibaba.fastjson.JSONObject;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by pengyi on 2016/4/11.
 */
public class FileUploadService implements IFileUploadService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String DOT = ".";

    private FileUploadConfig fileUploadConfig;
    @Autowired
    private IAccountAppService accountAppService;
    @Autowired
    private RedisService redisService;

    public void setFileUploadConfig(FileUploadConfig fileUploadConfig) {
        this.fileUploadConfig = fileUploadConfig;
    }

    @Override
    public UploadResult upload(MultipartFile[] files) throws IOException {
        List<Object> resultFiles = new ArrayList<Object>();
        File folder = new File(fileUploadConfig.getPath(), fileUploadConfig.getFolder());//创建工作目录
        folder.mkdirs();

        String url = fileUploadConfig.getDomainName() + fileUploadConfig.getFolder();
        for (MultipartFile file : files) {
            String message = null;
            if (file.isEmpty()) {
                message = "文件未上传";
                logger.info("文件未上传");
            }

            //返回客户端的文件系统中的原始文件名
            String fileName = file.getOriginalFilename();
            //获取文件后缀名
            String type = (fileName.substring(fileName.lastIndexOf(DOT) + 1)).toLowerCase();
            //获取文件大小
            long fileSize = file.getSize();

            if (fileUploadConfig.getMaxSize() < fileSize) {
                message = "文件过大，无法上传！";
                logger.info("上传文件[{}]大小[{}]超过了[{}]", fileName, fileSize, fileUploadConfig.getMaxSize());
            }

            if (!fileUploadConfig.getType().contains(type)) {
                message = "文件类型错误！";
                logger.info("上传文件[{}]类型[{}]错误", fileName, type);
            }
            if (CoreStringUtils.isEmpty(message)) {
                String filename = file.getOriginalFilename();
                String roomNo = filename.substring(0, filename.indexOf("_"));

                File file1 = new File(folder, roomNo);
                file1.mkdirs();
                //原文件
                File saveFile = new File(file1, CoreStringUtils.join(DOT, filename.substring(0, filename.indexOf(".")), type));
                file.transferTo(saveFile);

                logger.info("上传文件[{}]成功", saveFile.getAbsolutePath());

                resultFiles.add(new UploadSuccess(saveFile.getName(), fileSize,
                        url + saveFile.getName(),
                        CoreStringUtils.join(null, "/upload/delete?roomNo=", roomNo)));
            } else {
                resultFiles.add(new UploadFailure(file.getOriginalFilename(), message));
            }
        }
        return new UploadResult(resultFiles.toArray());
    }

    /**
     * @param roomNo 全路径图片地址
     */
    @Override
    public void delete(String roomNo) {
        try {
            File folder = new File(fileUploadConfig.getPath(), fileUploadConfig.getFolder());
            File file = new File(folder, roomNo);
            this.deleteFile(file);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    /**
     * 修改头像
     *
     * @param file
     * @return
     */
    @Override
    public JSONObject uploadHead(MultipartFile file, String username) {

        JSONObject jsonObject = new JSONObject();

        //用户是否存在
        if (CoreStringUtils.isEmpty(username)) {
            jsonObject.put("status", "fail");
            jsonObject.put("errmsg", "用户不存在");
            return jsonObject;
        }
        //文件是否为空
        if (file == null || file.isEmpty()) {
            jsonObject.put("status", "fail");
            jsonObject.put("errmsg", "没有上传文件");
            return jsonObject;
        }
        //获取文件大小
        long fileSize = file.getSize();
        if (fileUploadConfig.getMaxSize() < fileSize) {
            jsonObject.put("status", "fail");
            jsonObject.put("errmsg", "文件大小超出【"+(fileUploadConfig.getMaxSize())/1024+"KB】限制");
            return jsonObject;
        }

        //返回客户端的文件系统中的原始文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String type = (fileName.substring(fileName.lastIndexOf(DOT) + 1)).toLowerCase();
        if (!fileUploadConfig.getType().contains(type)) {
            jsonObject.put("status","fail");
            jsonObject.put("errmsg", "不支持的图片类型"+fileUploadConfig.getType().toString()+"");
            return jsonObject;
        }

        Account account = accountAppService.searchByUsername(username);
        if (account == null) {
            jsonObject.put("status", "fail");
            jsonObject.put("errmsg", "用户不存在");
            return jsonObject;
        }
        try {
            File folder = new File(fileUploadConfig.getPath(), fileUploadConfig.getFolder());//创建工作目录
            folder.mkdirs();
            //遍历,删除之前的头像
            File files = new File(folder+"");
            String[] fileNames = files.list();
            for(String name:fileNames){
                if(name.startsWith(username)){
                    File file1 = new File(folder+"/"+name);
                    file1.delete();
                }
            }
            //以用户ID+时间戳作为图片名称
            String result = username + System.currentTimeMillis();
            String newFileName = result + "." + type;
            FileOutputStream out = new FileOutputStream(folder + "/" + newFileName);
            // 写入文件
            out.write(file.getBytes());
            out.flush();
            out.close();
            //更新用户头像
            boolean flag = accountAppService.updateHead(fileUploadConfig.getDomainName() + fileUploadConfig.getFolder() + newFileName, username);
            if(flag){
                //更新用户头像成功
                logger.info(username + "修改头像[{}]成功", newFileName);
                jsonObject.put("status", "succ");
                jsonObject.put("url", fileUploadConfig.getDomainName() + fileUploadConfig.getFolder() + newFileName);
                jsonObject.put("errmsg", "修改头像成功");
            }else {
                jsonObject.put("status", "fail");
                jsonObject.put("errmsg", "修改头像失败");
            }

        } catch (Exception e) {
            jsonObject.put("status","fail");
            jsonObject.put("errmsg","修改头像失败");
        }
        return jsonObject;
    }

    /**
     * 保存聊天图片
     * @param file
     * @param username
     * @return
     */
    @Override
    public JSONObject uploadChat(MultipartFile file, String username, Integer roomType) {

        JSONObject jsonObject = new JSONObject();
        //用户是否存在
        if (CoreStringUtils.isEmpty(username)) {
            jsonObject.put("status", "fail");
            jsonObject.put("errmsg", "用户不存在");
            return jsonObject;
        }
        //文件是否为空
        if (file == null || file.isEmpty()) {
            jsonObject.put("status", "fail");
            jsonObject.put("errmsg", "没有上传文件");
            return jsonObject;
        }
        //获取文件大小
        long fileSize = file.getSize();
        if (fileUploadConfig.getMaxSize() < fileSize) {
            jsonObject.put("status", "fail");
            jsonObject.put("errmsg", "文件大小超出【"+(fileUploadConfig.getMaxSize())/1024+"KB】限制");
            return jsonObject;
        }

        //返回客户端的文件系统中的原始文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String type = (fileName.substring(fileName.lastIndexOf(DOT) + 1)).toLowerCase();
        if (!fileUploadConfig.getType().contains(type)) {
            jsonObject.put("status","fail");
            jsonObject.put("errmsg", "不支持的图片类型"+fileUploadConfig.getType().toString()+"");
            return jsonObject;
        }
        Account account = accountAppService.searchByUsername(username);
        if (account == null) {
            jsonObject.put("status", "fail");
            jsonObject.put("errmsg", "用户不存在");
            return jsonObject;
        }
        if(account.getGag() != null && account.getGag() == 1){
            jsonObject.put("status", "fail");
            jsonObject.put("errmsg", "该账户已被禁言");
            return jsonObject;
        }
        try {
            File folder = new File(fileUploadConfig.getPath(), fileUploadConfig.getChatImg());//创建工作目录
            folder.mkdirs();
            //时间戳+用户ID作为图片名称
            String result = CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS")+username;
            String newFileName = result + "." + type;
            FileOutputStream out = new FileOutputStream(folder + "/" + newFileName);
            // 写入文件
            out.write(file.getBytes());
            out.flush();
            out.close();
            //上传聊天图片成功
            result = fileUploadConfig.getDomainName() + fileUploadConfig.getChatImg() + newFileName;
            logger.info(username + "上传聊天图片[{}]成功", result);
            jsonObject.put("status", "succ");
            jsonObject.put("url", result);
            jsonObject.put("errmsg", "聊天图片发送成功");
            //广播聊天信息
            ChatProcess.messageProcess(new CreateChatCommand(roomType,username,3,result));

        } catch (Exception e) {
            jsonObject.put("status", "fail");
            jsonObject.put("errmsg", "聊天图片发送失败");
        }
        return jsonObject;
    }

    /**
     * 生成下注图片
     * @param fileName 文件名
     */
    @Override
    public String uploadBetImg(String fileName, BufferedImage image) {

        try {
            File folder = new File(fileUploadConfig.getPath(), fileUploadConfig.getBetImg());//创建工作目录
            folder.mkdirs();
            FileOutputStream out = new FileOutputStream(folder + "/" + fileName);
            // 写入文件
            BufferedOutputStream bos = new BufferedOutputStream(out);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
            return fileUploadConfig.getDomainName() + fileUploadConfig.getBetImg() + fileName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存开奖图片
     * @param lotteryImg 开奖截图
     * @param roadImg   路单截图
     * @param strings 开奖信息
     */
    @Override
    public void uploadLottery(MultipartFile lotteryImg, MultipartFile roadImg,MultipartFile sceneImg, String[] strings) {

        try {
            File folder = new File(fileUploadConfig.getPath(), fileUploadConfig.getLottery());//创建工作目录
            folder.mkdirs();
            //返回客户端的文件系统中的原始文件名
            String fileName1 = lotteryImg.getOriginalFilename();
            //获取文件后缀名
            String type1 = "."+(fileName1.substring(fileName1.lastIndexOf(DOT) + 1)).toLowerCase();
            String newName1;

            if("11".equals(strings[1])){
                //如果是重开,则后面跟不同信息，来和之前区分
                //年-月-日_房_鞋_局_投注1/分数2/开奖3/路单4+时间戳; 2018-1-2_1_1_1_1_15454678944.jpg
                GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[0]));
                //开奖截图
                newName1 = CoreDateUtils.formatDate(new Date())+"_"+strings[0]+"_"+gameStatus.getxNum()+"_"+gameStatus.getjNum()+"_"+3+System.currentTimeMillis()+type1;
            }else {
                //年-月-日_房_鞋_局_投注1/分数2/开奖3/路单4; 2018-1-2_1_1_1_1.jpg
                GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[0]));
                //开奖截图
                newName1 = CoreDateUtils.formatDate(new Date())+"_"+strings[0]+"_"+gameStatus.getxNum()+"_"+gameStatus.getjNum()+"_"+3+type1;
            }

            FileOutputStream out1 = new FileOutputStream(folder + "/" + newName1);

            // 写入文件1
            out1.write(lotteryImg.getBytes());
            out1.flush();
            out1.close();
            //广播截图信息
            String url1 = fileUploadConfig.getDomainName() + fileUploadConfig.getLottery() + newName1;

            CreateChatCommand chatCommand = new CreateChatCommand(Integer.valueOf(strings[0]),"admin",3,url1);
            ServiceUtil.serviceUtil.getChatAppService().create(chatCommand);

            if(roadImg != null){
                String fileName2 = roadImg.getOriginalFilename();
                String type2 = "."+(fileName2.substring(fileName2.lastIndexOf(DOT) + 1)).toLowerCase();
                String newName2;
                if("11".equals(strings[1])){
                    //如果是重开,则后面跟不同信息，来和之前区分
                    //年-月-日_房_鞋_局_投注1/分数2/开奖3/路单4+时间戳; 2018-1-2_1_1_1_1_15454678944.jpg
                    GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[0]));
                    //路单截图
                    newName2 = CoreDateUtils.formatDate(new Date())+"_"+strings[0]+"_"+gameStatus.getxNum()+"_"+gameStatus.getjNum()+"_"+4+System.currentTimeMillis()+type2;
                }else {
                    //年-月-日_房_鞋_局_投注1/分数2/开奖3/路单4; 2018-1-2_1_1_1_1.jpg
                    GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[0]));
                    //路单截图
                    newName2 = CoreDateUtils.formatDate(new Date())+"_"+strings[0]+"_"+gameStatus.getxNum()+"_"+gameStatus.getjNum()+"_"+4+type2;
                }

                FileOutputStream out2 = new FileOutputStream(folder + "/" + newName2);
                // 写入文件2
                out2.write(roadImg.getBytes());
                out2.flush();
                out2.close();
                //广播截图信息
                String url2 = fileUploadConfig.getDomainName() + fileUploadConfig.getLottery() + newName2;
                chatCommand = new CreateChatCommand(Integer.valueOf(strings[0]),"admin",3,url2);
                ServiceUtil.serviceUtil.getChatAppService().create(chatCommand);

            }

            if(sceneImg != null){
                String fileName3 = sceneImg.getOriginalFilename();
                String type3 = "."+(fileName3.substring(fileName3.lastIndexOf(DOT) + 1)).toLowerCase();
                String newName3;
                if("11".equals(strings[1])){
                    //如果是重开,则后面跟不同信息，来和之前区分
                    //年-月-日_房_鞋_局_投注1/分数2/开奖3/路单4+时间戳; 2018-1-2_1_1_1_1_15454678944.jpg
                    GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[0]));
                    //现场截图
                    newName3 = CoreDateUtils.formatDate(new Date())+"_"+strings[0]+"_"+gameStatus.getxNum()+"_"+gameStatus.getjNum()+"_"+5+System.currentTimeMillis()+type3;
                }else {
                    //年-月-日_房_鞋_局_投注1/分数2/开奖3/路单4; 2018-1-2_1_1_1_1.jpg
                    GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[0]));
                    //现场截图
                    newName3 = CoreDateUtils.formatDate(new Date())+"_"+strings[0]+"_"+gameStatus.getxNum()+"_"+gameStatus.getjNum()+"_"+5+type3;

                }
                FileOutputStream out3 = new FileOutputStream(folder + "/" + newName3);
                // 写入文件3
                out3.write(sceneImg.getBytes());
                out3.flush();
                out3.close();
                String url3 = fileUploadConfig.getDomainName() + fileUploadConfig.getLottery() + newName3;
                chatCommand = new CreateChatCommand(Integer.valueOf(strings[0]),"admin",3,url3);
                ServiceUtil.serviceUtil.getChatAppService().create(chatCommand);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 上传公告图片
     * @param file 图片
     * @return
     */
    @Override
    public JSONObject uploadNotice(MultipartFile file) {

        JSONObject jsonObject = new JSONObject();

        //返回客户端的文件系统中的原始文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String type = (fileName.substring(fileName.lastIndexOf(DOT) + 1)).toLowerCase();
        if (!fileUploadConfig.getType().contains(type)) {
            jsonObject.put("status",1);
            jsonObject.put("errmsg", "不支持的图片类型"+fileUploadConfig.getType().toString()+"");
            return jsonObject;
        }

        try {
            File folder = new File(fileUploadConfig.getPath(), fileUploadConfig.getFolder());//创建工作目录
            folder.mkdirs();
            //notice+时间戳作为图片名称
            String result = "notice_"+CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
            String newFileName = result + "." + type;
            FileOutputStream out = new FileOutputStream(folder + "/" + newFileName);
            // 写入文件
            out.write(file.getBytes());
            out.flush();
            out.close();
            //上传聊天图片成功
            result = fileUploadConfig.getDomainName() + fileUploadConfig.getFolder() + newFileName;
            logger.info("上传公告图片[{}]成功", result);
            jsonObject.put("status", 0);
            jsonObject.put("url", result);
            //广播聊天信息

        } catch (Exception e) {
            jsonObject.put("status", 1);
            jsonObject.put("errmsg", "聊天图片发送失败");
        }
        return jsonObject;
    }

    /**
     * 图片验证码
     * @param jsonObject
     * @return
     */
    @Override
    public synchronized JSONObject validateCode(JSONObject jsonObject) {

        try {
            File folder = new File(fileUploadConfig.getPath(), fileUploadConfig.getValidateCode());//创建工作目录
            folder.mkdirs();
            //UUID为唯一标识
            String uuid = UUID.randomUUID().toString();
            //图片途径
            String path = folder+"/"+uuid+".png";
            //生成图片,返回验证码
            String vCode = CoreImgUtils.validateCode(path);
            if(vCode != null){
                Object[] objects = new Object[2];
                objects[0] = vCode;
                objects[1] = System.currentTimeMillis();

                //存储验证码
                GlobalVariable.getCodeMap().put(uuid,objects);

                String imgUrl = fileUploadConfig.getDomainName() + fileUploadConfig.getValidateCode() + uuid+".png";
                jsonObject.put("code", 0);
                jsonObject.put("errmsg", "获取验证码图片成功");
                jsonObject.put("flag", uuid);
                jsonObject.put("pic", imgUrl);
                return jsonObject;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        jsonObject.put("code", 1);
        jsonObject.put("errmsg", "获取验证码图片失败");
        return jsonObject;
    }

    /**
     * 删除本地图片
     * @param flag
     */
    @Override
    public void deleteCode(String flag) {

        try {
            if(flag == null){
                Map<String,Object[]> maps = GlobalVariable.getCodeMap();
                //验证码，存活期为2分钟
                //使用迭代器的remove()方法删除元素
                Iterator<Map.Entry<String,Object[]>> it = maps.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry<String,Object[]> entry = it.next();
                    if((System.currentTimeMillis()-(long)entry.getValue()[1]) > 120000){
                        //清除本地验证码图片
                        File file = new File(fileUploadConfig.getPath(), fileUploadConfig.getValidateCode()+"/"+entry.getValue()[0]+".png");
                        if(file.exists()){
                            file.delete();
                        }
                        it.remove();
                    }
                }
            }else {
                //清除本地验证码图片
                File file = new File(fileUploadConfig.getPath(), fileUploadConfig.getValidateCode()+"/"+flag+".png");
                if(file.exists()){
                    file.delete();
                }
                GlobalVariable.getCodeMap().remove(flag);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean deleteFile(File file) {
        boolean flag = FileUtils.deleteQuietly(file);
        if (flag) {
            logger.info("删除文件[{}]成功", file.getAbsolutePath());
        } else {
            logger.error("删除文件[{}]失败", file.getAbsolutePath());
        }
        return flag;
    }
}
