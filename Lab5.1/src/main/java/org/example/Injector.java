package org.example;

import java.lang.reflect.Field;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class Injector<T> {

    public T inject(T obj) {
        try {
            Properties properties = loadProperties("application.properties"); // Загрузка из файла

            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(AutoInjectable.class)) {
                    String interfaceName = field.getType().getName();
                    String implementationClassName = properties.getProperty(interfaceName);

                    if (implementationClassName != null) {
                        Class<?> implementationClass = Class.forName(implementationClassName);
                        Object implementationInstance = implementationClass.getDeclaredConstructor().newInstance();

                        field.setAccessible(true); // Разрешаем доступ к private полям
                        field.set(obj, implementationInstance);
                    } else {
                        System.err.println("Не найдено реализации для интерфейса: " + interfaceName);
                    }
                }
            }

            return obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Или выбросить исключение, в зависимости от требований
        }
    }

    private Properties loadProperties(String filename) {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                System.out.println("К сожалению, не удалось найти " + filename);
                return properties;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }
}
