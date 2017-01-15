/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import com.fr.design.file.core.ChooseFileFilter;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.jhrcore.msg.CommMsg;

/**
 *
 * @author mxliteboss
 */
public class FileChooserUtil {

    private static JFileChooser fileChooser = null;
    public static int FILES_AND_DIRECTORIES = JFileChooser.FILES_AND_DIRECTORIES;
    public static int FILES_ONLY = JFileChooser.FILES_ONLY;
    public static ChooseFileFilter picFilter = new ChooseFileFilter(new String[]{"jpg", "png", "gif"}, "图片文件");
    public static ChooseFileFilter xlsFilter = new ChooseFileFilter(new String[]{"xls"}, "Excel文件");
    public static ChooseFileFilter pdfFilter = new ChooseFileFilter(new String[]{"pdf"}, "PDF文档");
    public static ChooseFileFilter docFilter = new ChooseFileFilter(new String[]{"doc"}, "Word文档");
    public static ChooseFileFilter dbfFilter = new ChooseFileFilter(new String[]{"dbf"}, "Dbf文档");
    public static ChooseFileFilter cptFilter = new ChooseFileFilter(new String[]{"cpt"}, "报表模板文档");
    public static ChooseFileFilter zipFilter = new ChooseFileFilter(new String[]{"zip"}, "Zip文档");
    public static ChooseFileFilter xmlFilter = new ChooseFileFilter(new String[]{"xml"}, "Xml文档");

    public static File getXMLFile(Object title) {
        return getSingleFile(title, xmlFilter);
    }

    public static File getZIPFile(Object title) {
        return getSingleFile(title, zipFilter);
    }

    public static File getPICFile(Object title) {
        return getSingleFile(title, picFilter);
    }

    public static File getDOCFile(Object title) {
        return getSingleFile(title, docFilter);
    }

    public static File getXLSFile(Object title) {
        return getSingleFile(title, xlsFilter);
    }

    public static File getXLSFile(Object title, String defaultName) {
        return getSingleFile(title, xlsFilter, defaultName);
    }

    public static File getDBFFile(Object title) {
        return getSingleFile(title, dbfFilter);
    }

    public static File getPDFFile(Object title) {
        return getSingleFile(title, pdfFilter);
    }

    public static File getCPTFile(Object title) {
        return getSingleFile(title, cptFilter);
    }

    public static File getXMLExportFile(Object title) {
        return getFileForExport(title, "xml");
    }

    public static File getZIPExportFile(Object title) {
        return getFileForExport(title, "zip");
    }

    public static File getDOCExportFile(Object title) {
        return getFileForExport(title, "doc");
    }

    public static File getXLSExportFile(Object title) {
        return getFileForExport(title, "xls");
    }
    
    public static File getXLSExportFile(Object title, String name)
  {
    return getFileForExport(title, "xls", name);
  }

    public static File getDBFExportFile(Object title) {
        return getFileForExport(title, "dbf");
    }

    public static File getPDFExportFile(Object title) {
        return getFileForExport(title, "pdf");
    }

    public static File getCPTExportFile(Object title) {
        return getFileForExport(title, "cpt");
    }

    public static File getPICExportFile(Object title) {
        return getFileForExport(title, "pic");
    }

    public static File getFile(Object title, String fileType) {
        fileType = fileType.toLowerCase();
        if ("pdf".equals(fileType)) {
            return getPDFFile(title);
        } else if ("xls".equals(fileType)) {
            return getXLSFile(title);
        } else if ("doc".equals(fileType)) {
            return getDOCFile(title);
        } else if ("cpt".equals(fileType)) {
            return getCPTFile(title);
        } else if ("pic".equals(fileType)) {
            return getPICFile(title);
        } else if ("dbf".equals(fileType)) {
            return getDBFFile(title);
        } else if ("zip".equals(fileType)) {
            return getZIPFile(title);
        } else if ("xml".equals(fileType)) {
            return getXMLFile(title);
        }
        return getSingleFile(title, null);
    }

    public static File getFile(Object title, String fileType, String defaultName) {
        fileType = fileType.toLowerCase();
        if ("pdf".equals(fileType)) {
            return getPDFFile(title);
        }
        if ("xls".equals(fileType)) {
            return getXLSFile(title, defaultName);
        }
        if ("doc".equals(fileType)) {
            return getDOCFile(title);
        }
        if ("cpt".equals(fileType)) {
            return getCPTFile(title);
        }
        if ("pic".equals(fileType)) {
            return getPICFile(title);
        }
        if ("dbf".equals(fileType)) {
            return getDBFFile(title);
        }
        if ("zip".equals(fileType)) {
            return getZIPFile(title);
        }
        if ("xml".equals(fileType)) {
            return getXMLFile(title);
        }
        return getSingleFile(title, null);
    }

