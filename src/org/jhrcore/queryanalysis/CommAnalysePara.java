/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.queryanalysis;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.query.CommAnalyseScheme;
import org.jhrcore.entity.query.QueryPart;
import org.jhrcore.entity.query.QueryPartPara;
import org.jhrcore.entity.query.QueryScheme;

/**
 *
 * @author mxliteboss
 */
public class CommAnalysePara {

    private CommAnalyseScheme commAnalyseScheme;
    private QueryScheme queryScheme;
    private List<EntityDef> cal_entitys = new ArrayList<EntityDef>();
    private QueryPart queryPart;
    private Hashtable<String, QueryPartPara> para_keys = new Hashtable<String, QueryPartPara>();
    private Class query_class;

    public Class getQuery_class() {
        return query_class;
    }

    public void setQuery_class(Class query_class) {
        this.query_class = query_class;
    }
    
    public CommAnalyseScheme getCommAnalyseScheme() {
        return commAnalyseScheme;
    }

    public void setCommAnalyseScheme(CommAnalyseScheme commAnalyseScheme) {
        this.commAnalyseScheme = commAnalyseScheme;
    }

    public List<EntityDef> getCal_entitys() {
        return cal_entitys;
    }

    public void setCal_entitys(List<EntityDef> cal_entitys) {
        this.cal_entitys = cal_entitys;
    }

    public QueryScheme getQueryScheme() {
        return queryScheme;
    }

    public void setQueryScheme(QueryScheme queryScheme) {
        this.queryScheme = queryScheme;
    }

    public Hashtable<String, QueryPartPara> getPara_keys() {
        return para_keys;
    }

    public void setPara_keys(Hashtable<String, QueryPartPara> para_keys) {
        this.para_keys = para_keys;
    }

    public QueryPart getQueryPart() {
        return queryPart;
    }

    public void setQueryPart(QueryPart queryPart) {
        this.queryPart = queryPart;
    }
    
}
