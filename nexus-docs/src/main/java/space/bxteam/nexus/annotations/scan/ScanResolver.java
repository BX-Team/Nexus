package space.bxteam.nexus.annotations.scan;

import java.util.List;

public interface ScanResolver<RESULT> {
    List<RESULT> resolve(ScanRecord record);
}