    public static File getDirectoryForExport(Object title) {
        JFileChooser chooser = getFileChooser();
        title = title == null ? "请选择文件夹" : title;
        chooser.setDialogTitle(title + "");
        FileFilter[] ffs = chooser.getChoosableFileFilters();
        for (FileFilter ff : ffs) {
            chooser.removeChoosableFileFilter(ff);
        }
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setMultiSelectionEnabled(false);
        int i = chooser.showSaveDialog(null);
        if (i != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        File file = chooser.getSelectedFile();
        if (file.isDirectory()) {
            return file;
        }
        return new File(FileUtil.getDirectoryPath(file));
    }

    public static File getFileForExport(Object title, String fileType) {
        File file = getFile(title, fileType);
        if (file == null || SysUtil.objToStr(fileType).equals("")) {
            return file;
        }
        String file_path = file.getPath();
        fileType = fileType.toLowerCase();

        if (fileType.equals("pic")) {
            if (file.getName().contains(".")) {
                String type = file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase();
                if (!(type.equals("jpg") || type.equals("png") || type.equals("gif"))) {
                    file_path = file_path + ".jpg";
                }
            } else {
                file_path = file_path + ".jpg";
            }
        } else if (!file_path.toLowerCase().endsWith("." + fileType)) {
            file_path = file_path + "." + fileType;
        }
        file = new File(file_path);
        if (file.isFile()) {
            if (file.exists()) {
                if (MsgUtil.showNotConfirmDialog("您选择的文件已存在，是否覆盖?")) {
                    return null;
                }
            }
        }
        return file;
    }

    public static File getFileForExport(Object title, String fileType, String defaultName) {
        File file = getFile(title, fileType, defaultName);
        if (file == null || SysUtil.objToStr(fileType).equals("")) {
            return file;
        }
        String file_path = file.getPath();
        fileType = fileType.toLowerCase();

        if (fileType.equals("pic")) {
            if (file.getName().contains(".")) {
                String type = file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase();
                if (!(type.equals("jpg") || type.equals("png") || type.equals("gif"))) {
                    file_path = file_path + ".jpg";
                }
            } else {
                file_path = file_path + ".jpg";
            }
        } else if (!file_path.toLowerCase().endsWith("." + fileType)) {
            file_path = file_path + "." + fileType;
        }
        file = new File(file_path);
        if (file.isFile()) {
            if (file.exists()) {
                if (MsgUtil.showNotConfirmDialog("您选择的文件已存在，是否覆盖?")) {
                    return null;
                }
            }
        }
        return file;
    }

    public static File getSingleFile(Object title, ChooseFileFilter filter) {
        JFileChooser chooser = getFileChooser();
        chooser.setDialogTitle(title + "");
        FileFilter[] ffs = chooser.getChoosableFileFilters();
        for (FileFilter ff : ffs) {
            chooser.removeChoosableFileFilter(ff);
        }
        chooser.setAcceptAllFileFilterUsed(filter == null);
        if (filter != null) {
            chooser.setFileFilter(filter);
        }
        chooser.setMultiSelectionEnabled(false);
        int i = chooser.showSaveDialog(null);
        if (i != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return chooser.getSelectedFile();
    }

    public static File getSingleFile(Object title, ChooseFileFilter filter, String defaultName) {
        JFileChooser chooser = getFileChooser();
        chooser.setDialogTitle(title + "");
        FileFilter[] ffs = chooser.getChoosableFileFilters();
        for (FileFilter ff : ffs) {
            chooser.removeChoosableFileFilter(ff);
        }
        chooser.setAcceptAllFileFilterUsed(filter == null);
        if (filter != null) {
            chooser.setFileFilter(filter);
        }
        chooser.setMultiSelectionEnabled(false);
        chooser.setSelectedFile(new File(defaultName));
        int i = chooser.showSaveDialog(null);
        if (i != 0) {
            return null;
        }
        return chooser.getSelectedFile();
    }

    public static List<File> getManyFile(Object title, ChooseFileFilter filter, int selectMod) {
        JFileChooser chooser = getFileChooser();
        List list = new ArrayList();
        chooser.setDialogTitle(title + "");
        if (filter == null) {
            chooser.setFileFilter(picFilter);
            chooser.setFileFilter(xlsFilter);
            chooser.setAcceptAllFileFilterUsed(true);
        } else {
            chooser.setAcceptAllFileFilterUsed(true);
            chooser.setFileFilter(filter);
        }
        chooser.setFileSelectionMode(selectMod);
        chooser.setMultiSelectionEnabled(true);
        int i = chooser.showSaveDialog(null);
        if (i != 0) {
            return list;
        }
        list.addAll(Arrays.asList(chooser.getSelectedFiles()));
        return list;
    }

    public static JFileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser() {

                protected JDialog createDialog(Component parent) throws HeadlessException {
                    JDialog dialog = super.createDialog(parent);
                    dialog.setIconImage(ImageUtil.getIconImage());
                    return dialog;
                }
            };
        }
        fileChooser.setDialogTitle(CommMsg.SELECTFILE_MESSAGE + "");
        return fileChooser;
    }
}
