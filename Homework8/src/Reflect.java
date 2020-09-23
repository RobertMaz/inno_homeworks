import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class Reflect implements Cleaner{

    /**
     * Method first save all fields from fieldsToOutput,
     * then clear filed from fieldsToCleanup. First parameter any object.
     * If object instance of Map, then first save all values from fieldsToOutput,
     * then clear map by key from fieldsToCleanup;
     *
     * @param object          - object for work
     * @param fieldsToCleanup
     * @param fieldsToOutput
     * @return
     * @throws IllegalAccessException - if fields from fieldsToCleanUp or fieldsToOutput not found in object class.
     */
    @Override
    public List<String> cleanUp(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws IllegalAccessException {
        List<String> values;
        if (object instanceof Map) {
            Map map = (Map) object;
            values = getValuesString(map, fieldsToOutput);
            cleanFieldsInMap(map, fieldsToCleanup);
            return values;
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        checkFields(fields, fieldsToCleanup, fieldsToOutput);
        values = getFieldsString(object, fieldsToOutput, fields);
        cleanFields(object, fieldsToCleanup, fields);
        return values;
    }


    /**
     * Check fieldsToCleanUp and fieldsToOutput, if fields not contains field, then throw new IllegalArgumentException.
     *
     * @param fields
     * @param fieldsToCleanup
     * @param fieldsToOutput
     */
    private void checkFields(Field[] fields, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) {
        fieldsToCleanup.forEach(cur -> {
            if (Arrays.stream(fields).noneMatch(cur2 -> cur2.getName().equals(cur))) {
                throw new IllegalArgumentException("Not found field");
            }
        });

        fieldsToOutput.forEach(cur -> {
            if (Arrays.stream(fields).noneMatch(cur2 -> cur2.getName().equals(cur))) {
                throw new IllegalArgumentException("Not found field");
            }
        });
    }

    /**
     * Clean keys in map, from fieldsToCleanUp. if key not find, throw new IllegalArgumentException.
     *
     * @param map
     * @param fieldsToCleanup
     */
    private void cleanFieldsInMap(Map map, Set<String> fieldsToCleanup) {
        for (String s : fieldsToCleanup) {
            if (map.containsKey(s)) {
                map.remove(s);
            } else {
                throw new IllegalArgumentException("Key not found:" + s);
            }
        }
    }

    /**
     * Get all values in map from fieldsToOutput.
     *
     * @param map
     * @param fieldsToOutput
     * @return
     */
    private List<String> getValuesString(Map map, Set<String> fieldsToOutput) {
        List<String> list = new ArrayList<>();
        for (String name : fieldsToOutput) {
            if (map.containsValue(name)) {
                list.add(name);
            } else {
                throw new IllegalArgumentException("Value not found:" + name);
            }
        }
        return list;
    }

    /**
     * Get all String from user which contains fieldsToOutput.
     * @param user
     * @param fieldsToOutput
     * @param fields
     * @return
     * @throws IllegalAccessException
     */
    private List<String> getFieldsString(Object user, Set<String> fieldsToOutput, Field[] fields) throws IllegalAccessException {
        List<String> list = new ArrayList<>();
        for (Field field : fields) {
            if (fieldsToOutput.contains(field.getName())) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                if (field.get(user) != null) {
                    switch (type.getName()) {
                        case "int":
                        case "double":
                        case "float":
                        case "long":
                        case "byte":
                        case "short":
                        case "boolean":
                        case "char":
                            list.add(String.valueOf(field.get(user)));
                            break;
                        default:
                            list.add(field.get(user).toString());
                    }
                }
            }

        }
        return list;
    }

    /**
     * Clean all fields which contains in fieldsToCleanUp for object.
     * @param object
     * @param fieldsToCleanup
     * @param fields
     * @throws IllegalAccessException
     */
    private void cleanFields(Object object, Set<String> fieldsToCleanup, Field[] fields) throws IllegalAccessException {
        for (Field field : fields) {
            if (fieldsToCleanup.contains(field.getName())) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                switch (type.getName()) {
                    case "int":
                    case "double":
                    case "float":
                    case "long":
                        field.set(object, 0);
                        break;
                    case "byte":
                        field.setByte(object, (byte) 0);
                        break;
                    case "short":
                        field.setShort(object, (short) 0);
                        break;
                    case "boolean":
                        field.setBoolean(object, false);
                        break;
                    case "char":
                        field.setChar(object, '\u0000');
                        break;
                    default:
                        field.set(object, null);
                }
            }
        }
    }
}
