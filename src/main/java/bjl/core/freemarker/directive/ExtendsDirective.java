package bjl.core.freemarker.directive;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by pengyi on 2016/3/3.
 */
public class ExtendsDirective implements TemplateDirectiveModel {

    public final static String DIRECTIVE_NAME = "extends";

    public void execute(Environment env,
                        Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {

        String name = DirectiveUtils.getRequiredParam(params, "name");
        String encoding = DirectiveUtils.getParam(params, "encoding", null);
        String includeTemplateName = env.toFullTemplateName(getTemplatePath(env), name);
        env.include(includeTemplateName, encoding, true);
    }

    private String getTemplatePath(Environment env) {
        String templateName = ((Template) env.getParent()).getName();
        return templateName.lastIndexOf('/') == -1 ? "" : templateName.substring(0, templateName.lastIndexOf('/') + 1);
    }

}

