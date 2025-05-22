package CourseWork.Core.CourseWork.Domain;

public enum Roles {
    ADMIN(1),
    DRIVER(2),
    GUEST(3);

    private final int value;

    Roles(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Roles fromValue(int value) {
        for (Roles role : Roles.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Нет роли с таким значением: " + value);
    }
}
