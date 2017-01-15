/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.queryanalysis;

import java.util.List;
import org.jhrcore.entity.query.QueryExtraField;

/**
 *
 * @author mxliteboss
 */
public interface IPickDefineExtraFieldListener {
    public void pickField(List<QueryExtraField> fields);
}
