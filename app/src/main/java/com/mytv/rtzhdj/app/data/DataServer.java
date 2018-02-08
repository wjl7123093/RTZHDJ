package com.mytv.rtzhdj.app.data;

import com.mytv.rtzhdj.app.data.entity.CommunityEntity;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;
import com.mytv.rtzhdj.app.data.entity.NewsEntity;
import com.mytv.rtzhdj.app.data.entity.VoluteerServiceEntity;

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
            coursewareEntity.setId(i + 1);
            coursewareEntity.setTitle("学习中共中央十九大精神 " + i);
            coursewareEntity.setDatetime("04/05/" + i);
            coursewareEntity.setType(i / 3 + 1);
            coursewareEntity.setScores(i * 2);
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
            voluteerServiceEntity.setDeadtime("04/05/" + i);
            voluteerServiceEntity.setUrl("http://pic117.nipic.com/file/20161213/24416158_165731241000_2.jpg");
            voluteerServiceEntity.setStar_num(i * 3);
            voluteerServiceEntity.setComment_num(i * 5);
            voluteerServiceEntity.setJoin_num(i * 18);
            voluteerServiceEntity.setTotal_num(i * 30);
            voluteerServiceEntity.setStatus(i / 2);
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
