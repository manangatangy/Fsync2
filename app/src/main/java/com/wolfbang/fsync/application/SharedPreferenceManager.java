/**
 * (C) 2015. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 * copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 * documentation may be reproduced in any form by any means without prior written authorization of National Australia
 * Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 */

package com.wolfbang.fsync.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Created by Aniruddh Fichadia on 22/10/2015.
 */
public class SharedPreferenceManager {

    // Init property
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
    @Deprecated
    private static final String KEY_INIT_PROPERTY_SIGNATURE = "key_init_property_signature";
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
    @Deprecated
    private static final String KEY_INIT_PROPERTY_SIGNED_CONTENT = "key_init_property_signed_content";
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
    @Deprecated
    private static final String KEY_INIT_PROPERTY_UNSIGNED_CONTENT = "key_init_property_unsigned_content";

    // Init version
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
    @Deprecated
    private static final String KEY_INIT_VERSION_SIGNATURE = "key_init_version_signature";
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
    @Deprecated
    private static final String KEY_INIT_VERSION_SIGNED_CONTENT = "key_init_version_signed_content";
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
    @Deprecated
    private static final String KEY_INIT_VERSION_UNSIGNED_CONTENT = "key_init_version_unsigned_content";
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
    @Deprecated
    private static final String KEY_INIT_VERSION_LAST_CHECK_UPTIME = "key_init_version_last_check_uptime";

    // App
    private static final String KEY_APP_VERSIONCODE = "key_app_versioncode";

    // Find us
    private static final String KEY_HAS_VISITED_FIND_US = "key_has_visited_find_us";

    // Feedback form
    private static final String KEY_PILOT_FEEDBACK_PROVIDED = "key_pilot_feedback";

    private static final String KEY_NO_FEES_ATM_MSG_DISPLAYED = "key_no_fees_atm_msg";

    // OTN
    private static final String KEY_OTN_GEOFENCES_ADDED = "key_otn_geofence_added";
    private static final String KEY_OTN_TIMESTAMP_LAST_DOMESTIC_NOTIFICATION =
            "key_otn_timestamp_last_domestic_notification";
    private static final String KEY_OTN_DOMESTIC_NOTIFICATION_CLICKED = "key_otn_domestic_notification_clicked";
    private static final String KEY_OTN_LAST_LOCATION_IS_IN_AUSTRALIA = "key_otn_last_location_in_australia";

    // travel tools
    private static final String KEY_LAST_KNOWN_COUNTRY = "key_last_known_location";

    // Rewards
    private static final String KEY_REWARDS_URL_USE_SIT = "key_rewards_url_use_sit";

    // What's new
    private static final String KEY_LAST_WHATS_NEW_VERSION_DISPLAYED = "key_whats_new";

    // Pre select user after migration
    private static final String KEY_PRE_SELECT_USER_REQUIRED = "key_pre_select_user_required";

    private static SharedPreferenceManager sInstance;

    private final SharedPreferences mSharedPreferences;

//    @Deprecated
//    public static SharedPreferenceManager getInstance() {
//        return getInstance(MyApplication.getInstance().getApplicationContext());
//    }

    public static SharedPreferenceManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPreferenceManager(context);
        }

        return sInstance;
    }

    private SharedPreferenceManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    // App
    public int getAppVersionCode() {
        return mSharedPreferences.getInt(KEY_APP_VERSIONCODE, 0);
    }

    public void setAppVersionCode(int versionCode) {
        mSharedPreferences.edit().putInt(KEY_APP_VERSIONCODE, versionCode)
                          .apply();
    }

    // region Init property

//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
    @Deprecated
    public String getInitPropertySignature(String initPropertyEndPoint) {
        final String signature = mSharedPreferences.getString(KEY_INIT_PROPERTY_SIGNATURE + initPropertyEndPoint, null);
//        Timber.tag(AppSettings.INIT_PROPERTY_TAG).i("signature get = " + signature);
        return signature;
    }

//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
    @Deprecated
    public void setInitPropertySignature(String initPropertyEndPoint, String signature) {
//        Timber.tag(AppSettings.INIT_PROPERTY_TAG).i("signature set = " + signature);
        mSharedPreferences.edit().putString(KEY_INIT_PROPERTY_SIGNATURE + initPropertyEndPoint,
                                            signature).apply();
    }

