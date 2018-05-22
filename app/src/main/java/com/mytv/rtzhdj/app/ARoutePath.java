package com.mytv.rtzhdj.app;

/**
 * ARoute 路径
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-18
 * @update 2018-1-20 新增 Start/Login/Register/Settings
 *                        News/NewsEducation/NewsPoverty/NewsDetail/NewsLive
 *                        Topic/TopicDetail/Search
 *                        StudyRecord/StudyCourseware
 *                        NewsSimple/EffectEvaluation
 *                        CompulsoryCourse/ElectiveCourse/CourseDetail 页面path
 *         2018-1-21 新增 MyTask/DoubleReporting/MyReporting
 *                        Organizational/VolunteerService
 *                        ReportCommunity/SurroundingCommunity
 *                        WishWall/Donate/MyDonation
 *                        QuestionOnline/VoteOnline/EventDetails/SignIn 页面path
 *         2018-2-6  新增 NewsClean 页面path
 *         2018-2-7  新增 PartyKnowledge/PartyHistory 页面path
 *         2018-2-8  新增 MyReceive 页面path
 *         2018-2-9  新增 MyReceiveWish 页面path
 *         2018-2-11 新增 PartyMember/MembershipCredentials/ConnectionTransfer 页面path
 *         2018-2-26 新增 Comment 页面path
 *         2018-2-28 新增 UpdatePwd/UpdateInfo/Webview/Feedback/BindingMobile
 *                        GradeRank 页面path
 *         2018-3-5  新增 NewsVideoDetail 页面path
 *         2018-3-15 新增 CourseVideoDetail 页面path
 *         2018-3-21 新增 FindPwd/GetVertifyCode 页面path
 *         2018-3-22 新增 SetPwd 页面path
 *         2018-3-23 新增 Guide 页面path
 *         2018-3-24 新增 SendMsg/MyMsg/PartyMemberDetail 页面path
 *         2018-3-26 新增 VoteDetail/VoteBrief/VoteResult/VoteEntryDetail 页面path
 *         2018-3-28 新增 VolunteerServiceDetail 页面path
 *         2018-5-4  新增 MyJoin 页面path
 *         2018-5-8  新增 NewsAll 页面path
 *         2018-5-10 新增 ScoresDetails 页面path
 *         2018-5-15 新增 Questionaire 页面path
 *         2018-5-22 新增 QuestionaireSurvey 页面path
 */
public interface ARoutePath {

