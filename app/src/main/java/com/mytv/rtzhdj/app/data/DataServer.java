package com.mytv.rtzhdj.app.data;

import com.mytv.rtzhdj.app.data.entity.CommentEntity;
import com.mytv.rtzhdj.app.data.entity.CommunityEntity;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;
import com.mytv.rtzhdj.app.data.entity.GradeRankEntity;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.app.data.entity.QuestionaireEntitiy;
import com.mytv.rtzhdj.app.data.entity.TestingEntity;
import com.mytv.rtzhdj.app.data.entity.VoluteerServiceEntity;
import com.mytv.rtzhdj.app.data.entity.VoteEntrylEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class DataServer {

    private static final String HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK = "https://avatars1.githubusercontent.com/u/7698209?v=3&s=460";
    private static final String CYM_CHAD = "CymChad";

    private DataServer() {
    }

    /**
     * 获取 新闻模拟数据
     * @param lenth
     * @return
     */
    public static List<NewsEntity> getNewsData(int lenth) {
        List<NewsEntity> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            NewsEntity newsEntity = new NewsEntity();
            newsEntity.setId(i + 1);
            newsEntity.setTitle("Chad" + i);
            newsEntity.setDatetime("04/05/" + i);
            newsEntity.setStar_num(i * 2);
            newsEntity.setComment_num(i * 5);
            newsEntity.setImg_url("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            list.add(newsEntity);
        }
        return list;
    }

    /**
     * 获取 学习课件模拟数据
     * @param lenth
     * @return
     */
    public static List<CoursewareEntity> getCoursewareData(int lenth) {
        List<CoursewareEntity> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            CoursewareEntity coursewareEntity = new CoursewareEntity();
            coursewareEntity.setTitle("学习中共中央十九大精神 " + i);
            list.add(coursewareEntity);
        }
        return list;
    }

    /**
     * 获取 志愿服务模拟数据
     * @param lenth
     * @return
     */
    public static List<VoluteerServiceEntity> getVolunteerServiceData(int lenth) {
        List<VoluteerServiceEntity> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            VoluteerServiceEntity voluteerServiceEntity = new VoluteerServiceEntity();
            voluteerServiceEntity.setId(i + 1);
            voluteerServiceEntity.setTitle("志愿服务，从心开始 " + i);
            voluteerServiceEntity.setEnrollEndDate("04/05/" + i);
            voluteerServiceEntity.setImgUrl("http://pic117.nipic.com/file/20161213/24416158_165731241000_2.jpg");
//            voluteerServiceEntity.setStar_num(i * 3);
//            voluteerServiceEntity.setComment_num(i * 5);
            voluteerServiceEntity.setSignedup(i * 18);
            voluteerServiceEntity.setEnrollCount(i * 30);
            voluteerServiceEntity.setEnd(i % 2 == 0);
            list.add(voluteerServiceEntity);
        }
        return list;
    }

    /**
     * 获取 周边社区模拟数据
     * @param lenth
     * @return
     */
    public static List<CommunityEntity> getCommunityData(int lenth) {
        List<CommunityEntity> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            CommunityEntity communityEntity = new CommunityEntity();
            communityEntity.setId(i + 1);
            communityEntity.setTitle("学习中共中央十九大精神 " + i);
            communityEntity.setUrl("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=658124491,198552142&fm=27&gp=0.jpg");
            list.add(communityEntity);
        }
        return list;
    }

    /**
     * 获取 在线问卷模拟数据
     * @param lenth
     * @return
     */
    public static List<QuestionaireEntitiy> getQuestionaireData(int lenth) {
        List<QuestionaireEntitiy> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            QuestionaireEntitiy questionaireEntitiy = new QuestionaireEntitiy();
            questionaireEntitiy.setId(i + 1);
            questionaireEntitiy.setTitle("村镇两委干部教育培训现状调查问卷 " + i);
            questionaireEntitiy.setStart_time("2018-01-20");
            questionaireEntitiy.setEnd_time("2018-02-03");
            questionaireEntitiy.setStatus(i % 2);
            list.add(questionaireEntitiy);
        }
        return list;
    }

    /**
     * 获取 党员信息模拟数据
     * @param lenth
     * @return
     */
    public static List<PartyMemberEntity> getPartyMemberData(int lenth) {
        List<PartyMemberEntity> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            PartyMemberEntity partyMemberEntity = new PartyMemberEntity();
            partyMemberEntity.setId(i + 1);
            partyMemberEntity.setUserName("はたけ·カカシ 影の分身" + i);
            partyMemberEntity.setPhotourl("http://www.qq745.com/uploads/allimg/150217/1-15021G55929-50.jpg");
            partyMemberEntity.setDuty("党员");
            partyMemberEntity.setParty_branch("xxxxxxxx支部");
            partyMemberEntity.setMobile("189xxxx8888");
            partyMemberEntity.setTelephone("0816-2222888");
            partyMemberEntity.setEmergency_phone("158xxxx9999");
            partyMemberEntity.setOther_connections("无");
            partyMemberEntity.setAddress("四川省绵阳市涪城区xxxxxxxxxx");
            partyMemberEntity.setQq("978543241");
            partyMemberEntity.setEmail("xxxxxxxxx@163.com");
            list.add(partyMemberEntity);
        }
        return list;
    }

    /**
     * 获取 评论数据
     * @param lenth
     * @return
     */
    public static List<CommentEntity> getCommentData(int lenth) {
        List<CommentEntity> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setCommentId(i + 1);
            commentEntity.setUserName("はたけ·カカシ 影の分身" + i);
            commentEntity.setAvatarUrl("http://p.3761.com/pic/89371406508468.jpg");
            commentEntity.setCommentTime("2017-12-23 23:34:22");
            commentEntity.setCommentInfo("坚决拥护中国共产党！");
            commentEntity.setLikeNum(i);
            list.add(commentEntity);
        }
        return list;
    }

    /**
     * 获取 效果测评数据
     * @param lenth
     * @return
     */
    public static List<TestingEntity> getTestingData(int lenth) {
        List<TestingEntity> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            TestingEntity testingEntity = new TestingEntity();
            testingEntity.setId(i + 1);
            testingEntity.setTitle("党的十九大精神学习测试（一）");
            testingEntity.setScores(0);
            testingEntity.setTotal_num(3);
            testingEntity.setLast_num(2);
            testingEntity.setStart_time("2017-12-11");
            testingEntity.setEnd_time("2017-12-21");
            testingEntity.setLast_time("暂无");
            list.add(testingEntity);
        }
        return list;
    }

    /**
     * 获取 成绩排行数据
     * @param lenth
     * @return
     */
    public static List<GradeRankEntity.GradeRank> getGradeRankData(int lenth) {
        List<GradeRankEntity.GradeRank> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            /*GradeRankEntity.GradeRank rankEntity = new GradeRankEntity.GradeRank();
            rankEntity.setUserID(i + 1);
            rankEntity.setUserName("はたけ·カカシ");
//            rankEntity.setImg_url("http://p.3761.com/pic/89371406508468.jpg");
            rankEntity.setPublishmentSystemName("中共阿坝州壤塘县xxxxxx第一党支部");
//            rankEntity.setTime("2017-12-11 09:43");
//            rankEntity.setRank(i + 1);
            list.add(rankEntity);*/
        }
        return list;
    }

    /**
     * 获取 投票作品数据
     * @param lenth
     * @return
     */
    public static List<VoteEntrylEntity> getVoteEntryData(int lenth) {
        List<VoteEntrylEntity> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            VoteEntrylEntity voteEntrylEntity = new VoteEntrylEntity();
            voteEntrylEntity.setId(i + 1);
            voteEntrylEntity.setTitle("《无题》 作者: 佚名");
            voteEntrylEntity.setImgUrl("http://p.3761.com/pic/89371406508468.jpg");
//            voteEntrylEntity.setContent("作品描述作品描述作品描述作品描述作品描述作品描述");
//            voteEntrylEntity.setDatetime("2017-12-11 09:43");
            voteEntrylEntity.setVoteNum(40 - i);
            voteEntrylEntity.setRanking(i + 1);
            list.add(voteEntrylEntity);
        }
        return list;
    }

