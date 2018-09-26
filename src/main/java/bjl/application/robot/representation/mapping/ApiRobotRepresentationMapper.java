package bjl.application.robot.representation.mapping;

import bjl.application.robot.representation.ApiRobotRepresentation;
import bjl.core.upload.FileUploadConfig;
import bjl.domain.model.robot.Robot;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2018/5/2
 */
@Component
public class ApiRobotRepresentationMapper extends CustomMapper<Robot,ApiRobotRepresentation> {

    @Autowired
    private FileUploadConfig fileUploadConfig;

    public void mapAtoB(Robot robot, ApiRobotRepresentation representation, MappingContext context) {

        String head = "http://www.ying906.com/"+fileUploadConfig.getFolder()+robot.getHead()+".png";
        representation.setUserid(robot.getId());
        representation.setHead(head);
    }
}
