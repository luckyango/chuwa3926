package core;

import annotations.MyComponent;
import annotations.MyScope;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import annotations.MyAutowired;

public class SimpleBeanFactory implements BeanFactory {

    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
    private final Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public void registerBean(String beanName, Class<?> beanClass) {
        if (!beanClass.isAnnotationPresent(MyComponent.class)) {
            throw new RuntimeException(beanClass.getName() + " is not annotated with @MyComponent");
        }

        String scope = "singleton";
        if (beanClass.isAnnotationPresent(MyScope.class)) {
            scope = beanClass.getAnnotation(MyScope.class).value();
        }

        beanDefinitions.put(beanName, new BeanDefinition(beanClass, scope));
    }

    @Override
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitions.get(beanName);
        if (beanDefinition == null) {
            throw new RuntimeException("No bean named " + beanName);
        }

        if ("singleton".equals(beanDefinition.getScope())) {
            if (!singletonObjects.containsKey(beanName)) {
                Object bean = createBean(beanDefinition);
                singletonObjects.put(beanName, bean);
            }
            return singletonObjects.get(beanName);
        } else if ("prototype".equals(beanDefinition.getScope())) {
            return createBean(beanDefinition);
        } else {
            throw new RuntimeException("Unsupported scope: " + beanDefinition.getScope());
        }
    }

    private Object createBean(BeanDefinition beanDefinition) {
        try {
            Class<?> clazz = beanDefinition.getBeanClass();
            Object instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(MyAutowired.class)) {
                    String dependencyBeanName = lowerFirst(field.getType().getSimpleName());
                    Object dependency = getBean(dependencyBeanName);

                    field.setAccessible(true);
                    field.set(instance, dependency);
                }
            }

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create bean: " + beanDefinition.getBeanClass().getName(), e);
        }
    }

    private String lowerFirst(String name) {
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
}
