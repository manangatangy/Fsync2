package com.lsmvp.view;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;

/**
 * Created by Jeremy Le on 7/09/2015.
 */
public class DialogFactory {

//    public static CustomAlertDialogBuilder createErrorDialogBuilder(Context context, int titleId, int messageId) {
//        return createErrorDialogBuilder(context, titleId, messageId, null);
//    }
//
//    public static CustomAlertDialogBuilder createErrorDialogBuilder(Context context, int titleId, int messageId,
//                                                                    @Nullable
//                                                                            DialogInterface.OnDismissListener onDismissListener) {
//        return new CustomAlertDialogBuilder(context)
//                .setImageId(R.drawable.ic_alert_exclaim_medium)
//                .setTitleId(titleId)
//                .setContentId(messageId)
//                .setPositiveButtonLabelId(R.string.btn_ok)
//                .setOnDismissListener(onDismissListener);
//    }
//
//    public static CustomAlertDialogBuilder createCancellableDialog(Context context, TitleAndMessage titleAndMessage) {
//        return createErrorDialogBuilder(context, titleAndMessage.titleResId, titleAndMessage.messageResId);
//    }
//
//    public static CustomAlertDialogBuilder createCancellableDialog(Context context, TitleAndMessage titleAndMessage,
//                                                                   int positiveButtonResId,
//                                                                   DialogInterface.OnClickListener
//                                                                           positiveBtnClickListener,
//                                                                   int negativeButtonResId,
//                                                                   DialogInterface.OnClickListener
//                                                                           negativeBtnClickListener) {
//        return createCancellableDialog(context, titleAndMessage.titleResId, titleAndMessage.messageResId,
//                                       positiveButtonResId, positiveBtnClickListener,
//                                       negativeButtonResId, negativeBtnClickListener);
//    }


    /**
     * If ye desired no title, pass it as -1
     *
     * @param context
     *         - Activity context, compulsory
     * @param titleId
     *         - title resource id, compulsory
     * @param messageId
     *         - message resource id, compulsory
     * @param positiveButtonResId
     *         - positive button title id, optional. If not specified (equals -1) the click listener will be ignored
     * @param positiveBtnClickListener
     *         - positive button click listener, optional
     * @param negativeButtonResId
     *         - negative button title id, optional. If not specified (equals -1) the click listener will be ignored
     * @param negativeBtnClickListener
     *         - negative button click listener, optional
     *
     * @see #createCancellableDialog(Context, int, int, int, OnClickListener, int, OnClickListener, OnDismissListener)
     */
//    public static CustomAlertDialogBuilder createCancellableDialog(
//            @NotNull Context context,
//            int titleId, int messageId,
//            int positiveButtonResId, DialogInterface.OnClickListener positiveBtnClickListener,
//            int negativeButtonResId, DialogInterface.OnClickListener negativeBtnClickListener) {
//        return createCancellableDialog(context, titleId, messageId,
//                                       positiveButtonResId, positiveBtnClickListener,
//                                       negativeButtonResId, negativeBtnClickListener,
//                                       null);
//    }

    /**
     * If ye desired no title, pass it as -1
     *
     * @param context
     *         - Activity context, compulsory
     * @param titleId
     *         - title resource id, compulsory
     * @param messageId
     *         - message resource id, compulsory
     * @param positiveButtonResId
     *         - positive button title id, optional. If not specified (equals -1) the click listener will be ignored
     * @param positiveBtnClickListener
     *         - positive button click listener, optional
     * @param negativeButtonResId
     *         - negative button title id, optional. If not specified (equals -1) the click listener will be ignored
     * @param negativeBtnClickListener
     *         - negative button click listener, optional
     * @param onDismissListener
     *         - dialog dismiss listener, optional
     *
     * @return
     */
//    public static CustomAlertDialogBuilder createCancellableDialog(
//            @NotNull Context context,
//            int titleId, int messageId,
//            int positiveButtonResId, OnClickListener positiveBtnClickListener,
//            int negativeButtonResId, OnClickListener negativeBtnClickListener,
//            @Nullable OnDismissListener onDismissListener) {
//        CustomAlertDialogBuilder builder = new CustomAlertDialogBuilder(context)
//                .setImageId(R.drawable.ic_alert_exclaim_medium)
//                .setTitleId(titleId)
//                .setContentId(messageId);
//        if (positiveButtonResId != -1) {
//            builder.setPositiveButtonLabelId(positiveButtonResId);
//            // listener may be null but we always want to auto dismiss
//            builder.setPositiveButtonOnClickListener(positiveBtnClickListener, true);
//        }
//        if (negativeButtonResId != -1) {
//            builder.setNegativeButtonLabelId(negativeButtonResId);
//            builder.setNegativeButtonOnClickListener(negativeBtnClickListener, true);
//        }
//        if (onDismissListener != null) {
//            builder.setOnDismissListener(onDismissListener);
//        }
//        return builder;
//    }

