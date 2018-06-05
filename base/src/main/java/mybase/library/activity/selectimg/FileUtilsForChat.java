package mybase.library.activity.selectimg;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * 文件操作类 Created by Nereo on 2015/4/8.
 */
public class FileUtilsForChat {
	private final static String TAG = FileUtilsForChat.class.getSimpleName();
	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	public static File createTmpFile(Context context) throws IOException {
		File dir = null;
		if (TextUtils.equals(Environment.getExternalStorageState(),
				Environment.MEDIA_MOUNTED)) {
			dir = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
			if (!dir.exists()) {
				dir = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM
								+ "/Camera");
				if (!dir.exists()) {
					dir = getCacheDirectory(context, true);
				}
			}
		} else {
			dir = getCacheDirectory(context, true);
		}
		return File.createTempFile(JPEG_FILE_PREFIX, JPEG_FILE_SUFFIX, dir);
	}

	private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

	/**
	 * Returns application cache directory. Cache directory will be created on
	 * SD card <i>("/Android/data/[app_package_name]/cache")</i> if card is
	 * mounted and app has appropriate permission. Else - Android defines cache
	 * directory on device's file system.
	 * 
	 * @param context
	 *            Application context
	 * @return Cache {@link File directory}.<br />
	 *         <b>NOTE:</b> Can be null in some unpredictable cases (if SD card
	 *         is unmounted and {@link Context#getCacheDir()
	 *         Context.getCacheDir()} returns null).
	 */
	public static File getCacheDirectory(Context context) {
		return getCacheDirectory(context, true);
	}

	/**
	 * Returns application cache directory. Cache directory will be created on
	 * SD card <i>("/Android/data/[app_package_name]/cache")</i> (if card is
	 * mounted and app has appropriate permission) or on device's file system
	 * depending incoming parameters.
	 * 
	 * @param context
	 *            Application context
	 * @param preferExternal
	 *            Whether prefer external location for cache
	 * @return Cache {@link File directory}.<br />
	 *         <b>NOTE:</b> Can be null in some unpredictable cases (if SD card
	 *         is unmounted and {@link Context#getCacheDir()
	 *         Context.getCacheDir()} returns null).
	 */
	public static File getCacheDirectory(Context context, boolean preferExternal) {
		File appCacheDir = null;
		String externalStorageState;
		try {
			externalStorageState = Environment.getExternalStorageState();
		} catch (NullPointerException e) { // (sh)it happens (Issue #660)
			externalStorageState = "";
		} catch (IncompatibleClassChangeError e) { // (sh)it happens too (Issue
													// #989)
			externalStorageState = "";
		}
		if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState)
				&& hasExternalStoragePermission(context)) {
			appCacheDir = getExternalCacheDir(context);
		}
		if (appCacheDir == null) {
			appCacheDir = context.getCacheDir();
		}
		if (appCacheDir == null) {
			String cacheDirPath = "/data/data/" + context.getPackageName()
					+ "/cache/";
			appCacheDir = new File(cacheDirPath);
		}
		return appCacheDir;
	}

	/**
	 * Returns individual application cache directory (for only image caching
	 * from ImageLoader). Cache directory will be created on SD card
	 * <i>("/Android/data/[app_package_name]/cache/uil-images")</i> if card is
	 * mounted and app has appropriate permission. Else - Android defines cache
	 * directory on device's file system.
	 * 
	 * @param context
	 *            Application context
	 * @param cacheDir
	 *            Cache directory path (e.g.: "AppCacheDir",
	 *            "AppDir/cache/images")
	 * @return Cache {@link File directory}
	 */
	public static File getIndividualCacheDirectory(Context context,
			String cacheDir) {
		File appCacheDir = getCacheDirectory(context);
		File individualCacheDir = new File(appCacheDir, cacheDir);
		if (!individualCacheDir.exists()) {
			if (!individualCacheDir.mkdir()) {
				individualCacheDir = appCacheDir;
			}
		}
		return individualCacheDir;
	}

	private static File getExternalCacheDir(Context context) {
		File dataDir = new File(new File(
				Environment.getExternalStorageDirectory(), "Android"), "data");
		File appCacheDir = new File(
				new File(dataDir, context.getPackageName()), "cache");
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				return null;
			}
			try {
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException e) {
			}
		}
		return appCacheDir;
	}

	private static boolean hasExternalStoragePermission(Context context) {
		int perm = context
				.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
		return perm == PackageManager.PERMISSION_GRANTED;
	}

	/**
	 * 判断路径是文件还是目录
	 * 
	 * @param fileOrDirName
	 * @param context
	 * @return
	 */
	public static boolean isAssetsDirs(String fileOrDirName, Context context) {
		return !(fileOrDirName.startsWith(".") || (fileOrDirName
				.lastIndexOf(".") != -1));
	}

	/**
	 * 使用list列出所有文件和目录。如果是目录：则在目标区域建立一个同名目录。如果为文件，则copy it。
	 * 
	 * @param AssetsPath
	 * @param ObjectPath
	 * @param context
	 * @return
	 */
	public static boolean CopyAssetsPath(String AssetsPath, String ObjectPath,
			Context context) {
		File ObjPath = new File(ObjectPath);
		// 不存在创建文件夹
		if (!ObjPath.exists()) {
			Log.e(TAG, "Object Path not found or not Dir:" + ObjectPath);
			ObjPath.mkdirs();
		}
		AssetManager am = context.getAssets();
		try {
			String[] FileOrDirName = am.list(AssetsPath);
			// Log.e("3DiJoy",
			// String.format("In Assets Path: [%s]. There is:[%d] file or Dir",
			// AssetsPath, FileOrDirName.length));
			for (int i = 0; i < FileOrDirName.length; i++) {
				// 如果是目录
				if (isAssetsDirs(AssetsPath + "/" + FileOrDirName[i], context)) {
					File N_DIR = new File(ObjectPath + "/" + FileOrDirName[i]);
					if (!N_DIR.exists()) {
						Log.e(TAG, String.format("Will Create Dir:[%s]",
								ObjectPath + "/" + FileOrDirName[i]));
						N_DIR.mkdir();
						CopyAssetsPath(AssetsPath + "/" + FileOrDirName[i],
								ObjectPath + "/" + FileOrDirName[i], context);
					}
				} else // 如果是文件就copy到指定目录
				{
					File file = new File(ObjectPath + "/" + FileOrDirName[i]);
					if (!file.exists()) {
						Log.e(TAG, String.format("Will Create file:[%s]",
								ObjectPath + "/" + FileOrDirName[i]));
						CopyAssets(AssetsPath + "/" + FileOrDirName[i],
								ObjectPath + "/" + FileOrDirName[i], context);
					}

				}
				// Log.e("3DiJoy", String.format("File Or Dir:[%s]",
				// FileOrDirName[i]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 复制文件
	 * 
	 * @param assetFile
	 * @param objectFile
	 * @param context
	 */
	public static void CopyAssets(String assetFile, String objectFile,
			Context context) {
		try {
			InputStream inStream = context.getAssets().open(assetFile);
			String filePath = objectFile;
			OutputStream outStream = new FileOutputStream(filePath);
			byte[] buffer = new byte[1024];
			int read;
			while ((read = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, read);
			}
			outStream.flush();
			inStream.close();
			inStream = null;
			outStream.close();
			outStream = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
