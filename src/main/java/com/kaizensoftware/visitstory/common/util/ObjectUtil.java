package com.kaizensoftware.visitstory.common.util;

import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class ObjectUtil {

    public void merge(Object fromObject, Object toObject) {

        Arrays.stream(fromObject.getClass().getMethods()).forEach((fromMethod) -> {

            if (fromMethod.getName().startsWith("get")) {

                if (!fromMethod.getName().toLowerCase().contains("class")) {

                    String fromMethodName = fromMethod.getName();
                    String toMethodName = fromMethodName.replace("get", "set");

                    try {

                        Method toMethod = toObject.getClass().getMethod(toMethodName, fromMethod.getReturnType());
                        Object value = fromMethod.invoke(fromObject, (Object[]) null);

                        if (value != null) {

                            if (value instanceof Long) {
                                long numValue = (long) value;
                                if (numValue != 0) {
                                    toMethod.invoke(toObject, value);
                                }
                            } else {
                                if (value instanceof String) {
                                    toMethod.invoke(toObject, value);
                                }
                            }
                        }
                    } catch (Exception ignored) { }
                }

            }

        });
    }
}