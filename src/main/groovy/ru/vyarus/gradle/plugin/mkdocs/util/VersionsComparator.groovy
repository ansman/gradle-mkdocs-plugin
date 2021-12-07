package ru.vyarus.gradle.plugin.mkdocs.util

import groovy.transform.CompileStatic

/**
 * A {@link Comparator} for version (or chapter) numbers, which have an arbitrary number of decimal points.
 * <p>
 * The code was taken from https://bugs.openjdk.java.net/browse/JDK-8134512 and
 * http://cr.openjdk.java.net/~igerasim/8134512/04/webrev/index.html.
 *
 * @author Ivan Gerasimov
 * @since 07.12.2021
 */
@CompileStatic
@SuppressWarnings('CodeNarc')
class VersionsComparator {

    static Comparator<String> comparingVersions(boolean leadingZeroesFirst) {
        return new AlphaDecimalComparator<String>(Comparator
                .comparing({ it.toString() }, Comparator.naturalOrder()), leadingZeroesFirst)
    }

    // adding methods as suggested in https://bugs.openjdk.java.net/browse/JDK-8213167 to store proper docs.
    // methods commented because groovy fails to resolve types correctly with them

    /**
     * The returned comparator compares two character sequences as though each
     * of them would be first transformed into a tuple of the form:
     * <pre>{@code (A0, N0, A1, N1, ..., An-1, Nn-1, An, Nn)}</pre>
     * where:
     * <p>{@code A0} and {@code An} are (possibly empty) sub-sequences
     * consisting of non-decimal-digit characters,
     * <p>{@code A1 ... An-1} are non-empty sub-sequences consisting of
     * non-decimal-digit characters,
     * <p>{@code N0 ... Nn-1} are non-empty sub-sequences consisting of
     * decimal-digit characters, and
     * <p>{@code Nn} is a (possibly empty) sub-sequence consisting of
     * decimal-digit characters.
     *
     * <p>All sub-sequences concatenated together in order as they appear in the
     * tuple yield the original character sequence.
     *
     * After transformation, the tuples are compared by their elements (from
     * left to right) so that corresponding {@code Ax} elements are compared
     * using the provided comparator {@code alphaComparator} and {@code Nx}
     * elements are compared as non negative decimal integers.
     *
     * The first pair of compared elements that is different with respect to the
     * used comparator (either {@code alphaComparator}, or special decimal
     * comparator) if any, provides the result produced by this comparator.
     * The arguments are treated equal, if and only if all the subsequences,
     * both decimal and non-decimal, compare equal.
     *
     * <p>For example, the following array was sorted using such comparator:
     * <pre>{@code
     * { "1ab", "5ab", "10ab",
     *   "a1b", "a5b", "a10b",
     *   "ab1", "ab5", "ab10" };}</pre>
     *
     * <p>When comparing numerical parts, an empty character sequence is
     * considered less than any non-empty sequence of decimal digits.
     *
     * <p>If the numeric values of two compared character sub-sequences are
     * equal, but their string representations have different number of leading
     * zeroes, the comparator treats the number with less leading zeros as
     * smaller.
     * For example, {@code "abc 1" < "abc 01" < "abc 001"}.
     *
     * @apiNote For example, to sort a collection of {@code String} based on
     * case-insensitive ordering, and treating numbers with more leading
     * zeroes as greater, one could use
     *
     * <pre>{@code
     *     Comparator<String> cmp = Comparator.comparingAlphaDecimal(
     *             Comparator.comparing(CharSequence::toString,
     *                                  String::compareToIgnoreCase));
     *}</pre>
     *
     * @implSpec To test if the given code point represents a decimal digit,
     * the comparator checks if {@link java.lang.Character#getType(int)}
     * returns value {@link java.lang.Character#DECIMAL_DIGIT_NUMBER}.
     * The comparator uses {@link java.lang.Character#digit(int, int)} with
     * the second argument set to {@code 10} to determine the numeric
     * value of a digit represented by the given code point.
     *
     * @param alphaComparator the comparator that compares sub-sequences
     *                         consisting of non-decimal-digits
     * @param <T>  the type of elements to be compared; normally
     * {@link java.lang.CharSequence}
     * @return a comparator that compares character sequences, following the
     *                         rules described above
     * @throws NullPointerException if the argument is null
     */
//    static <T extends CharSequence> Comparator<T> comparingAlphaDecimal(
//            Comparator<? super CharSequence> alphaComparator) {
//        return new AlphaDecimalComparator<T>(
//                Objects.requireNonNull(alphaComparator), false)
//    }

