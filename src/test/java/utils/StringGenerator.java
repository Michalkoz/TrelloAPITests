package utils;

public class StringGenerator {

        public static StringBuilder stringBuilder = new StringBuilder("");
        private static String lastString;

        public static String stringGenerator ( int amountOfLetters) {
            for (int i = 0; i <= amountOfLetters - 1; i++) {
                stringBuilder.append("a");
            }
            lastString = new String(stringBuilder);
            return lastString;
        }


    }

