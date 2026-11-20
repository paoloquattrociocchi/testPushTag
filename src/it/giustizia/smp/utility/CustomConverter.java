package it.giustizia.smp.utility;

public class CustomConverter implements org.dozer.CustomConverter {

    public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {

        if (source == null) {
            return null;
        }
        if (source instanceof Boolean) {
            if (destClass.equals(Character.class)) {
                if ((source != null) && ((Boolean) source).booleanValue()) {
                    destination = new Character('S');
                }
            } else
                if (destClass.equals(String.class)) {
                    if ((source != null) && ((Boolean) source).booleanValue()) {
                        destination = new String("S");
                    }
                }
        } else
            if (source instanceof Character) {
                if (destClass.equals(Boolean.class) || destClass.equals(boolean.class)) {
                    if ((source != null) && ((Character) source).equals(new Character('S'))) {
                        destination = new Boolean(true);
                    } else {
                        destination = new Boolean(false);
                    }
                }
            } else
                if (source instanceof String) {
                    if (destClass.equals(Boolean.class) || destClass.equals(boolean.class)) {
                        if ((source != null) && ((String) source).equals("S")) {
                            destination = new Boolean(true);
                        } else {
                            destination = new Boolean(false);
                        }
                    }
                }
        return destination;
    }
}