    /**
     * The returned comparator compares two character sequences as though each
     * of them would be first transformed into a tuple of the form:
     * <pre>{@code (A0, N0, A1, N1, ..., An-1, Nn-1, An, Nn)}</pre>
     * where:
     * <p>{@code A0} and {@code An} are (possibly empty) sub-sequences
     * consisting of non-decimal-digit characters,
     * <p>{@code A1 ... An-1} are non-empty sub-sequences consisting of
     * non-decimal-digit characters,
     * <p>{@code N0 ... Nn-1} are non-empty sub-sequences consisting of
     * decimal-digit characters, and
     * <p>{@code Nn} is a (possibly empty) sub-sequence consisting of
     * decimal-digit characters.
     *
     * <p>All sub-sequences concatenated together in order as they appear in the
     * tuple yield the original character sequence.
     *
     * After transformation, the tuples are compared by their elements (from
     * left to right) so that corresponding {@code Ax} elements are compared
     * using the provided comparator {@code alphaComparator} and {@code Nx}
     * elements are compared as non negative decimal integers.
     *
     * The first pair of compared elements that is different with respect to the
     * used comparator (either {@code alphaComparator}, or special decimal
     * comparator) if any, provides the result produced by this comparator.
     * The arguments are treated equal, if and only if all the subsequences,
     * both decimal and non-decimal, compare equal.
     *
     * <p>For example, the following array was sorted using such comparator:
     * <pre>{@code
     * { "1ab", "5ab", "10ab",
     *   "a1b", "a5b", "a10b",
     *   "ab1", "ab5", "ab10" };}</pre>
     *
     * <p>When comparing numerical parts, an empty character sequence is
     * considered less than any non-empty sequence of decimal digits.
     *
     * <p>If the numeric values of two compared character sub-sequences are
     * equal, but their string representations have different number of leading
     * zeroes, the comparator treats the number with more leading zeros as
     * smaller.
     * For example, {@code "abc 001" < "abc 01" < "abc 1"}.
     *
     * @apiNote For example, to sort a collection of {@code String} based on
     * case-insensitive ordering, and treating numbers with less leading
     * zeroes as greater, one could use
     *
     * <pre>{@code
     *       Comparator<String> cmp = Comparator.comparingAlphaDecimalLeadingZeroesFirst(
     *             Comparator.comparing(CharSequence::toString,
     *                                  String::compareToIgnoreCase));
     *}</pre>
     *
     * @implSpec To test if the given code point represents a decimal digit,
     * the comparator checks if {@link java.lang.Character#getType(int)}
     * returns value {@link java.lang.Character#DECIMAL_DIGIT_NUMBER}.
     * The comparator uses {@link java.lang.Character#digit(int, int)} with
     * the second argument set to {@code 10} to determine the numeric
     * value of a digit represented by the given code point.
     *
     * @param alphaComparator the comparator that compares sub-sequences
     *                         consisting of non-decimal-digits
     * @param <T>  the type of elements to be compared; normally
     * {@link java.lang.CharSequence}
     * @return a comparator that compares character sequences, following the
     *                         rules described above
     * @throws NullPointerException if the argument is null
     */
//    static <T extends CharSequence> Comparator<T> comparingAlphaDecimalLeadingZeroesFirst(
//            Comparator<? super CharSequence> alphaComparator) {
//        return new AlphaDecimalComparator<T>(
//                Objects.requireNonNull(alphaComparator), true)
//    }

    /**
     * Compares char sequences, taking into account their numeric part if one exists.
     */
    static class AlphaDecimalComparator<T extends CharSequence> implements Comparator<T> {

        private final Comparator<? super CharSequence> alphaComparator
        private final Comparator<CharSequence> decimalComparator

        AlphaDecimalComparator(Comparator<? super CharSequence> alphaComparator, boolean leadingZeroesFirst) {
            this(alphaComparator, DecimalComparator.getInstance(leadingZeroesFirst))
        }

        private AlphaDecimalComparator(Comparator<? super CharSequence> alphaComparator,
                                       Comparator<CharSequence> decimalComparator) {
            this.alphaComparator = alphaComparator
            this.decimalComparator = decimalComparator
        }

        @Override
        Comparator<T> reversed() {
            return new AlphaDecimalComparator<T>(alphaComparator.reversed(),
                    decimalComparator.reversed())
        }

        @Override
        int compare(T cs1, T cs2) {
            Decomposer d1 = new Decomposer(cs1)
            Decomposer d2 = new Decomposer(cs2)
            for (; ;) {
                int cmp
                if ((cmp = alphaComparator.compare(d1.get(), d2.get())) != 0 ||
                        (cmp = decimalComparator.compare(d1.get(), d2.get())) != 0) {
                    return cmp
                }
                if (d1.eos() && d2.eos()) return 0
            }
        }

        /**
         * Given a CharSequence, splits it into a series of subsequences so that
         * every character of the very first subsequence (possibly empty) is
         * not a decimal digit;  then every character of the second subsequence
         * is a decimal digit, and so on.
         */
        private static class Decomposer {
            private final CharSequence sequence
            private boolean expectingDecimal = false
            private int index = 0

