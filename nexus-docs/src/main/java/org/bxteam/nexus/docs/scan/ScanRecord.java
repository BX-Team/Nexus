package org.bxteam.nexus.docs.scan;

import java.lang.reflect.Method;
import java.util.List;

public record ScanRecord(Class<?> clazz, List<Method> methods) { }
