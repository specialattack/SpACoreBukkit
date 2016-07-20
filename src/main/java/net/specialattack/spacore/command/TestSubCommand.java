package net.specialattack.spacore.command;

import net.specialattack.spacore.Consts;
import net.specialattack.spacore.api.command.AbstractMultiSubCommand;
import net.specialattack.spacore.api.command.ISubCommandHolder;
import net.specialattack.spacore.command.test.TestSelectorCommand;

public class TestSubCommand extends AbstractMultiSubCommand {

    public TestSubCommand(ISubCommandHolder command, String name, String permissions, String... aliases) {
        super(command, name, permissions, aliases);
        new TestSelectorCommand(this, "selector", Consts.PERM_COMMAND_SPA_TEST_SELECTOR, "sel", "select");
    }
}
