package com.samodule.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.exception.DataException;
//import org.hibernate.property.PropertyAccessor;
//import org.hibernate.property.PropertyAccessorFactory;
//import org.hibernate.property.Setter;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.hibernate.transform.ResultTransformer;


public class AliasToBeanNestedResultTransformerDTO extends
AliasedTupleSubsetResultTransformer {

    private static final long serialVersionUID = -8047276133980128266L;

    private static final int TUPE_INDEX = 0;
    private static final int ALISES_INDEX = 1;
    private static final int FIELDNAME_INDEX = 2;

//    private static final PropertyAccessor accessor = PropertyAccessorFactory
//            .getPropertyAccessor("property");
    PropertyAccessStrategy accessor = PropertyAccessStrategyFieldImpl.INSTANCE;
    
    private final Class<?> resultClass;

    private Object[] entityTuples;
    private String[] entityAliases;

    private Map<String, Class<?>> fieldToClass = new HashMap<String, Class<?>>();
    private Map<String, List<?>> subEntities = new HashMap<String, List<?>>();
    private List<String> nestedAliases = new ArrayList<String>();
    private Map<String, Class<?>> listFields = new HashMap<String, Class<?>>();

    public boolean isTransformedValueATupleElement(String[] aliases,
            int tupleLength) {
        return false;
    }

    public AliasToBeanNestedResultTransformerDTO(Class<?> resultClass) {

        this.resultClass = resultClass;
    }
    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {

        handleSubEntities(tuple, aliases);
        cleanParams(tuple, aliases);
        ResultTransformer rootTransformer = new AliasToBeanResultTransformer(
                resultClass);
        Object root = rootTransformer.transformTuple(entityTuples,
                entityAliases);

        loadSubEntities(root);

        cleanMaps();
        return root;
    }

    private void handleSubEntities(Object[] tuple, String[] aliases)
            throws HibernateException {
        String fieldName = "";
        String aliasName = "";
        try {
            for (int i = 0; i < aliases.length; i++) {
                String alias = aliases[i];
                if (alias.contains(".")) {

                    String[] sp = alias.split("\\.");
                    StringBuilder aliasBuilder = new StringBuilder();
                    for (int j = 0; j < sp.length; j++) {
                        if (j == 0) {
                            fieldName = sp[j];
                        } else {
                            aliasBuilder.append(sp[j]);
                            aliasBuilder.append(".");
                        }
                    }
                    aliasName = aliasBuilder.substring(0,
                            aliasBuilder.length() - 1);

                    nestedAliases.add(alias);
                    manageEntities(fieldName, aliasName, tuple[i]);
                }
            }
        } catch (NoSuchFieldException e) {
            throw new HibernateException("Could not instantiate resultclass: "
                    + resultClass.getName() + " for field name: " + fieldName
                    + " and alias name:" + aliasName);
        }
    }

    private Class<?> findClass(String fieldName) throws NoSuchFieldException,
    SecurityException {
        if (fieldToClass.containsKey(fieldName)) {
            return fieldToClass.get(fieldName);
        } else {
            Class<?> subclass = resultClass.getDeclaredField(fieldName)
                    .getType();

            if (subclass.equals(List.class) || subclass.equals(Set.class)) {
                if (subclass.equals(List.class)) {
                    listFields.put(fieldName, LinkedList.class);
                } else {
                    listFields.put(fieldName, HashSet.class);
                }
                Field field = resultClass.getDeclaredField(fieldName);
                ParameterizedType genericType = (ParameterizedType) field
                        .getGenericType();
                subclass = (Class<?>) genericType.getActualTypeArguments()[0];

            }
            fieldToClass.put(fieldName, subclass);
            return subclass;
        }
    }

    @SuppressWarnings("unchecked")
    private void manageEntities(String fieldName, String aliasName,
            Object tupleValue) throws NoSuchFieldException, SecurityException {
        Class<?> subclass = findClass(fieldName);
        if (!subEntities.containsKey(fieldName)) {
            List<Object> list = new ArrayList<Object>();
            list.add(new ArrayList<Object>());
            list.add(new ArrayList<String>());
            list.add(FIELDNAME_INDEX, subclass);
            subEntities.put(fieldName, list);
        }
        ((List<Object>) subEntities.get(fieldName).get(TUPE_INDEX))
        .add(tupleValue);
        ((List<String>) subEntities.get(fieldName).get(ALISES_INDEX))
        .add(aliasName);
    }

    private void cleanParams(Object[] tuple, String[] aliases) {
        entityTuples = new Object[aliases.length - nestedAliases.size()];
        entityAliases = new String[aliases.length - nestedAliases.size()];

        for (int j = 0, i = 0; j < aliases.length; j++) {
            if (!nestedAliases.contains(aliases[j])) {
                entityTuples[i] = tuple[j];
                entityAliases[i] = aliases[j];
                ++i;
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    private void loadSubEntities(Object root) throws HibernateException {
        try {
            for (String fieldName : subEntities.keySet()) {
                Class<?> subclass = (Class<?>) subEntities.get(fieldName).get(
                        FIELDNAME_INDEX);

                ResultTransformer subclassTransformer = new AliasToBeanNestedResultTransformer(
                        subclass);

                Object subObject = subclassTransformer.transformTuple(
                        ((List<Object>) subEntities.get(fieldName).get(0))
                        .toArray(),
                        ((List<Object>) subEntities.get(fieldName).get(1))
                        .toArray(new String[0]));

                //Setter setter = accessor.getSetter(resultClass, fieldName);
                PropertyAccess access = accessor.buildPropertyAccess(resultClass, fieldName);
                Setter setter = access.getSetter();
                if (listFields.containsKey(fieldName)) {
                    Class<?> collectionClass = listFields.get(fieldName);
                    Collection subObjectList = (Collection) collectionClass
                            .newInstance();
                    subObjectList.add(subObject);
                    setter.set(root, subObjectList, null);
                } else {
                    setter.set(root, subObject, null);
                }
            }
        } catch (Exception e) {
            throw new HibernateException(e);
        }
    }

    private void cleanMaps() {
        fieldToClass = new HashMap<String, Class<?>>();
        subEntities = new HashMap<String, List<?>>();
        nestedAliases = new ArrayList<String>();
        listFields = new HashMap<String, Class<?>>();
    }
    public List<? extends Entity<?>> cleanList(
            List<? extends Entity<?>> resultList) throws DataException {
        List<Entity<?>> entities = new ArrayList<Entity<?>>();
        Entity<?> current = null;
        try {
            for (Entity<?> entity : resultList) {
            if (entity.getId() == null) {
                continue;
            }
            if (current == null) {
                current = entity;
                continue;
            }
            if (current.getId().equals(entity.getId())) {
                append(current, entity);
            } else {
                entities.add(current);
                current = entity;
            }
            }
            if (current != null) {
            entities.add(current);
            }
            cleanSubList(entities);
            return entities;
        } catch (Exception e) {
            throw new DataException(e.getMessage(),new SQLException(e.getCause()));
        }
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public Set<? extends Entity<?>> cleanList(
            Set<? extends Entity<?>> resultList) throws DataException {
            List listToClean = new LinkedList();
            listToClean.addAll(resultList);
            listToClean = cleanList(listToClean);
            resultList.clear();
            resultList.addAll(listToClean);
            return resultList;
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        private void append(Entity<?> current, Entity<?> next)
            throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = current.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().equals(List.class)) {
            field.setAccessible(true);
            List valueNext = (List) field.get(next);
            List valueCurrent = (List) field.get(current);
            if (valueNext != null) {
                if (valueCurrent != null) {
                valueCurrent.addAll(valueNext);
                field.set(current, valueCurrent);
                } else {
                field.set(current, valueNext);
                }
            }
            } else if (field.getType().equals(Set.class)) {
            field.setAccessible(true);
            Set valueNext = (Set) field.get(next);
            Set valueCurrent = (Set) field.get(current);
            if (valueNext != null) {
                if (valueCurrent != null) {
                valueCurrent.addAll(valueNext);
                field.set(current, valueCurrent);
                } else {
                field.set(current, valueNext);
                }
            }
            }
        }

        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        private void cleanSubList(List<? extends Entity<?>> listToClean)
            throws IllegalArgumentException, IllegalAccessException,
            DataException {
                for (Entity<?> entity : listToClean) {
                    Field[] fields = entity.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if (field.getType().equals(List.class)) {
                            field.setAccessible(true);
                            List valueToClean = (List) field.get(entity);
                            // Throw a thread
                            if (valueToClean != null) {
                                valueToClean = cleanList(valueToClean);
                                field.set(entity, valueToClean);
                            }
                            } else if (field.getType().equals(Set.class)) {
                                field.setAccessible(true);
                                Set valueToClean = (Set) field.get(entity);
                                // Throw a thread
                                if (valueToClean != null) {
                                    valueToClean = cleanList(valueToClean);
                                    field.set(entity, valueToClean);
                                }
                            }

                    }
                }
        }
}