//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
//    @Deprecated
//    public String getInitPropertySignedContent(String initPropertyEndPoint) {
//        final String signedContent = mSharedPreferences.getString(KEY_INIT_PROPERTY_SIGNED_CONTENT +
//                                                                          initPropertyEndPoint, null);
//        Timber.tag(AppSettings.INIT_PROPERTY_TAG).i("signedContent get = " + signedContent);
//        return signedContent;
//    }
//
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
//    @Deprecated
//    public void setInitPropertySignedContent(String initPropertyEndPoint, String content) {
//        Timber.tag(AppSettings.INIT_PROPERTY_TAG).i("signedContent set = " + content);
//        mSharedPreferences.edit().putString(KEY_INIT_PROPERTY_SIGNED_CONTENT +
//                                                    initPropertyEndPoint, content).apply();
//    }
//
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
//    @Deprecated
//    public String getInitPropertyUnsignedContent(String initPropertyEndPoint) {
//        final String unsignedContent = mSharedPreferences.getString(KEY_INIT_PROPERTY_UNSIGNED_CONTENT +
//                                                                            initPropertyEndPoint, null);
//        Timber.tag(AppSettings.INIT_PROPERTY_TAG).i("unsignedContent get = " + unsignedContent);
//        return unsignedContent;
//    }
//
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
//    @Deprecated
//    public void setInitPropertyUnsignedContent(String initPropertyEndPoint, String content) {
//        Timber.tag(AppSettings.INIT_PROPERTY_TAG).i("unsignedContent set = " + content);
//        mSharedPreferences.edit().putString(KEY_INIT_PROPERTY_UNSIGNED_CONTENT +
//                                                    initPropertyEndPoint, content)
//                          .apply();
//    }
//    // endregion
//
//
//    // region Init version
//
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
//    @Deprecated
//    public String getInitVersionSignature(String initVersionEndPoint) {
//        final String signature = mSharedPreferences.getString(KEY_INIT_VERSION_SIGNATURE + initVersionEndPoint, null);
//        Timber.tag(AppSettings.INIT_VERSION_TAG).i("signature get = " + signature);
//        return signature;
//    }
//
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
//    @Deprecated
//    public void setInitVersionSignature(String initVersionEndPoint, String signature) {
//        Timber.tag(AppSettings.INIT_VERSION_TAG).i("signature set = " + signature);
//        mSharedPreferences.edit().putString(KEY_INIT_VERSION_SIGNATURE + initVersionEndPoint,
//                                            signature).apply();
//    }
//
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
//    @Deprecated
//    public String getInitVersionSignedContent(String initVersionEndPoint) {
//        final String signedContent = mSharedPreferences.getString(KEY_INIT_VERSION_SIGNED_CONTENT +
//                                                                          initVersionEndPoint, null);
//        Timber.tag(AppSettings.INIT_VERSION_TAG).i("signedContent get = " + signedContent);
//        return signedContent;
//    }
//
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
//    @Deprecated
//    public void setInitVersionSignedContent(String initVersionEndPoint, String content) {
//        Timber.tag(AppSettings.INIT_VERSION_TAG).i("signedContent set = " + content);
//        mSharedPreferences.edit().putString(KEY_INIT_VERSION_SIGNED_CONTENT +
//                                                    initVersionEndPoint, content).apply();
//    }
//
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
//    @Deprecated
//    public String getInitVersionUnsignedContent(String initVersionEndPoint) {
//        final String unsignedContent = mSharedPreferences.getString(KEY_INIT_VERSION_UNSIGNED_CONTENT +
//                                                                            initVersionEndPoint, null);
//        Timber.tag(AppSettings.INIT_VERSION_TAG).i("unsignedContent get = " + unsignedContent);
//        return unsignedContent;
//    }
//
//    /** @deprecated Replaced in {@link au.com.nab.mobile.framework.DevSwitch#INIT_V2} implementation */
//    @Deprecated
//    public void setInitVersionUnsignedContent(String initVersionEndPoint, String content) {
//        Timber.tag(AppSettings.INIT_VERSION_TAG).i("unsignedContent set = " + content);
//        mSharedPreferences.edit().putString(KEY_INIT_VERSION_UNSIGNED_CONTENT +
//                                                    initVersionEndPoint, content)
//                          .apply();
//    }
    // endregion


    // region Find us
    public boolean hasVisitedFindUsScreen() {
        return mSharedPreferences.getBoolean(KEY_HAS_VISITED_FIND_US, false);
    }

    public void setHasVisitedFindUsScreen(boolean hasVisited) {
        mSharedPreferences.edit().putBoolean(KEY_HAS_VISITED_FIND_US, hasVisited).apply();
    }
    // endregion


    // region Feedback form
    public boolean hasPilotFeedbackProvided() {
        return mSharedPreferences.getBoolean(KEY_PILOT_FEEDBACK_PROVIDED, false);
    }

    public void setPilotFeedbackProvided(boolean feedbackProvided) {
        mSharedPreferences.edit().putBoolean(KEY_PILOT_FEEDBACK_PROVIDED, feedbackProvided).apply();
    }
    // endregion


    // region OTN
    public boolean anyOtnGeofenceAdded() {
        return mSharedPreferences.getBoolean(KEY_OTN_GEOFENCES_ADDED, false);
    }

    public void setOtnGeofenceAdded(boolean added) {
        mSharedPreferences.edit().putBoolean(KEY_OTN_GEOFENCES_ADDED, added).apply();
    }

    public long getLastOtnDomesticNotificationTime() {
        return mSharedPreferences.getLong(KEY_OTN_TIMESTAMP_LAST_DOMESTIC_NOTIFICATION, 0);
    }

    public void setNewOtnDomesticNotificationTime(long timestamp) {
        mSharedPreferences.edit().putLong(KEY_OTN_TIMESTAMP_LAST_DOMESTIC_NOTIFICATION, timestamp).apply();
    }

    public boolean getOtnDomesticNotificationClicked() {
        return mSharedPreferences.getBoolean(KEY_OTN_DOMESTIC_NOTIFICATION_CLICKED, false);
    }

    public void setOtnDomesticNotificationClicked(boolean clicked) {
        mSharedPreferences.edit().putBoolean(KEY_OTN_DOMESTIC_NOTIFICATION_CLICKED, clicked).apply();
    }

    public boolean getOtnLastLocationIsInAustralia() {
        return mSharedPreferences.getBoolean(KEY_OTN_LAST_LOCATION_IS_IN_AUSTRALIA, true);
    }

    public void setLastLocationIsInAustralia(boolean inside) {
        mSharedPreferences.edit().putBoolean(KEY_OTN_LAST_LOCATION_IS_IN_AUSTRALIA, inside).apply();
    }

