/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.importxls;

import java.util.Hashtable;
import java.util.List;
import org.jhrcore.entity.ExportScheme;

/**
 *
 * @author Administrator
 */
public class XlsImportInfo {

    private ExportScheme exportScheme;
    //private List<List<String>> values;
    private List<Hashtable<String,String>> values;
    public ExportScheme getExportScheme() {
        return exportScheme;
    }

    public void setExportScheme(ExportScheme exportScheme) {
        this.exportScheme = exportScheme;
    }

    public List<Hashtable<String, String>> getValues() {
        return values;
    }

    public void setValues(List<Hashtable<String, String>> values) {
        this.values = values;
    }
    
//    public List<List<String>> getValues() {
//        return values;
//    }
//
//    public void setValues(List<List<String>> values) {
//        this.values = values;
//    }
}
