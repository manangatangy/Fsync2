https://stackoverflow.com/questions/28798699/floating-toolbar-with-appcompat

1. Changed theme to @style/Theme.AppCompat.Light.NoActionBar
when it comes time to do the appBar/navigation, read;
https://developer.android.com/training/appbar/up-action.html#java
https://developer.android.com/reference/android/support/v7/app/ActionBar.html
https://developer.android.com/training/appbar/index.html
https://developer.android.com/training/implementing-navigation/ancestral.html
https://developer.android.com/reference/android/support/design/widget/CoordinatorLayout.html
floating-toolbar:
https://stackoverflow.com/questions/28798699/floating-toolbar-with-appcompat

2. user entered fields for a server;
FtpClientConfig:
String serverTimeZoneId (as from String[] java.util.TimeZone.getAvailableIDs())
String serverSystemKey (specified list)
String serverLanguageCode
String defaultDateFormatStr (java.text.SimpleDateFormat)
String recentDateFormatStr (java.text.SimpleDateFormat)
String serverLanguageCode (two letter iso 639 language code, from Collection<String> FtpClientConfig.getSupportedLanguageCodes())

Use  setUnparseableEntries(boolean saveUnparseable) and then if FTPFile.isValid() returns false,
the unparsed entry will be available at FTPFile.getRawListing().

This guy says it's a beaut:
https://stackoverflow.com/questions/1567601/android-ftp-library

This is a long detailed example from the apache peeps:
http://commons.apache.org/proper/commons-net/examples/ftp/FTPClientExample.java

Runtime permissions:
https://developer.android.com/guide/topics/permissions/index.html
https://developer.android.com/guide/topics/permissions/overview.html
https://www.captechconsulting.com/blogs/runtime-permissions-best-practices-and-how-to-gracefully-handle-permission-removal

mListDir fails to parse:
entry =
modify=20180306103717;perm=adfrw;size=31;type=OS.unix=symlink;unique=801U66C00C8;UNIX.group=1002;UNIX.mode=0777;UNIX.owner=1002; Music
and therefore excludes sylinks from the resultset.

MLSxEntryParser#128
The above entry is parsed as factparts = { type, OS.unix, symlink } which has three elements.
