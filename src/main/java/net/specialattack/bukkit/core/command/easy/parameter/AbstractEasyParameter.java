package net.specialattack.bukkit.core.command.easy.parameter;

public abstract class AbstractEasyParameter<T> implements IEasyParameterHandler<T> {

    private String name;
    private T value;

    @SuppressWarnings("unchecked")
    public <K extends AbstractEasyParameter<T>> K setName(String name) {
        this.name = name;
        return (K) this;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean takesAll() {
        return false;
    }

    @Override
    public String getHelpDisplay() {
        return "[" + this.name + "]";
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static abstract class Multi<T> extends AbstractEasyParameter<T> {

        private boolean takesAll = false;

        @SuppressWarnings("unchecked")
        public <K extends AbstractEasyParameter.Multi<T>> K setTakeAll() {
            this.takesAll = true;
            return (K) this;
        }

        @Override
        public boolean takesAll() {
            return this.takesAll;
        }

        @Override
        public String getHelpDisplay() {
            String name = this.getName();
            if (this.takesAll()) {
                return "[" + name + "1 [" + name + "2 ...]]";
            } else {
                return "[" + name + "]";
            }
        }

    }

}