//    public void setLastKnownCountry(CountryDetails country) {
//        mSharedPreferences.edit().putString(KEY_LAST_KNOWN_COUNTRY, new Gson().toJson(country)).apply();
//    }
//
//    public CountryDetails getLastKnownCountry() {
//        String json = mSharedPreferences.getString(KEY_LAST_KNOWN_COUNTRY, "");
//        try {
//            return new Gson().fromJson(json, CountryDetails.class);
//        } catch (JsonSyntaxException e) {
//            return null;
//        }
//    }

    public void setRewardsUseSitUrl(boolean useSit) {
        mSharedPreferences.edit().putBoolean(KEY_REWARDS_URL_USE_SIT, useSit).apply();
    }

    public void resetOtnNotificationData() {
        setNewOtnDomesticNotificationTime(0);
        setOtnGeofenceAdded(false);
        setLastLocationIsInAustralia(true);
        setOtnDomesticNotificationClicked(false);
    }
    // endregion


    // General Operations
    private void removePreference(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    public void setNoFeesAtmMsgDismissed(boolean noFeesAtmMsgDismissed) {
        mSharedPreferences.edit().putBoolean(KEY_NO_FEES_ATM_MSG_DISPLAYED, noFeesAtmMsgDismissed)
                          .apply();
    }

    public boolean hasNoFeesAtmMsgDismissed() {
        return mSharedPreferences.getBoolean(KEY_NO_FEES_ATM_MSG_DISPLAYED, false);
    }

    // region What's new
    public void setLastVersionWhatsNewDisplayed(int lastVersion) {
        mSharedPreferences.edit().putInt(KEY_LAST_WHATS_NEW_VERSION_DISPLAYED, lastVersion)
                          .apply();
    }

    public int getLastVersionWhatsNewDisplayed() {
        return mSharedPreferences.getInt(KEY_LAST_WHATS_NEW_VERSION_DISPLAYED, -1);
    }

    public void setPreSelectUserRequired(boolean required) {
        if (required) {
            mSharedPreferences.edit().putBoolean(KEY_PRE_SELECT_USER_REQUIRED, true).apply();
        } else {
            mSharedPreferences.edit().remove(KEY_PRE_SELECT_USER_REQUIRED).apply();
        }
    }

    public boolean isPreSelectUserRequired() {
        return mSharedPreferences.getBoolean(KEY_PRE_SELECT_USER_REQUIRED, false);
    }

    public void setInitVersionLastCheckUptime(long uptimeMillis) {
        mSharedPreferences.edit().putLong(KEY_INIT_VERSION_LAST_CHECK_UPTIME, uptimeMillis).apply();
    }

    public long getInitVersionLastCheckUptime() {
        return mSharedPreferences.getLong(KEY_INIT_VERSION_LAST_CHECK_UPTIME, 0);
    }
    // endregion
}
