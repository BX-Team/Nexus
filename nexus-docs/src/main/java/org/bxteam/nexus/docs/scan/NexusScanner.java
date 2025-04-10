package org.bxteam.nexus.docs.scan;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.util.ArrayList;
import java.util.List;

public final class NexusScanner {
    private final ClassLoader classLoader;
    private final String packageToScan;

    public NexusScanner(ClassLoader classLoader, String packageToScan) {
        this.classLoader = classLoader;
        this.packageToScan = packageToScan;
    }

    public <RESULT, RESOLVER extends ScanResolver<RESULT>> List<RESULT> scan(RESOLVER resolver) {
        List<RESULT> results = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph().acceptPackages(packageToScan).enableAllInfo().scan()) {
            for (ClassInfo classInfo : scanResult.getAllClasses()) {
                Class<?> classToScan = Class.forName(classInfo.getName(), false, classLoader);
                ScanRecord record = new ScanRecord(classToScan, List.of(classToScan.getDeclaredMethods()));
                results.addAll(resolver.resolve(record));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return results;
    }
}
