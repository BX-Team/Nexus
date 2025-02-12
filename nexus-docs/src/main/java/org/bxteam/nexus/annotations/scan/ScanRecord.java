package org.bxteam.nexus.annotations.scan;

import java.lang.reflect.Method;
import java.util.List;

public record ScanRecord(Class<?> clazz, List<Method> methods) { }
