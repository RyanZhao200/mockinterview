package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.constant.QiniuConstant;
import com.debuggor.mockinterview.common.service.QiniuService;
import com.debuggor.mockinterview.common.util.Md5Util;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.service.FinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/***
 * 求职者管理Controller
 */
@Controller
@RequestMapping("/finder")
public class FinderController {

    Logger logger = LoggerFactory.getLogger(FinderController.class);

    @Autowired
    private FinderService finderService;
    @Autowired
    private QiniuService qiniuService;

    /**
     * 求职者登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "front/user/login";
    }

    /**
     * 求职者注册页面
     *
     * @return
     */
    @RequestMapping("/reg")
    public String reg() {
        return "front/user/reg";
    }

    /**
     * 用户登录成功后，挑战到首页
     *
     * @param email
     * @param password
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/loginAction")
    public String loginToIndex(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model, HttpSession session) {
        Finder finder = new Finder();
        finder.setEmail(email);
        finder.setPassword(password);

        String result = finderService.login(finder);
        if (!result.equals(MockConstant.LOGIN_SUCCESS)) {
            model.addAttribute("msg", result);
            // 这里存在问题，msg不能带过去
            return "redirect:/finder/login";
        }
        Finder user = finderService.getFinderByEmail(email);
        logger.info(user.getUsername() + "于" + new Date() + "登录");
        session.setAttribute("user", user);
        return "redirect:/forum";
    }

    /**
     * 注册
     *
     * @param finder
     * @param repassword
     * @param model
     * @return
     */
    @RequestMapping("/register")
    public String register(Finder finder, String repassword, Model model) {
        String result = finderService.register(finder, repassword);
        if (!"ok".equals(result)) {
            model.addAttribute("msg", result);
            return "front/user/reg";
        }
        return "front/user/tips";
    }

    /***
     * 登出
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        return "redirect:/finder/login";
    }

    /***
     * 求职者设置页面
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toSetFinderPage(HttpSession session, Model model) {
        Finder finder = (Finder) session.getAttribute("user");
        model.addAttribute("finder", finder);
        return "front/user/set";
    }

    @RequestMapping("/update")
    public String update(Finder finder, HttpSession session) {
        logger.info("用户跟新操作：" + finder.toString());
        finderService.update(finder);
        Finder user = (Finder) session.getAttribute("user");
        Finder finderByEmail = finderService.getFinderByEmail(user.getEmail());
        session.setAttribute("user", finderByEmail);

        return "redirect:/finder/toUpdate";
    }

    @RequestMapping(value = "/updateHeadUrl", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
    public String updateHeadUrl(MultipartFile headUrl, Integer fid, HttpSession session, Model model) throws IOException {
        // 文件类型限制
        String[] allowedType = {"image/bmp", "image/gif", "image/jpeg", "image/png", "image/jpg"};
        boolean allowed = Arrays.asList(allowedType).contains(headUrl.getContentType());
        if (!allowed) {
            model.addAttribute("msg", "error|不支持的类型");
            return "front/user/set";
        }
        // 图片大小限制
        if (headUrl.getSize() > 3 * 1024 * 1024) {
            model.addAttribute("msg", "error|图片大小不能超过3M");
            return "front/user/set";
        }
        // 包含原始文件名的字符串
        String fi = headUrl.getOriginalFilename();
        // 提取文件拓展名
        String fileNameExtension = fi.substring(fi.indexOf("."), fi.length());
        // 生成云端的真实文件名
        String remoteFileName = UUID.randomUUID().toString() + fileNameExtension;
        qiniuService.upload(headUrl.getBytes(), remoteFileName);
        // 返回图片的URL地址
        String headImage = QiniuConstant.QINIU_IMAGE_URL + remoteFileName;
        logger.info(headImage);

        Finder finder = new Finder();
        finder.setFid(fid);
        finder.setHeadUrl(headImage);
        finderService.update(finder);

        Finder user = (Finder) session.getAttribute("user");
        user = finderService.getFinderByEmail(user.getEmail());
        session.setAttribute("user", user);
        model.addAttribute("finder", user);
        return "front/user/set";
    }

    /**
     * 密码更新还未完成
     * @param oldPassword
     * @param password
     * @param confirm
     * @param session
     * @return
     */
    @RequestMapping("/updatePassword")
    public @ResponseBody
    String updatePassword(@RequestParam("oldPassword") String oldPassword,
                          @RequestParam("password") String password,
                          @RequestParam("confirm") String confirm,
                          HttpSession session) {
        logger.info(oldPassword + password + confirm);
        if (!password.equals(confirm)) {
            return "两次输入密码不相等！";
        }
        if (oldPassword.equals(password)) {
            return "新密码与原密码相同，请重新修改！";
        }
        Finder user = (Finder) session.getAttribute("user");
        Finder finder1 = finderService.getFinderByEmail(user.getEmail());
        String oldhash = Md5Util.hash(oldPassword);
        if (!oldhash.equals(finder1.getPassword())) {
            return "当前密码错误！";
        }
        String hash = Md5Util.hash(password);
        Finder finder = new Finder();
        finder.setFid(finder1.getFid());
        finder.setPassword(hash);
        finderService.update(finder);
        return "success";
    }

    @RequestMapping("/forget")
    public String forgetPasswordPage() {
        return "front/user/forget";
    }

    @RequestMapping("/activate")
    public String activate(String code, Model model) {
        Integer affected = finderService.isActivate(code);
        if (affected != null) {
            model.addAttribute("msg", "邮箱已验证，请登录");
            return "redirect:/finder/login";
        } else {
            model.addAttribute("msg", "验证失败，请重新注册");
            return "redirect:/finder/reg";
        }
    }
}
