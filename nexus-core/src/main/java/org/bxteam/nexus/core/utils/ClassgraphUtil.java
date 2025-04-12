package org.bxteam.nexus.core.utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.experimental.UtilityClass;
import org.bxteam.nexus.core.annotations.compatibility.CompatibilityService;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

@UtilityClass
public class ClassgraphUtil {
    public void scanClassesWithAnnotation(String packageName, @NotNull Class<? extends Annotation> annotation, Consumer<ClassInfo> consumer) {
        CompatibilityService compatibilityUtil = new CompatibilityService();

        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .acceptPackages(packageName)
                .scan()) {
            scanResult.getClassesWithAnnotation(annotation.getName()).forEach(classInfo -> {
                try {
                    Class<?> clazz = classInfo.loadClass();
                    if (compatibilityUtil.isCompatible(clazz)) consumer.accept(classInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
