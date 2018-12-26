package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.bean.Message;
import com.debuggor.mockinterview.common.bean.vo.InterviewerVo;
import com.debuggor.mockinterview.common.constant.UserConstant;
import com.debuggor.mockinterview.common.enumerate.*;
import com.debuggor.mockinterview.common.service.InterviewTypeService;
import com.debuggor.mockinterview.common.service.MessageService;
import com.debuggor.mockinterview.common.service.QiniuService;
import com.debuggor.mockinterview.interview.bean.*;
import com.debuggor.mockinterview.interview.service.*;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class InterviewController {
    Logger logger = LoggerFactory.getLogger(InterviewController.class);

    @Autowired
    private InterviewTypeService interviewTypeService;
    @Autowired
    private InterviewService interviewService;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private InterviewerService interviewerService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private EvaluationService evaluationService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private FinderService finderService;
    @Autowired
    private FollowerService followerService;

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) {
        // 获取面试官类别
        List<Type> interviewTypeList = interviewTypeService.getInterviewTypeList();
        model.addAttribute("parentList", interviewTypeList);
        // 获取首页展示的面试官
        List<InterviewerVo> interviewerVoList = interviewService.getInterviewerIndexList();
        model.addAttribute("voList", interviewerVoList);
        // 推荐的面试官
        List<Interviewer> interviewerHot = interviewService.getInterviewerHot(4);
        model.addAttribute("interviewerHot", interviewerHot);
        // 获取面试官的数量
        Integer interviewerNum = interviewerService.getInterviewerNum();
        model.addAttribute("interviewerNum", interviewerNum);
        return "front/index";
    }

    /**
     * 面试官列表（查询）
     *
     * @param model
     * @return
     */
    @RequestMapping("/interviewer")
    public String list(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                       @RequestParam(required = false, value = "tid") Integer tid,
                       Interviewer interviewer, Model model) {
        // 获取面试官类别
        List<Type> interviewTypeList = interviewTypeService.getInterviewTypeList();
        model.addAttribute("parentList", interviewTypeList);
        // 获取所有的面试官信息 列表
        if (tid != null) {
            interviewer.setTid(tid);
            model.addAttribute("tid", tid);
            Type type = interviewTypeService.getTypeById(tid);
            model.addAttribute("type", type);
        }
        PageInfo interviewerList = interviewService.getInterviewerList(interviewer, pn);
        logger.info("总共" + String.valueOf(interviewerList.getTotal()) + "条记录");
        model.addAttribute("pageInfo", interviewerList);
        return "front/interview/list";
    }


    /**
     * 面试官详情页（个人介绍、用户评价、热门面试官）
     *
     * @return
     */
    @RequestMapping("/interviewer/{iid}")
    public String interview(@PathVariable("iid") Integer iid,
                            @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                            HttpSession session, Model model) {
        Interviewer interviewer = interviewService.getInterviewerById(iid);
        model.addAttribute("interviewer", interviewer);
        List<Interviewer> interviewers = interviewService.getInterviewerHot(5);
        model.addAttribute("interviewers", interviewers);
        PageInfo pageInfo = evaluationService.getEvaluationByIid(iid, pn);
        model.addAttribute("pageInfo", pageInfo);
        Integer evaluationNum = evaluationService.getEvaluationNumByIid(iid);
        model.addAttribute("evaluationNum", evaluationNum);
        Float grade = evaluationService.getEvaluationGradeByIid(iid);
        model.addAttribute("grade", grade);
        model.addAttribute("iid", iid);
        // 是否关注
        Follower follower = new Follower();
        follower.setFollowersUid(iid);
        follower.setFollowersType(UserEnum.INTERVIEWER.key);
        Finder finder = (Finder) session.getAttribute("finder");
        if (finder != null) {
            follower.setFollowingUid(finder.getFid());
            follower.setFollowingType(UserEnum.FINDER.key);
        }
        Interviewer iv = (Interviewer) session.getAttribute("interviewer");
        if (iv != null) {
            follower.setFollowingUid(iv.getIid());
            follower.setFollowingType(UserEnum.INTERVIEWER.key);
        }
        Boolean followed = followerService.isFollowed(follower);
        model.addAttribute("followed",followed);
        return "front/interview/detail";
    }

    /**
     * 面试官向求职者发起视频聊天
     *
     * @return
     */
    @RequestMapping("/interview/publish")
    public String interviewerToFinder(@RequestParam(required = false, value = "fid") Integer fid,
                                      HttpSession session, Model model) throws Exception {
        Interviewer interviewer = (Interviewer) session.getAttribute("interviewer");
        if (interviewer == null) {
            return "/";
        }
        String roomToken = qiniuService.getRoomToken(interviewer.getIid(), fid, UserConstant.Interviewer_Type);
        model.addAttribute("roomToken", roomToken);
        logger.info("面试官：" + interviewer.getUsername() + "发起了面试");
        return "front/interview/room";
    }

    /**
     * 求职者向面试官发起视频聊天
     *
     * @return
     */
    @RequestMapping("/interview/subscribe")
    public String finderToInterviewer(@RequestParam(required = false, value = "iid") Integer iid,
                                      HttpSession session, Model model) throws Exception {
        Finder finder = (Finder) session.getAttribute("finder");
        if (finder == null) {
            return "/";
        }
        String roomToken = qiniuService.getRoomToken(iid, finder.getFid(), UserConstant.Finder_Type);
        model.addAttribute("roomToken", roomToken);
        logger.info("求职者：" + finder.getUsername() + "发起了面试");
        return "front/interview/room";
    }

    /**
     * 求职者面试流程-面试 环节
     *
     * @param oid   订单ID
     * @param model
     * @return
     */
    @RequestMapping("/interview/interview/{oid}")
    public String interviewPage(@PathVariable("oid") Integer oid, Model model) {
        if (oid == null) {
            return "/";
        }
        Order order = ordersService.getOrderById(oid);
        // 获取面试官信息
        Interviewer interviewer = interviewerService.getInterviewerById(order.getInterviewerId());
        model.addAttribute("interviewer", interviewer);
        model.addAttribute("order", order);
        return "front/interview/interview";
    }

    /**
     * 确认结单 并 跳转到评论页面
     *
     * @return
     */
    @RequestMapping("/interview/confirmOrder")
    public String confirmOrder(@RequestParam(required = false, value = "oid") Integer oid) {
        if (oid == null) {
            return "/";
        }
        Order orderById = ordersService.getOrderById(oid);
        Order order = new Order();
        order.setOrderNum(orderById.getOrderNum());
        // 结单:状态改变、新加结单时间
        order.setIsOrdered(StatusEnum.YES.key);
        order.setOrderedTime(new Date());
        ordersService.updateOrder(order);
        // 更新求职者消息
        Message m = messageService.getMessageByOid(oid, UserEnum.FINDER.key);
        Message message = new Message();
        message.setMid(m.getMid());
        message.setUpdateTime(new Date());
        message.setMessageUrl("/interview/evaluate/" + oid);
        message.setStatusType(StatusTypeEnum.WAIT_COMMENT.key);
        message.setMessageStatus(MessageStatusEnum.NOT_READ.key);
        messageService.update(message);
        //更新面试官消息  面试完成
        m = messageService.getMessageByOid(oid, UserEnum.INTERVIEWER.key);
        message.setMid(m.getMid());
        // ---------------------------求职者的首页，后续再更新这里 -----------------------------------------------
        message.setMessageUrl("");
        message.setStatusType(StatusTypeEnum.OVER_INTERVIEW.key);
        message.setMessageStatus(MessageStatusEnum.NOT_READ.key);
        messageService.update(message);
        return "redirect:/interview/evaluate/" + orderById.getOid();
    }

    /**
     * 求职者对面试官进行评论
     *
     * @param oid
     * @param model
     * @return
     */
    @RequestMapping("/interview/evaluate/{oid}")
    public String evaluationPage(@PathVariable("oid") Integer oid, Model model) {
        if (oid == null) {
            return "/";
        }
        Order order = ordersService.getOrderById(oid);
        Interviewer interviewer = interviewerService.getInterviewerById(order.getInterviewerId());
        model.addAttribute("order", order);
        model.addAttribute("interviewer", interviewer);
        return "front/interview/evaluation";
    }

    /**
     * 提交评论&更新订单表
     *
     * @param evaluation
     * @return
     */
    @RequestMapping("/interview/evaluateAction")
    public String evaluation(Evaluation evaluation, Model model) {
        evaluation.setCreateTime(new Date());
        logger.info(evaluation.toString());
        Integer eid = evaluationService.insert(evaluation);
        Order orderById = ordersService.getOrderById(evaluation.getOid());
        Order order = new Order();
        if (orderById != null) {
            order.setOrderNum(orderById.getOrderNum());
            order.setIsEvaluation(StatusEnum.YES.key);
            order.setEvaluationId(eid);
        }
        ordersService.updateOrder(order);
        model.addAttribute("iid", evaluation.getIid());
        // 更新消息
        Message m = messageService.getMessageByOid(evaluation.getOid(), UserEnum.FINDER.key);
        Message message = new Message();
        message.setMid(m.getMid());
        message.setUpdateTime(new Date());
        message.setStatusType(StatusTypeEnum.OVER_INTERVIEW.key);
        message.setMessageStatus(MessageStatusEnum.NOT_READ.key);
        message.setMessageUrl("/interviewer/" + evaluation.getIid());
        messageService.update(message);
        return "front/interview/tips";
    }

    /**
     * 面试官向求职者发起面试的页面
     *
     * @return
     */
    @RequestMapping("/interview/finder/{oid}")
    public String interviewToFinder(@PathVariable("oid") Integer oid, Model model) {
        Order order = ordersService.getOrderById(oid);
        model.addAttribute("order", order);
        Finder finder = null;
        if (order != null) {
            finder = finderService.getFinderById(order.getFinderId());
        }
        model.addAttribute("finder", finder);
        return "front/interview/interviewToFinder";
    }

    @RequestMapping("/interview/orderEnd")
    public String interviewerEnd(@RequestParam(required = false, value = "oid") Integer oid) {
        if (oid == null) {
            return "/";
        }
        // 更新订单
        Order o = ordersService.getOrderById(oid);
        Order order = new Order();
        order.setOrderNum(o.getOrderNum());
        // 面试官更新已经面试完成
        order.setIsInterviewed(StatusEnum.YES.key);
        ordersService.updateOrder(order);
        //更新求职者消息
        Message m = messageService.getMessageByOid(oid, UserEnum.FINDER.key);
        Message message = new Message();
        message.setMid(m.getMid());
        message.setStatusType(StatusTypeEnum.WAIT_ORDERED.key);
        message.setUpdateTime(new Date());
        messageService.update(message);
        //面试官更新消息
        m = messageService.getMessageByOid(oid, UserEnum.INTERVIEWER.key);
        message = new Message();
        message.setMid(m.getMid());
        message.setStatusType(StatusTypeEnum.OVER_INTERVIEW.key);
        message.setUpdateTime(new Date());
        message.setMessageStatus(MessageStatusEnum.NOT_READ.key);
        messageService.update(message);
        return "redirect:/interviewer/messageInterview";
    }
}
