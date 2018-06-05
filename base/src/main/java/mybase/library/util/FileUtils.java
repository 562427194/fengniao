package mybase.library.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class FileUtils {
	// 删除文件�?
	// param folderPath 文件夹完整绝对路�?

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内�?
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下�?有文�?
	// param path 文件夹完整绝对路�?
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文�?
				delFolder(path + "/" + tempList[i]);// 再删除空文件�?
				flag = true;
			}
		}
		return flag;
	}
	public static long getFileSizes(File f) throws Exception{//取得文件大小
        long s=0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
           s= fis.available();
        } else {
            f.createNewFile();
            System.out.println("文件不存�?");
        }
        return s;
    }
    // 递归
    public static long getFileSize(File f)throws Exception//取得文件夹大�?
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getFileSize(flist[i]);
            } else
            {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    
    public static String FormetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
   
    public static long getlist(File f){//递归求取目录文件个数
        long size = 0;
        File flist[] = f.listFiles();
        size=flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;
   }
    public static String getFileSizeFmt(File file) throws Exception{
    	return FormetFileSize(getFileSizes(file));
    }
    public static String getFileName(String file){
    	if(file==null){
    		return "未知";
    	}
    	int start  = file.lastIndexOf("/")+1;
    	if(start==0){
    		return file;
    	}
    	return file.substring(start,file.length());
    }
}
