package bjl.interfaces.permission.web;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.permission.IPermissionAppService;
import bjl.application.permission.command.CreatePermissionCommand;
import bjl.application.permission.command.EditPermissionCommand;
import bjl.application.permission.command.ListPermissionCommand;
import bjl.application.permission.representation.PermissionRepresentation;
import bjl.application.shared.command.SharedCommand;
import bjl.core.enums.LoggerType;
import bjl.core.exception.ConcurrencyException;
import bjl.core.exception.ExistException;
import bjl.core.exception.NoFoundException;
import bjl.core.util.CoreHttpUtils;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import bjl.interfaces.shared.web.AlertMessage;
import bjl.interfaces.shared.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by pengyi on 2016/3/31.
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private final IPermissionAppService permissionAppService;
    @Autowired
    private ILoggerAppService loggerAppService;

    @Autowired
    public PermissionController( IPermissionAppService permissionAppService) {
        this.permissionAppService = permissionAppService;
    }

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListPermissionCommand command) {
        return new ModelAndView("/permission/list", "pagination", permissionAppService.pagination(command)).addObject("command", command);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@ModelAttribute("command") CreatePermissionCommand command) {
        return new ModelAndView("/permission/create", "command", command);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute("command") CreatePermissionCommand command, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/permission/create", "command", command);
        }
        AlertMessage alertMessage;
        PermissionRepresentation permission;
        try {
            permission = permissionAppService.create(command);
        } catch (ExistException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING,
                    this.getMessage("permission.name.Exist.messages", new Object[]{command.getName()}, locale));
            return new ModelAndView("/permission/create", "command", command)
                    .addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        } catch (Exception e) {
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "创建权限【"+permission.getName()+"】成功", CoreHttpUtils.getClientIP(request));

            loggerAppService.create(loggerCommand);
        }
        alertMessage = new AlertMessage(this.getMessage("default.create.success.messages", null, locale));
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        redirectAttributes.addAttribute("id", permission.getId());
        return new ModelAndView("redirect:/permission/info/{id}");
    }

    @RequestMapping(value = "/info/{id}")
    public ModelAndView info(@PathVariable String id, RedirectAttributes redirectAttributes, Locale locale) {
        AlertMessage alertMessage;
        PermissionRepresentation permission;
        try {
            permission = permissionAppService.searchByID(id);
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING, this.getMessage("permission.id.not.found.messages", new Object[]{id}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            return new ModelAndView("redirect:/permission/pagination");
        } catch (Exception e) {
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("/permission/info", "permission", permission);
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable String id, @ModelAttribute("command") EditPermissionCommand command,
                             RedirectAttributes redirectAttributes, Locale locale) {
        AlertMessage alertMessage;
        PermissionRepresentation permission;
        try {
            permission = permissionAppService.searchByID(id);
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING, this.getMessage("permission.id.not.found.messages", new Object[]{id}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            return new ModelAndView("redirect:/permission/pagination");
        } catch (Exception e) {
            logger.error(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("/permission/edit", "permission", permission).addObject("command", command);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@Valid @ModelAttribute("command") EditPermissionCommand command, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/permission/edit", "command", command);
        }
        AlertMessage alertMessage;
        PermissionRepresentation permission;
        try {
            permission = permissionAppService.edit(command);
        } catch (ConcurrencyException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING,
                    this.getMessage("default.optimistic.locking.failure", new Object[]{command.getName()}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            redirectAttributes.addAttribute("id", command.getId());
            return new ModelAndView("redirect:/permission/edit/{id}");
        } catch (ExistException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING,
                    this.getMessage("permission.name.Exist.messages", new Object[]{command.getName()}, locale));
            return new ModelAndView("/permission/edit", "command", command)
                    .addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING, this.getMessage("permission.id.not.found.messages", new Object[]{command.getId()}, locale));
            return new ModelAndView("/permission/edit", "command", command).addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        } catch (Exception e) {
            logger.error(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "修改权限【"+permission.getName()+"】成功", CoreHttpUtils.getClientIP(request));

            loggerAppService.create(loggerCommand);
        }
        alertMessage = new AlertMessage(this.getMessage("default.edit.success.messages", null, locale));
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        redirectAttributes.addAttribute("id", permission.getId());
        return new ModelAndView("redirect:/permission/info/{id}");
    }

    @RequestMapping(value = "/update_status")
    public ModelAndView updateStatus(SharedCommand command, RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request) {
        AlertMessage alertMessage;
        try {
            permissionAppService.updateStatus(command);
        } catch (ConcurrencyException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING,
                    this.getMessage("default.optimistic.locking.failure", new Object[]{command.getId()}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            redirectAttributes.addAttribute("id", command.getId());
            return new ModelAndView("redirect:/permission/pagination");
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING, this.getMessage("permission.id.not.found.messages", new Object[]{command.getId()}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            return new ModelAndView("redirect:/permission/pagination");
        } catch (Exception e) {
            logger.error(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "更新权限状态成功", CoreHttpUtils.getClientIP(request));

            loggerAppService.create(loggerCommand);
        }
        alertMessage = new AlertMessage(this.getMessage("default.edit.success.messages", null, locale));
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        return new ModelAndView("redirect:/permission/pagination");
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Pagination<PermissionRepresentation> list(@RequestBody ListPermissionCommand command) {
        return permissionAppService.paginationJSON(command);
    }
}
