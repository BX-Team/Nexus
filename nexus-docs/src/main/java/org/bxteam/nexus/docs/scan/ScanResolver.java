package org.bxteam.nexus.docs.scan;

import java.util.List;

public interface ScanResolver<RESULT> {
    List<RESULT> resolve(ScanRecord record);
}
