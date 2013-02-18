package util;

import java.util.*;

public abstract class Grammar {

    private static final List<String> ones = Arrays.asList("no",
                                                           "one",
                                                           "two",
                                                           "three",
                                                           "four",
                                                           "five",
                                                           "six",
                                                           "seven",
                                                           "eight",
                                                           "nine",
                                                           "ten",
                                                           "eleven",
                                                           "twelve",
                                                           "thirteen",
                                                           "fourteen",
                                                           "fifteen",
                                                           "sixteen",
                                                           "seventeen",
                                                           "eighteen",
                                                           "nineteen");
    private static final List<String> tens = Arrays.asList("",
                                                           "ten",
                                                           "twenty",
                                                           "thirty",
                                                           "forty",
                                                           "fifty",
                                                           "sixty",
                                                           "seventy",
                                                           "eighty",
                                                           "ninety");
    private static final List<String> ordinal_ones = Arrays.asList("zeroeth",
                                                                   "first",
                                                                   "second",
                                                                   "third",
                                                                   "fourth",
                                                                   "fifth",
                                                                   "sixth",
                                                                   "seventh",
                                                                   "eighth",
                                                                   "ninth",
                                                                   "tenth",
                                                                   "eleventh",
                                                                   "twelfth",
                                                                   "thirteenth",
                                                                   "fourteenth",
                                                                   "fifteenth",
                                                                   "sixteenth",
                                                                   "seventeenth",
                                                                   "eighteenth",
                                                                   "nineteenth");
    private static final List<String> ordinal_tens = Arrays.asList("",
                                                                   "tenth",
                                                                   "twentieth",
                                                                   "thirtieth",
                                                                   "fortieth",
                                                                   "fiftieth",
                                                                   "sixtieth",
                                                                   "seventieth",
                                                                   "eightieth",
                                                                   "ninetieth");
    private static final Set<Character> vowels = new HashSet<Character>(Arrays.asList('a',
                                                                                      'e',
                                                                                      'i',
                                                                                      'o',
                                                                                      'u',
                                                                                      'A',
                                                                                      'E',
                                                                                      'I',
                                                                                      'O',
                                                                                      'U'));
    private static final Map<String, String> exceptions = new HashMap<String, String>();

    static {
        exceptions.put("calf", "calves");
        exceptions.put("dwarf", "dwarves");
        exceptions.put("half", "halves");
        exceptions.put("leaf", "leaves");
        exceptions.put("loaf", "loaves");
        exceptions.put("sheaf", "sheaves");
        exceptions.put("shelf", "shelves");
        exceptions.put("thief", "thieves");
        exceptions.put("wolf", "wolves");
        exceptions.put("scarf", "scarves");
        exceptions.put("wharf", "wharves");
        exceptions.put("knife", "knives");
        exceptions.put("life", "lives");
        exceptions.put("wife", "wives");
        exceptions.put("beans", "beans");
        exceptions.put("child", "children");
        exceptions.put("ox", "oxen");
        exceptions.put("foot", "feet");
        exceptions.put("goose", "geese");
        exceptions.put("tooth", "teeth");
        exceptions.put("person", "people");
        exceptions.put("louse", "lice");
        exceptions.put("mouse", "mice");
        exceptions.put("echo", "echoes");
        exceptions.put("hero", "heroes");
        exceptions.put("potato", "potatoes");
        exceptions.put("tomato", "tomatoes");
        exceptions.put("negro", "negroes");
        exceptions.put("deer", "deer");
        exceptions.put("doe", "doe");
        exceptions.put("fish", "fish");
        exceptions.put("lynx", "lynx");
        exceptions.put("quail", "quail");
        exceptions.put("remains", "remains");
        exceptions.put("salmon", "salmon");
        exceptions.put("sheep", "sheep");
        exceptions.put("swiss", "swiss");
        exceptions.put("trout", "trout");
        exceptions.put("quid", "quid");
        exceptions.put("penny", "pence");
        exceptions.put("roman", "romans");
        exceptions.put("imago", "imagines");
    }