    // Main
    String PATH_MAIN = "/rtdzdj/main";
    // Start
    String PATH_START = "/rtdzdj/start";
    // Login
    String PATH_LOGIN = "/rtdzdj/login";
    // Register
    String PATH_REGISTER = "/rtdzdj/register";
    // Settings
    String PATH_SETTINGS = "/rtdzdj/settings";
    // News
    String PATH_NEWS = "/rtdzdj/news";
    // NewsEducation
    String PATH_NEWS_EDUCATION = "/rtdzdj/news_education";
    // NewsClean
    String PATH_NEWS_CLEAN = "/rtdzdj/news_clean";
    // NewsPoverty
    String PATH_NEWS_POVERTY = "/rtdzdj/news_poverty";
    // NewsDetail
    String PATH_NEWS_DETAIL = "/rtdzdj/news_detail";
    // NewsLive
    String PATH_NEWS_LIVE = "/rtdzdj/news_live";
    // NewsCommon
    String PATH_NEWS_COMMON = "/rtdzdj/news_common";
    // Topic
    String PATH_TOPIC = "/rtdzdj/topic";
    // TopicDetail
    String PATH_TOPIC_DETAIL = "/rtdzdj/topic_detail";
    // Search
    String PATH_SEARCH = "/rtdzdj/search";
    // StudyRecord
    String PATH_STUDY_RECORD = "/rtdzdj/study_record";
    // StudyCourseware
    String PATH_STUDY_COURSEWARE = "/rtdzdj/study_courseware";
    // NewsSimple
    String PATH_NEWS_SIMPLE = "/rtdzdj/news_simple";
    // EffectEvaluation
    String PATH_EFFECT_EVALUATION = "/rtdzdj/effect_evaluation";
    // CompulsoryCourse
    String PATH_COMPULSORY_COURSE = "/rtdzdj/compulsory_course";
    // ElectiveCourse
    String PATH_ELECTIVE_COURSE = "/rtdzdj/elective_course";
    // CourseDetail
    String PATH_COURSE_DETAIL = "/rtdzdj/course_detail";
    // MyTask
    String PATH_MY_TASK = "/rtdzdj/my_task";
    // DoubleReporting
    String PATH_DOUBLE_REPORTING = "/rtdzdj/double_reporting";
    // MyReporting
    String PATH_MY_REPORTING = "/rtdzdj/my_reporting";
    // Organizational
    String PATH_ORGANIZATIONAL = "/rtdzdj/organizational";
    // VolunteerService
    String PATH_VOLUNTEER_SERVICE = "/rtdzdj/volunteer_service";
    // ReportCommunity
    String PATH_REPORT_COMMUNITY = "/rtdzdj/report_community";
    // SurroundingCommunity
    String PATH_SURROUNDING_COMMUNITY = "/rtdzdj/surrounding_community";
    // WishWall
    String PATH_WISH_WALL = "/rtdzdj/wish_wall";
    // Donate
    String PATH_DONATE = "/rtdzdj/donate";
    // MyDonation
    String PATH_MY_DONATION = "/rtdzdj/my_donation";
    // QuestionOnline
    String PATH_QUESTION_ONLINE = "/rtdzdj/question_online";
    // VoteOnline
    String PATH_VOTE_ONLINE = "/rtdzdj/vote_online";
    // EventDetails
    String PATH_EVENT_DETAILS = "/rtdzdj/event_details";
    // SignIn
    String PATH_SIGN_IN = "/rtdzdj/sign_in";
    // PartyKnowledge
    String PATH_PARTY_KNOWLEDGE = "/rtdzdj/party_knowledge";
    // PartyHistory
    String PATH_PARTY_HISTORY = "/rtdzdj/party_history";
    // MyReceive
    String PATH_MY_RECEIVE = "/rtdzdj/my_receive";
    // MyReceiveWish
    String PATH_MY_RECEIVE_WISH = "/rtdzdj/my_receive_wish";
    // PartyMember
    String PATH_PARTY_MEMBER = "/rtdzdj/party_member";
    // MembershipCredentials
    String PATH_MEMBERSHIP_CREDENTIALS = "/rtdzdj/membership_credentials";
    // ConnectionTransfer
    String PATH_CONNECTION_TRANSFER = "/rtdzdj/connection_transfer";
    // Comment  评论列表
    String PATH_COMMENT = "/rtdzdj/comment";
    // UpdatePwd  更新密码
    String PATH_UPDATE_PWD = "/rtdzdj/update_pwd";
    // UpdateInfo  更新信息
    String PATH_UPDATE_INFO = "/rtdzdj/update_info";
    // WEBVIEW  WAP加载页
    String PATH_WEBVIEW = "/rtdzdj/webview";
    // Feedback  意见反馈
    String PATH_FEEDBACK = "/rtdzdj/feedback";
    // BindingMobile  绑定手机
    String PATH_BINDING_MOBILE = "/rtdzdj/binding_mobile";
    // GradeRank  成绩排行
    String PATH_GRADE_RANK = "/rtdzdj/grade_rank";
    // NewsVideoDetail 视频新闻
    String PATH_NEWS_VIDEO_DETAIL = "/rtdzdj/news_video_detail";
    // CourseVideoDetail 视频课程
    String PATH_COURSE_VIDEO_DETAIL = "/rtdzdj/course_video_detail";
    // FindPwd 找回密码
    String PATH_FIND_PWD = "/rtdzdj/find_pwd";
    // GetVertifyCode 获取验证码
    String PATH_GET_VERTIFY_CODE = "/rtdzdj/get_vertify_code";
    // SetPWd 设置密码
    String PATH_SET_PWD = "/rtdzdj/set_pwd";
    // Guide 引导
    String PATH_GUIDE = "/rtdzdj/guide";
    // SendMsg 发送私信
    String PATH_SEND_MSG = "/rtdzdj/send_msg";
    // MyMsg 我的私信
    String PATH_MY_MSG = "/rtdzdj/my_msg";
    // PartyMemberDetail 党员详情
    String PATH_PARTY_MEMBER_DETAIL = "/rtdzdj/party_member_detail";
    // VoteDetail 投票详情
    String PATH_VOTE_DETAIL = "/rtdzdj/vote_detail";
    // VoteBrief 投票简介
    String PATH_VOTE_BRIEF = "/rtdzdj/vote_brief";
    // VoteResult 投票结果
    String PATH_VOTE_RESULT = "/rtdzdj/vote_result";
    // VoteEntryDetail 投票作品详情
    String PATH_VOTE_ENTRY_DETAIL = "/rtdzdj/vote_entry_detail";
    // VolunteerServiceDetail 志愿服务详情
    String PATH_VOLUNTEER_SERVICE_DETAIL = "/rtdzdj/volunteer_service_detail";
    // WishDetail 愿望详情
    String PATH_WISH_DETAIL = "/rtdzdj/wish_detail";
    // MyJoin 我要参与
    String PATH_MY_JOIN = "/rtdzdj/my_join";
    // NewsAll 带"全部"二级页面
    String PATH_NEWS_ALL = "/rtdzdj/news_all";
    // ScoresDetails 积分明细
    String PATH_SCORES_DETAILS = "/rtdzdj/scores_details";
    // Questionaire 效果评测问卷调查页面
    String PATH_QUESTIONAIRE = "/rtdzdj/questionaire";
    // QuestionaireSurvey 在线问卷调查页面
    String PATH_QUESTIONAIRE_SURVEY = "/rtdzdj/questionaire_survey";

}
