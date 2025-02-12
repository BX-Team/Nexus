package org.bxteam.nexus.core.utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

@UtilityClass
public class ClassgraphUtil {
    public void scanClassesWithAnnotation(String packageName, @NotNull Class<? extends Annotation> annotation, Consumer<ClassInfo> consumer) {
        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .acceptPackages(packageName)
                .scan()) {
            scanResult.getClassesWithAnnotation(annotation.getName()).forEach(consumer);
        }
    }
}