    /**
     * Returns a string for a number.
     *
     * @param num The number. Numbers from 100 result in "lots of".
     * @return The string.
     */
    public static String convertNumber(int num) {
        if (num < 0) {
            return "error";
        }
        if (num < 20) {
            return ones.get(num);
        }
        if (num > 99) {
            return "lots of";
        }
        int uns = num % 10;
        if (uns > 0) {
            return tens.get(num / 10) + "-" + ones.get(uns);
        }
        return tens.get(num / 10);
    }

    /**
     * Returns a number for a string.
     *
     * @param str The string. Numbers from 100 result in null.
     * @return The number.
     */
    public static Integer convertStringToNumber(String str) {
        str = str.toLowerCase();
        int num = ones.indexOf(str);
        if (num > -1) {
            return num;
        }
        num = tens.indexOf(str);
        if (num > -1) {
            return num * 10;
        }
        String arr[] = str.split(" |-");
        if (arr.length == 2) {
            num = tens.indexOf(arr[0]) * 10;
            if (num > -1) {
                num += ones.indexOf(arr[1]);
                if (num > 20) {
                    return num;
                }
            }
        }
        return null;
    }

    /**
     * Returns a string for an ordinal number (first, fourth).
     *
     * @param num The number. If a number larger than 99 is given, the returned String will hold the number with a .
     *            appended.
     * @return The string.
     */
    public static String convertOrdinalNumber(int num) {
        if (num < 0) {
            return "error";
        }
        if (num > 99) {
            return num + ".";
        }
        if (num < 20) {
            return ordinal_ones.get(num);
        }
        int uns = num % 10;
        if (uns > 0) {
            return tens.get(num / 10) + "-" + ordinal_ones.get(uns);
        }
        return ordinal_tens.get(num / 10);
    }

    /**
     * Returns a number for an ordinal.
     *
     * @param str The string. Numbers from 100 result in null.
     * @return The number.
     */
    public static Integer convertOrdinalStringToNumber(String str) {
        str = str.toLowerCase();
        int num = ordinal_ones.indexOf(str);
        if (num > -1) {
            return num;
        }
        num = ordinal_tens.indexOf(str);
        if (num > -1) {
            return num * 10;
        }
        String arr[] = str.split(" |-");
        if (arr.length == 2) {
            num = ordinal_tens.indexOf(arr[0]) * 10;
            if (num > -1) {
                num += ordinal_ones.indexOf(arr[1]);
                if (num > 20) {
                    return num;
                }
            }
        }
        return null;
    }

    /**
     * Returns the plural form (hopefully) of a word.
     *
     * @param str The word.
     * @return The plural form.
     */
    public static String pluralizeWord(String str) {
        String e = exceptions.get(str);
        if (e != null) {
            return e;
        }
        int len = str.length();
        if (str.endsWith("ese") || str.endsWith("craft")) {
            return str;
        }
        if (str.endsWith("is")) {
            return str.substring(0, len - 2) + "es";
        }
        if (str.endsWith("us")) {
            if (len < 4) {
                return str + "ses";
            }
            return str.substring(0, len - 2) + "i";
        }
        if (len == 1) {
            return str + "s";
        }
        if (str.endsWith("man")) {
            return str.substring(0, len - 3) + "men";
        }
        switch (str.charAt(len - 1)) {
        case 's':
            if (str.charAt(len - 2) != 's') {
                if (vowels.contains(str.charAt(len - 3))) {
                    return str + "es";
                }
                return str + "ses";
            }
        case 'x':
        case 'h':
            return str + "es";
        case 'y':
            switch (str.charAt(len - 2)) {
            case 'a':
            case 'e':
            case 'o':
                return str + "s";
            }
            return str.substring(0, len - 1) + "ies";
        case 'z':
            if (str.charAt(len - 2) == 'z') {
                return str + "es";
            }
            return str + "zes";
        }
        return str + "s";
    }