//    public static List<Status> addData(List list, int dataSize) {
//        for (int i = 0; i < dataSize; i++) {
//            Status status = new Status();
//            status.setUserName("Chad" + i);
//            status.setCreatedAt("04/05/" + i);
//            status.setRetweet(i % 2 == 0);
//            status.setUserAvatar("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
//            status.setText("Powerful and flexible RecyclerAdapter https://github.com/CymChad/BaseRecyclerViewAdapterHelper");
//            list.add(status);
//        }
//
//        return list;
//    }
//
//    public static List<MySection> getSampleData() {
//        List<MySection> list = new ArrayList<>();
//        list.add(new MySection(true, "Section 1", true));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(true, "Section 2", false));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(true, "Section 3", false));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(true, "Section 4", false));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(true, "Section 5", false));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        return list;
//    }
//
//    public static List<MultipleItem> getMultipleItemData() {
//        List<MultipleItem> list = new ArrayList<>();
//        for (int i = 0; i <= 4; i++) {
//            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
//            list.add(new MultipleItem(MultipleItem.TEXT, MultipleItem.TEXT_SPAN_SIZE, CYM_CHAD));
//            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE));
//            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
//            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
//        }
//
//        return list;
//    }

//    public static List<MultipleItem> getMultipleChildView() {
//        List<MultipleItem> list = new ArrayList<>();
//        for (int i = 0; i <= 4; i++) {
//            list.add(new ClickEntity(ClickEntity.CLICK_ITEM_VIEW, MultipleItem.TEXT_SPAN_SIZE, CYM_CHAD));
//            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.BIG_IMG_SPAN_SIZE));
//            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
//            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
//            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
//        }
//
//        return list;
//    }


}
