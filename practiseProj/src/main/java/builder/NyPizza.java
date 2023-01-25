package builder;

import java.util.Objects;

/**
 * @author AceWong
 * @create 2022/10/27 22:56
 */
public class NyPizza extends Pizza {
    public Size getSize() {
        return size;
    }

    public enum Size {SMALL, MEDIUM, LARGE}

    private final Size size;

    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }

    public static void main(String[] args) {
        NyPizza build = new Builder(Size.LARGE).build();

    }
}