    /**
     * If no title, pass it as -1
     *
     * @param context
     *         - Activity context, compulsory
     * @param titleId
     *         - title resource id, compulsory
     * @param messageId
     *         - message resource id, compulsory
     * @param positiveButtonResId
     *         - positive button title id, optional. If not specified (equals -1) the click listener will be ignored
     * @param positiveBtnClickListener
     *         - positive button click listener, optional
     * @param autoDismissEnabled
     *         - whether dialog should be dismissed when the button is clicked
     * @param onDismissListener
     *         - dialog dismiss listener, optional
     *
     * @return CustomAlertDialogBuilder
     */
//    public static CustomAlertDialogBuilder createSingleActionDialog(
//            @NonNull Context context,
//            int titleId, int messageId,
//            int positiveButtonResId, @Nullable OnClickListener positiveBtnClickListener,
//            boolean autoDismissEnabled,
//            @Nullable OnDismissListener onDismissListener) {
//        CustomAlertDialogBuilder builder = new CustomAlertDialogBuilder(context)
//                .setImageId(R.drawable.ic_alert_exclaim_medium)
//                .setTitleId(titleId)
//                .setContentId(messageId);
//
//        if (positiveButtonResId != -1) {
//            builder.setPositiveButtonLabelId(positiveButtonResId);
//            builder.setPositiveButtonOnClickListener(positiveBtnClickListener, autoDismissEnabled);
//        }
//        if (onDismissListener != null) {
//            builder.setOnDismissListener(onDismissListener);
//        }
//        return builder;
//    }

    /**
     * Show network error dialog. This offers an additional settings button which enabled user to go to settings and
     * configure wifi/mobile data
     * @param context context object
     * @param positiveListener additional positive onclick listener
     * @param negativeListener additional negative onclick listener
     * @return the dialog
     */
//    public static CustomAlertDialogBuilder createNetworkErrorDialog(final Context context,
//                                                                    final OnClickListener positiveListener,
//                                                                    final OnClickListener negativeListener) {
//        return new CustomAlertDialogBuilder(context)
//                .setCancelable(false)
//                .setImageId(R.drawable.ic_alert_exclaim_medium)
//                .setTitleId(R.string.me0006_title)
//                .setContentId(R.string.me0006)
//                .setPositiveButtonLabelId(R.string.btn_go_to_settings)
//                .setPositiveButtonOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dismissDialogIfShowing((AlertDialog) dialog);
//                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
//                        context.startActivity(intent);
//                        if (positiveListener != null) {
//                            positiveListener.onClick(dialog, which);
//                        }
//                    }
//                })
//                .setNegativeButtonLabelId(R.string.btn_cancel)
//                .setNegativeButtonOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dismissDialogIfShowing((AlertDialog) dialog);
//                        if (negativeListener != null) {
//                            negativeListener.onClick(dialog, which);
//                        }
//                    }
//                });
//    }

