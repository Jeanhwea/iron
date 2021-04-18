package com.avic.mti.iron.common.helper;

import java.io.*;
import java.util.Optional;

/**
 * 处理文件操作的帮助类
 *
 * @author Jinghui Hu
 * @since 2019-05-30, JDK1.8
 */
public class FileHelper {
  public static final String NEW_LINE = System.getProperty("line.separator");

  /**
   * 将文件分隔符强转成以 / 字符分割
   *
   * @return 分割的路径
   * @author Jinghui Hu
   * @since 2019-09-18, JDK1.8
   */
  public static String coercePath(String path) {
    return Optional.ofNullable(path).map(p1 -> p1.replace('\\', '/')).orElse(null);
  }

  /**
   * 将文件分隔符强转成本地操作系统的分割符
   *
   * @return 分割的路径
   * @author Jinghui Hu
   * @since 2019-09-18, JDK1.8
   */
  public static String localPath(String path) {
    return Optional.ofNullable(path)
        .map(p1 -> p1.replace('\\', '/'))
        .map(p2 -> p2.replace('/', File.separatorChar))
        .orElse(null);
  }

  /**
   * 创建目录，会递归创建每层目录
   *
   * @return 是否创建成功
   * @author Jinghui Hu
   * @since 2019-09-17, JDK1.8
   */
  public static boolean mkdir(String path) {
    return Optional.ofNullable(path)
        .map(FileHelper::localPath)
        .map(p -> new File(p).mkdirs())
        .orElse(false);
  }

  /**
   * 判断文件是否存在
   *
   * @return 是否存在
   * @author Jinghui Hu
   * @since 2019-09-17, JDK1.8
   */
  public static boolean exists(String filename) {
    return Optional.ofNullable(filename)
        .map(FileHelper::localPath)
        .map(f -> new File(f).exists())
        .orElse(false);
  }

  /**
   * 删除文件
   *
   * @return 是否删除成功
   * @author Jinghui Hu
   * @since 2019-09-17, JDK1.8
   */
  public static boolean rm(String filename) {
    return Optional.ofNullable(filename)
        .map(FileHelper::localPath)
        .map(f -> new File(f).delete())
        .orElse(false);
  }

  /**
   * 创建父级所有目录
   *
   * @return 是否创建成功
   * @author Jinghui Hu
   * @since 2019-09-17, JDK1.8
   */
  public static boolean mkParentDir(String filename) {
    return Optional.ofNullable(filename)
        .map(FileHelper::localPath)
        .map(f -> new File(f).getParentFile())
        .map(File::mkdirs)
        .orElse(false);
  }

  /**
   * 读取 file 文件，将内容写到 byte[] 数组中
   *
   * @return byte[] 数组
   * @author Jinghui Hu
   * @since 2019-06-14, JDK1.8
   */
  public static byte[] readBytes(File file) {
    byte[] data = null;
    FileInputStream fileInput = null;
    ByteArrayOutputStream bytesOut = null;

    try {
      fileInput = new FileInputStream(file);
      bytesOut = new ByteArrayOutputStream(1024);

      byte[] buffer = new byte[1024];
      int n;
      while ((n = fileInput.read(buffer)) != -1) {
        bytesOut.write(buffer, 0, n);
      }

      fileInput.close();
      fileInput = null;

      data = bytesOut.toByteArray();

      bytesOut.close();
      bytesOut = null;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fileInput != null) {
          fileInput.close();
        }

        if (bytesOut != null) {
          bytesOut.close();
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return data;
  }

  public static byte[] readBytes(String filename) {
    return Optional.ofNullable(filename)
        .map(FileHelper::localPath)
        .map(f -> FileHelper.readBytes(new File(filename)))
        .orElse(null);
  }

  /**
   * 读取文本文件的内容
   *
   * @return 读取的字符串
   * @author Jinghui Hu
   * @since 2019-10-17, JDK1.8
   */
  public static String readText(File file) {
    StringBuilder stringBuilder = new StringBuilder();
    FileReader fileReader = null;
    BufferedReader bufferedReader = null;
    boolean isFirstLine = true;
    try {
      fileReader = new FileReader(file);
      bufferedReader = new BufferedReader(fileReader);
      stringBuilder = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        if (isFirstLine) {
          stringBuilder.append(line);
        } else {
          stringBuilder.append(FileHelper.NEW_LINE).append(line);
        }

        isFirstLine = false;
      }

      bufferedReader.close();
      bufferedReader = null;
      fileReader.close();
      fileReader = null;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }

        if (fileReader != null) {
          fileReader.close();
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return stringBuilder.toString();
  }

  public static String readText(String filename) {
    return Optional.ofNullable(filename)
        .map(FileHelper::localPath)
        .map(f -> FileHelper.readText(new File(filename)))
        .orElse(null);
  }

  /**
   * 将 byte[] 数组中的内容写入文件中
   *
   * @return void
   * @author Jinghui Hu
   * @since 2019-09-16, JDK1.8
   */
  public static boolean writeBytes(byte[] data, File file) {
    if (data == null || file == null) {
      return false;
    }

    FileOutputStream fileOut = null;
    try {
      if (!file.exists()) {
        Optional.ofNullable(file.getParentFile()).map(File::mkdirs);
      }

      fileOut = new FileOutputStream(file);
      fileOut.write(data, 0, data.length);
      fileOut.flush();

      fileOut.close();
      fileOut = null;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (fileOut != null) {
          fileOut.flush();
          fileOut.close();
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return true;
  }

  public static boolean writeBytes(byte[] data, String filename) {
    return Optional.ofNullable(filename)
        .map(FileHelper::localPath)
        .map(f -> FileHelper.writeBytes(data, new File(f)))
        .orElse(false);
  }

  private FileHelper() {}
}