            Decomposer(CharSequence sequence) {
                this.sequence = sequence
            }

            CharSequence get() {
                int start = index, end = start, len = sequence.length() - start
                while (len > 0) {
                    int cp = Character.codePointAt(sequence, end)
                    int ct = Character.getType(cp)
                    boolean isDecimal = (ct == Character.DECIMAL_DIGIT_NUMBER)
                    if (isDecimal ^ expectingDecimal) {
                        break
                    }
                    int cpWidth = Character.charCount(cp)
                    end += cpWidth
                    len -= cpWidth
                }
                expectingDecimal = !expectingDecimal
                return sequence.subSequence(start, index = end)
            }

            boolean eos() {
                return index >= sequence.length()
            }
        }
    }

    /**
     * The comparator for comparing character sequences that consist solely
     * of decimal digits.  The result of comparing is as if the values were
     * compared numerically.
     */
    static class DecimalComparator implements Comparator<CharSequence> {

        private static final Comparator<CharSequence> DECIMAL_COMPARATOR_LEADING_ZEROES_FIRST =
                new DecimalComparator(true) {
                    @Override
                    Comparator<CharSequence> reversed() {
                        return DECIMAL_COMPARATOR_LEADING_ZEROES_FIRST_REVERSED
                    }
                }

        private static final Comparator<CharSequence> DECIMAL_COMPARATOR_LEADING_ZEROES_LAST =
                new DecimalComparator(false) {
                    @Override
                    Comparator<CharSequence> reversed() {
                        return DECIMAL_COMPARATOR_LEADING_ZEROES_LAST_REVERSED
                    }
                }

        private static final Comparator<CharSequence> DECIMAL_COMPARATOR_LEADING_ZEROES_FIRST_REVERSED =
                new DecimalComparator(true) {
                    @Override
                    Comparator<CharSequence> reversed() {
                        return DECIMAL_COMPARATOR_LEADING_ZEROES_FIRST
                    }

                    @Override
                    int compare(CharSequence cs1, CharSequence cs2) {
                        return super.compare(cs2, cs1)
                    }
                }

        private static final Comparator<CharSequence> DECIMAL_COMPARATOR_LEADING_ZEROES_LAST_REVERSED =
                new DecimalComparator(false) {
                    @Override
                    Comparator<CharSequence> reversed() {
                        return DECIMAL_COMPARATOR_LEADING_ZEROES_LAST
                    }

                    @Override
                    int compare(CharSequence cs1, CharSequence cs2) {
                        return super.compare(cs2, cs1)
                    }
                }

        private final boolean leadingZeroesFirst

        DecimalComparator(boolean leadingZeroesFirst) {
            this.leadingZeroesFirst = leadingZeroesFirst
        }

        static Comparator<CharSequence> getInstance(boolean leadingZeroesFirst) {
            return leadingZeroesFirst ? DECIMAL_COMPARATOR_LEADING_ZEROES_FIRST
                    : DECIMAL_COMPARATOR_LEADING_ZEROES_LAST
        }

        private boolean canSkipLeadingZeroes(CharSequence s, int len) {
            for (int i = 0; i < len;) {
                int cp = Character.codePointAt(s, i)
                if (Character.digit(cp, 10) != 0)
                    return false
                i += Character.charCount(cp)
            }
            return true
        }

        @Override
        int compare(CharSequence cs1, CharSequence cs2) {
            int len1 = Character.codePointCount(cs1, 0, cs1.length())
            int len2 = Character.codePointCount(cs2, 0, cs2.length())
            int dlen = len1 - len2
            if (len1 == 0 || len2 == 0) {
                return dlen
            } else if (dlen > 0) {
                if (!canSkipLeadingZeroes(cs1, dlen))
                    return 1
                int off = Character.offsetByCodePoints(cs1, 0, dlen)
                cs1 = cs1.subSequence(off, cs1.length())
            } else if (dlen < 0) {
                if (!canSkipLeadingZeroes(cs2, -dlen))
                    return -1
                int off = Character.offsetByCodePoints(cs2, 0, -dlen)
                cs2 = cs2.subSequence(off, cs2.length())
            }
            int cmp = 0
            int i1 = 0
            for (int i2 = 0; i1 < cs1.length();) {
                int cp1 = Character.codePointAt(cs1, i1)
                int cp2 = Character.codePointAt(cs2, i2)
                if (cp1 != cp2) {
                    if (cmp == 0) {
                        cmp = cp1 - cp2
                    }
                    int cmpNum = Character.digit(cp1, 10) -
                            Character.digit(cp2, 10)
                    if (cmpNum != 0) {
                        return cmpNum
                    }
                }
                i1 += Character.charCount(cp1)
                i2 += Character.charCount(cp2)
            }
            return dlen == 0 ? cmp : (leadingZeroesFirst ^ (dlen < 0) ? -1 : 1)
        }
    }
}