    /**
     * Creates prompt delete NAB ID dialog.
     */
//    public static CustomAlertDialogBuilder createRemoveNabIdPromptDialog(Context context, DialogInterface
//            .OnClickListener positiveBtnClickListener) {
//        return new CustomAlertDialogBuilder(context)
//                .setImageId(R.drawable.ic_feedback_question)
//                .setTitleId(R.string.mt0042_title)
//                .setContentId(R.string.mt0042)
//                .setPositiveButtonLabelId(R.string.mt0042_positive)
//                .setPositiveButtonOnClickListener(positiveBtnClickListener, true)
//                .setNegativeButtonLabelId(R.string.mt0042_negative)
//                .setNegativeButtonOnClickListener(null, true);
//    }
//
//    public static CustomAlertDialogBuilder createAddProfileFingerprintWarningDialog(Context context, DialogInterface
//            .OnClickListener positiveBtnClickListener) {
//        return new CustomAlertDialogBuilder(context)
//                .setTitleId(R.string.mt0595_title)
//                .setContentId(R.string.mt0595)
//                .setImageId(R.drawable.ic_alert_exclaim_medium)
//                .setPositiveButtonLabelId(R.string.btn_yes)
//                .setPositiveButtonOnClickListener(positiveBtnClickListener, true)
//                .setNegativeButtonLabelId(R.string.btn_no);
//    }
//
//    public static CustomAlertDialogBuilder createDeviceIdErrorDialog(
//            Context context, String userName,
//            DialogInterface.OnClickListener positiveBtnClickListener,
//            @Nullable DialogInterface.OnDismissListener onDismissClickListener) {
//        String title = context.getString(R.string.me0252_title, userName);
//        return new CustomAlertDialogBuilder(context)
//                .setImageId(R.drawable.ic_alert_exclaim_medium)
//                .setTitleText(title)
//                .setContentId(R.string.me0252)
//                .setOnDismissListener(onDismissClickListener)
//                .setPositiveButtonLabelId(R.string.me0252_positive)
//                .setCancelable(true)
//                .setPositiveButtonOnClickListener(positiveBtnClickListener, true);
//    }
//
//    public static CustomAlertDialogBuilder createRemoveNabIdOnAllDevicesDialog(Context context, DialogInterface
//            .OnClickListener clickListener) {
//        return new CustomAlertDialogBuilder(context)
//                .setImageId(R.drawable.ic_feedback_question)
//                .setTitleId(R.string.mt0270_title)
//                .setContentId(R.string.mt0270)
//                .setPositiveButtonLabelId(R.string.mt0270_positive)
//                .setPositiveButtonOnClickListener(clickListener, true)
//                .setNegativeButtonLabelId(R.string.mt0270_negative)
//                .setNegativeButtonOnClickListener(clickListener, true);
//    }
//
//    public static CustomAlertDialogBuilder createPushNotificationSettingLoadErrorDialog(Context context, DialogInterface
//            .OnClickListener clickListener) {
//
//        return new CustomAlertDialogBuilder(context)
//                .setCancelable(false)
//                .setImageId(R.drawable.ic_alert_exclaim_medium)
//                .setTitleId(R.string.me0408_title)
//                .setContentId(R.string.me0408)
//                .setPositiveButtonLabelId(R.string.me0408_positive)
//                .setPositiveButtonOnClickListener(clickListener, true)
//                .setNegativeButtonLabelId(R.string.me0408_negative)
//                .setNegativeButtonOnClickListener(clickListener, true);
//
//    }
//
//    public static CustomAlertDialogBuilder createLogoutConfirmationDialog(Context context, DialogInterface
//            .OnClickListener clickListener) {
//        return new CustomAlertDialogBuilder(context)
//                .setImageId(R.drawable.ic_logout)
//                .setTitleId(R.string.fragment_navigation_logout)
//                .setContentId(R.string.logout_dialog_message)
//                .setPositiveButtonLabelId(R.string.btn_yes)
//                .setPositiveButtonOnClickListener(clickListener, true)
//                .setNegativeButtonLabelId(R.string.btn_no);
//    }
//
//    /**
//     * Dismiss <code>alertDialog</code> if it is showing. Always returns null.
//     *
//     * @param alertDialog
//     *         {@link AlertDialog}
//     *
//     * @return null
//     */
//    @Contract("_ -> null")
//    public static AlertDialog dismissDialogIfShowing(AlertDialog alertDialog) {
//        if (alertDialog != null && alertDialog.isShowing()) {
//            alertDialog.dismiss();
//        }
//        return null;
//    }
//
//    public static void showDialog(Context context, @StringRes int title, @StringRes int message,
//                                  @StringRes int positiveButton, @StringRes int negativeButton,
//                                  @DrawableRes int image, final @Nullable DialogCallback callback) {
//        CustomAlertDialogBuilder builder = new CustomAlertDialogBuilder(context)
//                .setTitleId(title)
//                .setContentId(message)
//                .setPositiveButtonLabelId(positiveButton)
//                .setPositiveButtonOnClickListener(new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        if (callback != null) {
//                            callback.onDialogPositiveButtonClick();
//                        }
//                    }
//                });
//
//        if (negativeButton != 0) {
//            builder.setNegativeButtonOnClickListener(new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                    if (callback != null) {
//                        callback.onDialogNegativeButtonClick();
//                    }
//                }
//            });
//
//            builder.setNegativeButtonLabelId(negativeButton);
//        }
//
//        if (image != 0) {
//            builder.setImageId(image);
//        }
//
//        builder.show();
//    }

    public interface DialogCallback {
        void onDialogPositiveButtonClick();

        void onDialogNegativeButtonClick();
    }
}
