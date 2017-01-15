/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;

/**
 *
 * @author mxliteboss
 */
@Entity
public class EmpQueryAnalyseScheme extends CommAnalyseScheme implements Serializable,IKey,KeyInterface {

    private static final long serialVersionUID = 1L;

    @Transient
    @Override
    public List<String> getCalCaptions() {
        List<String> list = new ArrayList<String>();
        for (CommAnalyseField qaf : commAnalyseFields) {
            String caption = qaf.getField_caption() == null ? "" : qaf.getField_caption();
            if (!qaf.getStat_type().equals("∆’Õ®")) {
                caption = caption + "(" + qaf.getStat_type() + ")";
                list.add(caption);
            }
        }
        return list;
    }

    @Override
    @Transient
    public String getFieldClassName() {
        return "org.jhrcore.entity.query.EmpQueryAnalyseField";
    }
}
