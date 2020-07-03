package com.amazon.jdkteam.brownbag;

// if StringLite is subclass of String, we can use it as.
public final class StringLite implements CharSequence {
    private String base; // xliu: weakref?
    private int begin;
    private int end;
    private int hash; // Default to 0

    private StringLite(String base, int begin, int end) {
        this.base = base;
        this.begin= begin;
        this.end = end;
    }

    public StringLite(String base) {
        this.base = base;
        this.begin = 0;
        this.end = base.length();
    }

    public char charAt(int index) {
        if (begin + index >= end) {
            throw new StringIndexOutOfBoundsException(index);
        }

        return base.charAt(begin + index);
    }

    @Override
    public CharSequence subSequence(int startIndex, int endIndex) {
        if (begin + endIndex >= end) {
            throw new StringIndexOutOfBoundsException(startIndex);
        }

        StringLite lite = new StringLite(base, begin + startIndex, begin + endIndex);
        return lite;
    }

    public char[] toCharArray() {
        char result[] = new char[length()];
        int j = 0;

        for (int i=begin; i<end; ++i) {
            result[j++] = base.charAt(i);
        }
        return result;
    }

    public int length() {
        return end - begin;
    }

    public boolean isEmpty() {
        return length() == 0;
    }

    public int hashCode() {
        int h = hash;

        if (h == 0 && length() > 0) {
            for (int i = begin; i < end; i++) {
                h = 31 * h + base.charAt(i);
            }
            hash = h;
        }
        return h;
    }

    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }

        CharSequence obj = null;
        if (anObject instanceof StringLite) {
            StringLite rhs = (StringLite)anObject;
            if (base == rhs.base && begin == rhs.begin && end == rhs.end) {
                return true;
            }
            obj = rhs;
        }

        if (anObject instanceof String) {
            String rhs = (String)anObject;
            if (base == rhs && begin == 0 && end == rhs.length()) {
               return true;
            }

            obj =rhs;
        }

        if (obj != null && length() == obj.length()) {
            for (int i=0, j=begin; i<obj.length(); ++i, ++j) {
                if (obj.charAt(i) != base.charAt(j))
                    return false;

                return true;
            }
        }

        return false;
    }

    public int indexOf(int ch, int fromIndex) {
        int where = base.indexOf(ch, fromIndex);
        if (where >= end) {
            where = -1;
        }
        return where;
    }

    public static StringLite substring(String base, int beginIndex, int endIndex) {
        StringLite lite = new StringLite(base, beginIndex, endIndex);
        return lite;
    }

    public static StringLite prefix(String base, int endIndex) {
        return substring(base, 0, endIndex);
    }

    public static StringLite suffix(String base, int beginIndex) {
        return substring(base, beginIndex, base.length());
    }

    public String toString() {
        return base.substring(begin, end);
    }
}
