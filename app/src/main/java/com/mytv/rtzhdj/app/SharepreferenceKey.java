package com.mytv.rtzhdj.app;

/**
 * Sharepreference Keys
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-18
 * @update 2018-4-21
 */
public interface SharepreferenceKey {

    // Access_Token
    String KEY_ACCESS_TOKEN = "access_token";
    // LoginUser
    String KEY_LOGIN_USER = "login_user";
    // UserId
    String KEY_USER_ID = "user_id";     // UserId
    // IsFirstIn
    String KEY_IS_FIRST_IN = "is_first_in";   // -1 首次进入，1 非首次
    // IsLogin
    String KEY_IS_LOGIN = "is_login";   // 1 登录，0 注销
    // LoginUserDetail
    String KEY_LOGIN_USER_DETAIL = "login_user_detail";

    // PublishmentSystemId
    String KEY_PUBLISHMENT_SYSTEM_ID = "publishment_system_id"; // 报道社区ID
    // PublishmentSystemName
    String KEY_PUBLISHMENT_SYSTEM_NAME = "publishment_system_name"; // 报道社区名称
    // UserName
    String KEY_LOGIN_USER_NAME = "login_user_name"; // 用户名称
    // PhotoUrl
    String KEY_LOGIN_HEADER_URL = "login_header_url"; // 用户头像
    // Integral
    String KEY_LOGIN_INTEGRAL = "login_integral"; // 当前总积分
    // AddIntegral
    String KEY_LOGIN_ADD_INTEGRAL = "login_add_integral"; // 当前增加积分
    // PositiveEnergyValue
    String KEY_POSITIVE_ENERGY_VALUE = "login_positive_energy_value"; // 正能量值
    // UserTypeId
    String KEY_LOGIN_USER_TYPE = "login_user_type"; // 用户类型
    // Ranking
    String KEY_LOGIN_USER_RANK = "login_user_rank"; // 用户排名
    // ParticipantsNum
    String KEY_LOGIN_USER_PARTIN_TIMES = "login_user_partin_times"; // 参会次数
    // StudyNum
    String KEY_LOGIN_USER_STUDY_TIMES = "login_user_study_times"; // 学习次数
    // ActivityNum
    String KEY_LOGIN_USER_ACTIVITY_TIMES = "login_user_activity_times"; // 活动次数

}
