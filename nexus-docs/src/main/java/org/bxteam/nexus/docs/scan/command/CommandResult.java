package org.bxteam.nexus.docs.scan.command;

import java.util.List;

public record CommandResult(String name, List<String> aliases, List<String> permissions, List<String> descriptions, List<String> arguments) { }
