package bjl.interfaces.account.web;

import bjl.application.account.IAccountAppService;
import bjl.application.account.command.AuthorizeAccountCommand;
import bjl.application.account.command.ListAccountCommand;
import bjl.application.account.command.ResetPasswordCommand;
import bjl.application.account.representation.AccountRepresentation;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.role.IRoleAppService;
import bjl.application.role.representation.RoleRepresentation;
import bjl.application.shared.command.SharedCommand;
import bjl.application.userManager.IUserManagerAppService;
import bjl.core.enums.EnableStatus;
import bjl.core.enums.LoggerType;
import bjl.core.exception.ConcurrencyException;
import bjl.core.exception.NoFoundException;
import bjl.core.util.CoreHttpUtils;
import bjl.domain.model.account.Account;
import bjl.domain.service.account.IAccountService;
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
import java.util.List;
import java.util.Locale;

/**
 * Created by pengyi on 2016/3/31.
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IAccountAppService accountAppService;
    @Autowired
    private ILoggerAppService loggerAppService;

    @Autowired
    private IUserManagerAppService UserManagerAppService;
    @Autowired
    private IRoleAppService roleAppService;
    @Autowired
    private IAccountService accountService;


    @Autowired
    public AccountController( IAccountAppService accountAppService) {
        this.accountAppService = accountAppService;
    }

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListAccountCommand command) {

        List<RoleRepresentation> list = roleAppService.list(null);

        return new ModelAndView("/account/list", "pagination",accountAppService.pagination(command))
                .addObject("command", command).addObject("roles",list);
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Pagination<AccountRepresentation> list(@RequestBody ListAccountCommand command) {
        return accountAppService.paginationJSON(command);
    }

    @RequestMapping(value = "/info/{id}")
    public ModelAndView info(@PathVariable String id, RedirectAttributes redirectAttributes, Locale locale) {
        AlertMessage alertMessage;
        AccountRepresentation account;
        try {
            account = accountAppService.searchByID(id);
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(this.getMessage("account.id.not.found.messages", new Object[]{id}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            return new ModelAndView("redirect:/account/pagination");
        } catch (Exception e) {
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("/account/info", "account", account);
    }

    @RequestMapping(value = "/update_status")
    public ModelAndView updateStatus(SharedCommand command, RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request) {
        AlertMessage alertMessage;
        Account account;
        try {
            account = accountAppService.updateStatus(command);
        } catch (ConcurrencyException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING,
                    this.getMessage("default.optimistic.locking.failure", new Object[]{command.getId()}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            redirectAttributes.addAttribute("id", command.getId());
            return new ModelAndView("redirect:/account/pagination");
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(this.getMessage("account.id.not.found.messages", new Object[]{command.getId()}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            return new ModelAndView("redirect:/account/pagination");
        } catch (Exception e) {
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    (account.getStatus() == EnableStatus.DISABLE?"禁用":"启用")+"用户【"+command.getId()+"】成功", CoreHttpUtils.getClientIP(request));

            loggerAppService.create(loggerCommand);
        }
        alertMessage = new AlertMessage(this.getMessage("default.edit.success.messages", null, locale));
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        return new ModelAndView("redirect:/account/pagination");
    }

    @RequestMapping(value = "/reset_password/{id}", method = RequestMethod.GET)
    public ModelAndView resetPassword(@PathVariable String id, @ModelAttribute("command") ResetPasswordCommand command,
                                      RedirectAttributes redirectAttributes, Locale locale) {
        AlertMessage alertMessage;
        AccountRepresentation account;
        try {
            account = accountAppService.searchByID(id);
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(this.getMessage("account.id.not.found.messages", new Object[]{id}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            return new ModelAndView("redirect:/account/pagination");
        } catch (Exception e) {
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("/account/resetPassword", "account", account).addObject("command", command);
    }

    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    public ModelAndView resetPassword(@Valid @ModelAttribute("command") ResetPasswordCommand command, BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            AlertMessage alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING,
                    this.getMessage("account.password.NotBlank.messages", null, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            redirectAttributes.addAttribute("id", command.getId());
            return new ModelAndView("redirect:/account/reset_password/{id}");
        }

        AlertMessage alertMessage;
        Account account;
        try {
            account = accountAppService.resetPassword(command);

        } catch (ConcurrencyException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING,
                    this.getMessage("default.optimistic.locking.failure", new Object[]{command.getId()}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            redirectAttributes.addAttribute("id", command.getId());
            return new ModelAndView("redirect:/account/reset_password/{id}");
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(this.getMessage("account.id.not.found.messages", new Object[]{command.getId()}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            return new ModelAndView("redirect:/account/pagination");
        } catch (Exception e) {
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "重置用户【"+account.getName()+"】密码成功", CoreHttpUtils.getClientIP(request));

            loggerAppService.create(loggerCommand);
        }
        alertMessage = new AlertMessage(this.getMessage("default.edit.success.messages", null, locale));
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        return new ModelAndView("redirect:/account/pagination");
    }

    @RequestMapping(value = "/authorize/{id}", method = RequestMethod.GET)
    public ModelAndView authorize(@PathVariable String id, @ModelAttribute("command") AuthorizeAccountCommand command,
                                  RedirectAttributes redirectAttributes, Locale locale) {
        AlertMessage alertMessage;
        AccountRepresentation account;
        try {
            account = accountAppService.searchByID(id);
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(this.getMessage("account.id.not.found.messages", new Object[]{id}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            return new ModelAndView("redirect:/account/pagination");
        } catch (Exception e) {
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("/account/authorize", "account", account).addObject("command", command);
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public ModelAndView authorize(String role,String accountId, HttpServletRequest request,
                                  RedirectAttributes redirectAttributes, Locale locale) {
        AlertMessage alertMessage;
        Account account;
        try {
            account = accountAppService.authorized(accountId,role);
        } catch (ConcurrencyException e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING,
                    this.getMessage("default.optimistic.locking.failure", new Object[]{accountId}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            redirectAttributes.addAttribute("id", accountId);
            return new ModelAndView("redirect:/account/authorize/{id}");
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            if (e.getMessage().indexOf("Role") != -1) {
                alertMessage = new AlertMessage(this.getMessage("role.id.not.found.messages", new Object[]{role}, locale));
                redirectAttributes.addAttribute("id", accountId);
                redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
                return new ModelAndView("redirect:/account/authorize/{id}");
            } else {
                alertMessage = new AlertMessage(this.getMessage("account.id.not.found.messages", new Object[]{accountId}, locale));
                redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
                return new ModelAndView("redirect:/account/pagination");
            }
        } catch (Exception e) {
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "授权用户【"+(account == null?"":account.getName())+"】角色【"+(account == null ? "":account.getRoles().get(0).getDescription())+"】成功", CoreHttpUtils.getClientIP(request));

            loggerAppService.create(loggerCommand);
        }
        alertMessage = new AlertMessage(this.getMessage("default.edit.success.messages", null, locale));
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        return new ModelAndView("redirect:/account/pagination");
    }

    @RequestMapping(value = "/gag", method = RequestMethod.GET)
    public ModelAndView authorize(String id, HttpServletRequest request,
                                  RedirectAttributes redirectAttributes, Locale locale) {

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser == null) {
            return new ModelAndView("redirect:/logged");
        }
        AlertMessage alertMessage;
        Account account;
        try {
            account = accountAppService.gag(id);
        } catch (Exception e) {
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }

        if(account != null){
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    account.getGag()==1?"已禁言用户【"+account.getName()+"】":"已解除用户【"+account.getName()+"】的禁言", CoreHttpUtils.getClientIP(request));
            loggerAppService.create(loggerCommand);
        }

        alertMessage = new AlertMessage(this.getMessage("default.edit.success.messages", null, locale));
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        return new ModelAndView("redirect:/account/pagination");
    }

    /**
     * 设置前台管理员
     */
    @RequestMapping(value = "/frontAdmin", method = RequestMethod.GET)
    public ModelAndView frontAdmin(SharedCommand command) {

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser == null) {
            return new ModelAndView("redirect:/logged");
        }
        Account account;
        try {
            account = accountAppService.updateFontAdmin(command);
        } catch (ConcurrencyException e) {
            logger.warn(e.getMessage());
            return new ModelAndView("redirect:/account/pagination");
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            return new ModelAndView("redirect:/account/pagination");
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("redirect:/account/pagination");
        }
        return new ModelAndView("redirect:/account/pagination");
    }

}