    /**
     * Returns the given string with it's article prepended.
     *
     * @param noun The word to find the article of.
     * @return The string with article.
     */
    public static String addArticle(String noun) {
        return "a" + (vowels.contains(noun.charAt(0)) ? "n" : "") + " " + noun;
    }

    /**
     * Returns the given string with its article prepended, but only if the String doesn't already begin with an
     * article.
     *
     * @param noun The word to find the article of.
     * @param flag Whether to add 'the' unless there is an article already set.
     * @return The string with article.
     */
    public static String maybeAddArticle(String noun, boolean flag) {
        String tmp = noun.toLowerCase();
        if (tmp.startsWith("a ") ||
            tmp.startsWith("an ") ||
            tmp.startsWith("the ") ||
            tmp.startsWith("some ")) {
            return noun;
        } else if (flag) {
            return "the " + noun;
        } else {
            return addArticle(noun);
        }
    }

    /**
     * Returns the plural form of the given noun.
     *
     * @param str The noun to pluralize.
     * @return The pluralized form.
     */
    public static String pluralize(String str) {
        String words[] = str.split(" ");
        if (words.length == 1) {
            return pluralizeWord(words[0]);
        }
        int i = Arrays.binarySearch(words, "of");
        if (i > 0) {
            words[i - 1] = pluralizeWord(words[i - 1]);
        } else {
            words[words.length - 1] = pluralizeWord(words[words.length - 1]);
        }
        return join(" ", Arrays.asList(words));
    }

    /**
     * This function is made for listing things gramatically with ", " separating the strings in the list, and " and "
     * between the last two strings.
     *
     * @param lst The list to concatenate.
     * @return The concatenated string.
     */
    public static String makeList(List<?> lst) {
        int len = lst.size();
        if (len > 2) {
            return join(", ", lst.subList(0, len - 2)) + " and " + lst.get(len - 1);
        }
        if (len == 2) {
            return join(" and ", lst);
        }
        return lst.get(0).toString();
    }

    /**
     * Convenience method to call {@link #makeList(List)}.
     *
     * @param arr The list to concatenate.
     * @return The concatenated string.
     */
    public static String makeList(Object... arr) {
        return makeList(Arrays.asList(arr));
    }

    /**
     * Returns a string that is the concatenation of the strings in the given array.
     *
     * @param delim The delimiter to add between strings.
     * @param lst   The array of strings to join.
     * @return The concatenated string.
     */
    public static String join(String delim, List<?> lst) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0, len = lst.size(); i < len; ++i) {
            ret.append(lst.get(i));
            if (i < len - 1) {
                ret.append(delim);
            }
        }
        return ret.toString();
    }

    /**
     * Convenience method to call {@link #join(String, List)}.
     *
     * @param delim The delimiter to add between strings.
     * @param arr   The array of string to join.
     * @return The concatenated string.
     */
    public static String join(String delim, Object... arr) {
        return join(delim, Arrays.asList(arr));
    }

    /**
     * Returns the given string with a prefixed numeral.
     *
     * @param str The string to prefix.
     * @param num The number to prefix.
     * @return The prefixed string.
     */
    public static String compositeShort(String str, int num) {
        if (str == null) {
            return null;
        }
        if (num == 1) {
            return str;
        }
        String lower = str.toLowerCase();
        if (lower.startsWith("a ")) {
            str = str.substring(2);
        } else if (lower.startsWith("an ")) {
            str = str.substring(3);
        } else if (lower.startsWith("one ")) {
            str = str.substring(4);
        } else if (lower.startsWith("the ")) {
            str = str.substring(4);
        } else if (lower.startsWith("some ")) {
            str = str.substring(5);
        }
        return convertNumber(num) + " " + pluralize(str);
    }

