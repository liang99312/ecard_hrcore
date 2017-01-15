/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.query3;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.rebuild.EntityBuilder;

/**
 *
 * @author Administrator
 */
public class PropTreeModel extends DefaultTreeModel {

    private static final long serialVersionUID = 1L;
    private DefaultMutableTreeNode root = new DefaultMutableTreeNode("全部");
    private Class<?> entity_class;

    // 是否包含集合类型字段
    private boolean include_set_field = true;

    public void setInclude_set_field(boolean include_set_field) {
        this.include_set_field = include_set_field;
    }

    public PropTreeModel() {
        super(new DefaultMutableTreeNode());
    }

    public PropTreeModel(Class<?> the_class) {
        super(new DefaultMutableTreeNode());
        setEntity_class(the_class);
    }

    public PropTreeModel(Class<?> the_class, boolean include_set_field) {
        super(new DefaultMutableTreeNode());
        this.include_set_field = include_set_field;
        setEntity_class(the_class);
    }

    public void setEntity_class(Class<?> the_class) {
        entity_class = the_class;
        this.setRoot(root);
        root.removeAllChildren();
        buildTree(the_class, root, "", "", entity_class.getSimpleName(), "");
    }

    private void buildTree(Class<?> the_class2, DefaultMutableTreeNode node2, String superBindName, String superDisplayBindName,
            String superClassNames, String superFieldClasses) {
        Class<?> tmp_class = the_class2;
        while (tmp_class.getName().startsWith("org.jhrcore.entity")) {
            for (Field field : tmp_class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
                if (fieldAnnotation == null || !fieldAnnotation.visible()) {
                    continue;
                }

                DefaultMutableTreeNode cur_node = null;

                if (field.getType().getSimpleName().equals("Set") || field.getType().getSimpleName().equals("List")) {
                    if (!include_set_field) {
                        continue;
                    }
                    Type genericType = field.getGenericType();

                    ParameterizedType pType = (ParameterizedType) genericType;
                    Type[] arguments = pType.getActualTypeArguments();
                    for (Type type : arguments) {
                        Class clazz = (Class) type;
                        if (clazz.getSimpleName().startsWith("Base")) {
                            // 如果该集合包含的对象是基类，则获取他的继承类
                            List<String> list_subClass = EntityBuilder.getHt_subclasses().get(clazz.getName());
                            if (list_subClass != null) {
                                for (String subClass : list_subClass) {
                                    try {
                                        Class aClass = Class.forName(subClass);
                                        ClassAnnotation ca = (ClassAnnotation) aClass.getAnnotation(ClassAnnotation.class);
                                        cur_node = new DefaultMutableTreeNode(ca == null ? aClass.getSimpleName() : ca.displayName());
                                        node2.add(cur_node);
                                        buildTree(aClass, cur_node, "", fieldAnnotation.displayName(), superClassNames + ";" + aClass.getSimpleName(), superFieldClasses.equals("") ? "3" : (superFieldClasses + ";3"));
                                    } catch (ClassNotFoundException ex) {
                                        Logger.getLogger(PropTreeModel.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        } else {
                            cur_node = new DefaultMutableTreeNode(fieldAnnotation.displayName());
                            node2.add(cur_node);
                            buildTree(clazz, cur_node, "", fieldAnnotation.displayName(), superClassNames + ";" + clazz.getSimpleName(), superFieldClasses.equals("") ? "3" : (superFieldClasses + ";3"));
                        }
                    }
                } else {
                    TempField tempField = new TempField();
                    tempField.setEntityClass(the_class2);
                    tempField.setField_name(field.getName());
                    tempField.setDisplay_name(fieldAnnotation.displayName());
                    tempField.setBindName(superBindName.equals("") ? tempField.getField_name() : superBindName + "." + tempField.getField_name());
                    tempField.setDisplayBindName(superDisplayBindName.equals("") ? tempField.getDisplay_name() : superDisplayBindName + "." + tempField.getDisplay_name());

                    tempField.setEntityNames(superClassNames);
                    if (field.getType().getName().contains("entity") && !field.getType().getSimpleName().equals("Code")) {
                        tempField.setFieldClasses(superFieldClasses.equals("") ? "2" : (superFieldClasses + ";2"));
                    } else {
                        tempField.setFieldClasses(superFieldClasses.equals("") ? "1" : (superFieldClasses + ";1"));
                    }

                    cur_node = new DefaultMutableTreeNode(tempField);
                    node2.add(cur_node);

                    // 包含entity说明是我们定义的类，递归列举该类的字段
                    if (field.getType().getName().contains("entity") && !field.getType().getSimpleName().equals("Code") && !field.getType().getName().equals(entity_class.getName()) && entity_class == the_class2) {
                        buildTree(field.getType(), cur_node, tempField.getBindName(), tempField.getDisplayBindName(), superClassNames + ";" + field.getType().getSimpleName(), superFieldClasses.equals("") ? "2" : (superFieldClasses + ";2"));
                    }
                }
            }
            tmp_class = tmp_class.getSuperclass();
        }
    }
}
