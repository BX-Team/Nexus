package space.bxteam.nexus.core.scanner;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

public class ClassgraphScanner {
    public static void scanClassesWithAnnotation(String packageName, @NotNull Class<? extends Annotation> annotation, Consumer<ClassInfo> consumer) {
        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .acceptPackages(packageName)
                .scan()) {
            scanResult.getClassesWithAnnotation(annotation.getName()).forEach(consumer);
        }
    }
}