/*
    public String compositeList(List<Seed> obj) {
        int i;
        String sh,*ret;
        mapping things;

        things = ([]);
        if (!pointerp(obj) || !sizeof(obj)) {
            return "nothing";
        }
        for (i = 0; i < sizeof(obj); i++) {
            if (objectp(obj[i]) && obj[i] - >short())
            {
                if (living(obj[i])) {
                    if (interactive(obj[i])) {
                        sh = obj[i] - > query_name();
                    } else {
                        sh = (String)obj[i] - >
                    }short();
                } else {
                    sh = (String)obj[i] - >
                }short();
                if (sh) {
                    things[sh]++;
                }
            }
        }
        ret = keys(things);
        if (sizeof(ret)) {
            for (i = 0; i < sizeof(ret); i++) {
                ret[i] = composite_short(ret[i], things[ret[i]]);
            }
            return make_list(ret);
        }
        return 0;
    }

    public String compositeRoomItemList(List<Seed> obj) {
        int i;
        String sh,*ret;
        mapping things;

        things = ([]);
        if (!pointerp(obj) || !sizeof(obj)) {
            return "nothing";
        }
        for (i = 0; i < sizeof(obj); i++) {
            if (objectp(obj[i])) {
                if (living(obj[i]) && !obj[i] - > query_invis()) {
                    sh = obj[i] - > query_name();
                } else {
                    sh = obj[i] - >
                }short();
                if (sh) {
                    things[sh]++;
                }
            }
        }
        ret = keys(things);
        if (sizeof(ret)) {
            for (i = 0; i < sizeof(ret); i++) {
                ret[i] = composite_short(ret[i], things[ret[i]]);
            }
            return sprintf("There %s %s here.\n",
                           (things[ret[0]] == 1) ? "is" : "are",
                           make_list(ret)
            );
        }
        return "";
    }

    public String compositeRoomList(List<Seed> obj) {
        int i;
        String sh,*ret;
        mapping things;

        things = ([]);
        if (!pointerp(obj) || !sizeof(obj)) {
            return 0;
        }
        for (i = 0; i < sizeof(obj); i++) {
            if (objectp(obj[i]) && obj[i] - >short())
            {
                if (living(obj[i]) && interactive(obj[i])) {
                    sh = obj[i] - > query_name();
                } else {
                    sh = (String)obj[i] - >
                }short();
                if (sh) {
                    things[sh]++;
                }
            }
        }
        ret = keys(things);
        if (sizeof(ret)) {
            for (i = 0; i < sizeof(ret); i++) {
                ret[i] = composite_short(ret[i], things[ret[i]]);
            }
            return sprintf("%s %s here.\n",
                           capitalize(make_list(ret)),
                           (sizeof(ret) == 1 &&
                            things[ret[0]] == 1) ? "is" : "are");
        }
        return 0;
    }

    public String makeLivingList(List<Seed> objs) {
        mapping names;
        mapping monsters;
        int i, j;
        String ret,*list;
        mixed n;

        names = ([]);
        monsters = ([]);
        ret = "";
        list = ({ });
        if (!pointerp(objs)) {
            error("Non-array argument to make_living_list().\n");
        }

        for (i = sizeof(objs) - 1; i >= 0; i--) {
            if (!living(objs[i])) {
                continue;
            }
            n = objs[i] - > query_name();
            if (!n || !objs[i] - >short())
            continue;
            if (!interactive(objs[i])) {
                monsters[n] = objs[i];
            }
            names[n]++;
        }
        n = keys(names);
        list = allocate(sizeof(n));
        for (i = sizeof(n) - 1; i >= 0; i--) {
            if ((j = names[n[i]]) == 1) {
                if (monsters[n[i]]) {
                    n[i] = add_article(n[i]);
                }
                list[i] = n[i];
            } else {
                list[i] = convert_number(j) + " " + pluralize_word(n[i]);
            }
        }
        return make_list(list);
    }
*/
}
