/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui.listener;

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Binding.SyncFailure;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author hflj
 */
public class BindingAdapter implements BindingListener{

    @Override
    public void bindingBecameBound(Binding binding) {
        
    }

    @Override
    public void bindingBecameUnbound(Binding binding) {

    }

    @Override
    public void syncFailed(Binding binding, SyncFailure failure) {

    }

    @Override
    public void synced(Binding binding) {
       
    }

    @Override
    public void sourceChanged(Binding binding, PropertyStateEvent event) {
        
    }

    @Override
    public void targetChanged(Binding binding, PropertyStateEvent event) {
        
    }

}
