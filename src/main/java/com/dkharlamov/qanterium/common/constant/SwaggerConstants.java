package com.dkharlamov.qanterium.common.constant;

public final class SwaggerConstants {
    private SwaggerConstants() {
        throw new IllegalStateException("Utility class");
    }
    public static final String MAX_SAFE_LONG_VALUE = "9007199254740991";
    public static final long MAX_SAFE_LONG = 9007199254740991L;
    public static final String MAX_SAFE_INT_VALUE = "2147483647";
    public static final int MAX_SAFE_INT = 2_147_483_647;
    public static final String ZERO_VALUE = "0";
    public static final int ZERO = 0;
    public static final String MIN_STRING_LENGTH_1_VALUE = "1";
    public static final int MIN_STRING_LENGTH_1 = 1;
    public static final String DEFAULT_MAX_LIMIT = "100";
    public static final int MAX_LIMIT = 100;
    public static final int MAX_STRING_LENGTH_255 = 255;

    public static final String OFFSET_PARAMETER_NAME = "_page.offset"; //определяет индекс первого ресурса, по умолчанию (если не указан) равен 1. Т.е. отсчет начинается с 1 (не с 0).
    public static final String LIMIT_PARAMETER_NAME = "_page.limit"; //определяет максимальное количество ресурсов в ответе
    public static final String SORT_PARAMETER_NAME = "_sort"; //../items?_sort=<field1>,-<field2>. '+' или отсутствие знака – сортировка по возрастанию
    public static final String OFFSET_PARAMETER_DESCRIPTION = "Индекс первого элемента для пагинации";
    public static final String LIMIT_PARAMETER_DESCRIPTION = "Ограничение количества записей для пагинации";
    public static final String SORT_DESCRIPTION = "Сортировка. _sort=<field1>,-<field2>. '+' или отсутствие знака – сортировка по возрастанию";
    public static final String PAGE_SIZE_MAXIMUM = "500";
    public static final String PAGE_SIZE_DESCRIPTION = "Количество записей на странице";
    public static final String PAGE_NUMBER_DESCRIPTION = "Номер страницы";

    public static final String DESCRIPTION_BAD_REQUEST = "BAD_REQUEST";
    public static final String DESCRIPTION_OK = "OK";
    public static final String DESCRIPTION_FORBIDDEN = "FORBIDDEN";
    public static final String DESCRIPTION_NO_CONTENT = "NO_CONTENT";

    public static final String INTEGER_TYPE = "integer";
    public static final String DATE_TYPE = "date";
    public static final String DATE_TIME_TYPE = "date-time";

    public static final String DATE_FORMAT_PATTERN = "^[0-9]{4}-((0[1-9])|(1[0-2]))-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}(()|(.[0-9]{1,6})(Z|([+-](0[0-9]|1[0-2]):([0-5][0-9])))|(.[0-9]{1,6}))$";
    public static final String DATE_PATTERN = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_TIME_LOCAL_PATTERN = "^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}$";
    public static final String UUID_PATTERN = "^\\b[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-\\b[0-9a-fA-F]{12}\\b$";
    public static final String ALPHANUMERIC_PATTERN = "^[a-zA-Zа-яА-Я0-9]+$";
    public static final String LETTERS_WITH_WHITESPACE_ONLY_PATTERN = "^[a-zA-Zа-яА-Я ]+$";
    public static final String LETTERS_ONLY_PATTERN = "^[a-zA-Zа-яА-Я]+$";
    public static final String LETTERS_ONLY_ENG_PATTERN = "^[a-zA-Z]+$";
    public static final String ERROR_CODE_PATTERN = "^[a-z\\.-]+$";
    public static final String DIGITS_ONLY_PATTERN = "^[0-9]+$";
    public static final String ANY_SYMBOL_PATTERN = ".*";
    public static final String LOGIN_PATTERN = "^([^А-Яа-я\\,\\s\\:]+)$"; // паттерн взят из ОС Карточка сотрудника

    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

}
