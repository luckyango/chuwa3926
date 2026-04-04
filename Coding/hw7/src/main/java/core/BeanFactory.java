package core;


public interface BeanFactory {
    Object getBean(String beanName);
    void registerBean(String beanName, Class<?> beanClass);
}