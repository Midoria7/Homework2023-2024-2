package FileCopy;

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class FileCopyWithProgress {
    public static void main(String[] args) {
        System.out.println("请输入要复制的文件数量：");
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        System.out.println("请输入要复制的文件路径：");
        String[] sources = new String[n];
        for (int i = 0; i < n; i++) {
            sources[i] = scanner.nextLine();
        }
        System.out.println("请输入目标文件夹：");
        String targetDirectory = scanner.nextLine();
        scanner.close();

        for (String source : sources) {
            new Thread(new FileCopyTask(source, targetDirectory)).start();
        }
    }

    static class FileCopyTask implements Runnable {
        private String sourceFile;
        private String targetDirectory;

        public FileCopyTask(String sourceFile, String targetDirectory) {
            this.sourceFile = sourceFile;
            this.targetDirectory = targetDirectory;
        }

        @Override
        public void run() {
            try {
                File source = new File(sourceFile);
                File target = new File(targetDirectory, source.getName());
                copyFileWithProgress(source, target);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void copyFileWithProgress(File source, File target) throws IOException {
            try (InputStream in = new FileInputStream(source);
                 OutputStream out = new FileOutputStream(target)) {
                long totalBytes = source.length();
                long bytesCopied = 0;
                byte[] buffer = new byte[1024];
                int bytesRead;
                long lastTime = System.currentTimeMillis();
                while ((bytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                    bytesCopied += bytesRead;
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastTime >=50) {
                        int progress = (int) (bytesCopied * 100 / totalBytes);
                        System.out.println(source.getName() + " 文件已复制 " + progress + "%");
                        lastTime = currentTime; // 更新时间
                    }
                }
                System.out.println(source.getName() + " 复制完成！");
            }
        }
    }
}
